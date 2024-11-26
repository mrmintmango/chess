package handler;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.*;
import dataaccess.AuthDAOI;
import dataaccess.DataAccessException;
import dataaccess.GameDAOI;
import dataaccess.UserDAOI;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebSocket
public class WebsocketHandler {
    GameDAOI games;
    AuthDAOI auths;
    UserDAOI users;

    private final Map<Integer, Map<String, Session>> gameMap; //map <game id, map <Auth token, sessions>>

    public WebsocketHandler(GameDAOI gameDAOI, AuthDAOI auths, UserDAOI users) {
        this.games = gameDAOI;
        this.auths = auths;
        this.users = users;
        gameMap = new HashMap<>();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String userGameCommand) throws Exception {
        if (userGameCommand.contains("destroy"))
        {
            clearServer(userGameCommand.substring(7));
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserGameCommand.class, new CommandDeserializer());
        Gson gson = builder.create();

        UserGameCommand gameCommand = gson.fromJson(userGameCommand, UserGameCommand.class);
        switch (gameCommand.getCommandType()){
            case CONNECT -> connect(session, gameCommand.getGameID(), gameCommand.getAuthToken());
            case MAKE_MOVE -> makeMove(gameCommand.getGameID(), ((MakeMoveCommand) gameCommand).getMove(),
                    gameCommand.getAuthToken(), ((MakeMoveCommand) gameCommand).getMoveText(), session); //Find some other way to figure out if the player has already resigned or not.
            case LEAVE -> leave(gameCommand.getGameID(), gameCommand.getAuthToken());
            case RESIGN -> resign(gameCommand.getGameID(), gameCommand.getAuthToken());
        }
    }

    public void addGameToMap(int gameID, Session session, String auth){
        Map<String, Session> sessionMap = new HashMap<>();
        gameMap.put(gameID, sessionMap);
        addSessionToGame(gameID, session, auth);
    }

    public void addSessionToGame(int gameID, Session session, String auth){
        gameMap.get(gameID).put(auth, session);
        //System.out.println(gameMap);
    }

    public void connect(Session session, int gameID, String auth) {
        if(!gameMap.containsKey(gameID)){
            addGameToMap(gameID, session, auth);
        }
        else{
            addSessionToGame(gameID, session, auth);
        }

        GameData gameData;
        try{
            gameData = games.getGame(gameID);
        } catch (DataAccessException e) {
            System.out.println("88");
            throw new RuntimeException(e);
        }

        String player = getPlayerType(auth, gameID);
        String username = null;
        try{
            username = auths.getAuth(auth).username();
        } catch (DataAccessException e) {
            System.out.println("97");
            throw new RuntimeException(e);
        }


        String notif = username + " has joined the game as " + player;

        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameData, player);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notif);

        String load = new Gson().toJson(loadGameMessage);
        String notification = new Gson().toJson(notificationMessage);

        try{
            sendMe(gameID, auth, load);

            sendAllButMe(gameID, auth, notification);
        } catch (IOException e) {
            System.out.println("115");
            throw new RuntimeException(e);
        }
    }

    public void makeMove(int gameID, ChessMove move, String auth, String moveText, Session session) {
        //check the validity of the move.
        if(!gameMap.containsKey(gameID)){
            addGameToMap(gameID, session, auth);
        }
        else{
            addSessionToGame(gameID, session, auth);
        }

        try{
            if (games.isGameOver(gameID)){
                String errorMessage = "The game is already over";
                sendError(gameID, auth, errorMessage);
            }
            else if (isObserver(gameID, auth)) {
                String errorMessage = "You can't make a move as an observer";
                sendError(gameID, auth, errorMessage);
            }
            else if (!games.getTurn(gameID).equals(getPlayerType(auth, gameID))){
                String errorMessage = "You can't make a move when it's not your turn";
                sendError(gameID, auth, errorMessage);
            }
            else if (games.getGame(gameID).game().validMoves(move.getStartPosition()).contains(move)){
                updateGame(gameID, move);

                String player = getPlayerType(auth, gameID);

                LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME,
                        games.getGame(gameID), player);
                String load = new Gson().toJson(loadGameMessage);
                sendEveryone(gameID, auth, load);

                String moveMessage = auths.getAuth(auth).username() + " made the move " + moveText;
                NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                        moveMessage);
                String notification = new Gson().toJson(notificationMessage);
                sendAllButMe(gameID, auth, notification);

                //send in check, checkmate, or stalemate notification to all clients if applicable
                if (games.getGame(gameID).game().isInCheck(ChessGame.TeamColor.WHITE)){
                    String checkMessage = auths.getAuth(auth).username() + "(WHITE) has been put in check!";
                    sendCheckMate(gameID, auth, checkMessage);
                }
                else if (games.getGame(gameID).game().isInCheck(ChessGame.TeamColor.BLACK)){
                    String checkMessage = auths.getAuth(auth).username() + "(BLACK) has been put in check!";
                    sendCheckMate(gameID, auth, checkMessage);
                }
                else if (games.getGame(gameID).game().isInCheckmate(ChessGame.TeamColor.WHITE)){
                    String checkMessage = auths.getAuth(auth).username() + "(WHITE) has been put in check mate!";
                    sendCheckMate(gameID, auth, checkMessage);
                    gameOver(gameID);
                }
                else if (games.getGame(gameID).game().isInCheckmate(ChessGame.TeamColor.BLACK)){
                    String checkMessage = auths.getAuth(auth).username() + "(BLACK) has been put in check mate!";
                    sendCheckMate(gameID, auth, checkMessage);
                    gameOver(gameID);
                }
                else if (games.getGame(gameID).game().isInStalemate(ChessGame.TeamColor.WHITE)){
                    String checkMessage = auths.getAuth(auth).username() + "(WHITE) has been put in stale mate!";
                    sendCheckMate(gameID, auth, checkMessage);
                    gameOver(gameID);
                }
                else if (games.getGame(gameID).game().isInStalemate(ChessGame.TeamColor.BLACK)){
                    String checkMessage = auths.getAuth(auth).username() + "(BLACK) has been put in stale mate!";
                    sendCheckMate(gameID, auth, checkMessage);
                    gameOver(gameID);
                }
            }
            else {
                ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Invalid move");
                String error = new Gson().toJson(errorMessage);
                sendMe(gameID, auth, error);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void sendError(int gameID, String auth, String errorMessage){
        ErrorMessage error = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, errorMessage);
        String message = new Gson().toJson(error);
        try{
            sendMe(gameID, auth, message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(int gameID, String auth) {
        try{
            String goodbye = auths.getAuth(auth).username() + " has left the game.";
            NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, goodbye);
            String notification = new Gson().toJson(notificationMessage);
            sendAllButMe(gameID, auth, notification);
            userRemoval(gameID, auth);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(int gameID, String auth) {
        if (isObserver(gameID, auth)) {
            String errorMessage = "You can't resign as an observer";
            sendError(gameID, auth, errorMessage);
        }
        else {
            try{ //games.getGame(gameID).game().isGameOver()
                if (games.isGameOver(gameID)){
                    String errorMessage = "You can't resign after game is over";
                    sendError(gameID, auth, errorMessage);
                }
                else {
                    String goodbye = auths.getAuth(auth).username() + " has resigned from the game.";
                    sendCheckMate(gameID, auth, goodbye);
                    //userRemoval(gameID, auth);
                    gameOver(gameID);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void userRemoval(int gameID, String auth) throws DataAccessException {
        Map<String, Session> temp = gameMap.get(gameID);
        //Session sesh = temp.get(auth);
        //sesh.close();
        temp.remove(auth);

        if (games.getGame(gameID).whiteUsername() != null && auths.getAuth(auth).username().equals(games.getGame(gameID).whiteUsername())){
            games.updateGame(gameID, null, true);
        }
        else if (games.getGame(gameID).blackUsername() != null && auths.getAuth(auth).username().equals(games.getGame(gameID).blackUsername())){
            games.updateGame(gameID, null, false);
        }
    }

    public void updateGame(int gameID, ChessMove move){
        try{
            games.makeMove(gameID, move);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void gameOver(int gameID){
        games.updateGameOver(gameID);
    }

    public void sendCheckMate(int gameID, String auth, String checkMessage) throws IOException {
        NotificationMessage checkNotification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION,
                checkMessage);
        String checkNotif = new Gson().toJson(checkNotification);
        sendEveryone(gameID, auth, checkNotif);
    }

    //helpful methods
    public void sendEveryone(int gameID, String auth, String message) throws IOException {
        Map<String, Session> authMap = gameMap.get(gameID);
        for (String authToken : authMap.keySet()){

            Session temp = authMap.get(authToken);
            if(temp.isOpen()) {
                temp.getRemote().sendString(message);
            }
        }
    }

    public void sendMe(int gameID, String auth, String message) throws IOException {
        Session me = gameMap.get(gameID).get(auth);
        if (me.isOpen()) {
            me.getRemote().sendString(message);
        }
    }

    public void sendAllButMe(int gameID, String auth, String message) throws IOException {
        Map<String, Session> authMap = gameMap.get(gameID);
        for (String authToken : authMap.keySet()){
            if (!authToken.equals(auth)){
                Session temp = authMap.get(authToken);
                if (temp.isOpen()) {
                    temp.getRemote().sendString(message);
                }
            }
        }
    }

    public boolean isObserver(int gameID, String auth){
        try{
            return (!auths.getAuth(auth).username().equals(games.getGame(gameID).whiteUsername()) &&
                    !auths.getAuth(auth).username().equals(games.getGame(gameID).blackUsername()));
        } catch (Exception e) {
            throw new RuntimeException("Can't make a move as an observer (ERROR)");
        }
    }

    @OnWebSocketError
    public void webSocketError(Session session, Throwable message){
        ErrorMessage errorMessage = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message.getMessage());
        String error = new Gson().toJson(errorMessage);
        try {
            session.getRemote().sendString(error);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class CommandDeserializer implements JsonDeserializer<UserGameCommand> {
        @Override
        public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String typeString = jsonObject.get("commandType").getAsString();
            UserGameCommand.CommandType commandType = UserGameCommand.CommandType.valueOf(typeString);

            return switch(commandType) {
                case CONNECT -> context.deserialize(jsonElement, ConnectCommand.class);
                case MAKE_MOVE -> context.deserialize(jsonElement, MakeMoveCommand.class);
                case LEAVE -> context.deserialize(jsonElement, LeaveCommand.class);
                case RESIGN -> context.deserialize(jsonElement, ResignCommand.class);
                case null, default -> throw new JsonIOException("Invalid Command Type");
            };
        }
    }

    public String getPlayerType(String auth, int gameID){
        String player;
        String username;
        try {
            username = auths.getAuth(auth).username();
            if (games.getGame(gameID).whiteUsername() != null && games.getGame(gameID).whiteUsername().equals(username)) {
                player = "WHITE";
            } else if (games.getGame(gameID).blackUsername() != null && games.getGame(gameID).blackUsername().equals(username)) {
                player = "BLACK";
            } else {
                player = "OBSERVER";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return player;
    }

    public void clearServer(String adminPassword) {
        if (adminPassword.equals("Ruben is Awesome")) {
            users.clear();
            auths.clear();
            games.clear();
            gameMap.clear();
            System.out.println(":::Server cleared:::");
            System.out.println(":Terminating System:");
        }
        else {
            System.out.println("Wrong password");
        }
    }
}
