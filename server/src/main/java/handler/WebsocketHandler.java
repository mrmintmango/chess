package handler;

import chess.ChessBoard;
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
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }
    }

    public void addGameToMap(int gameID, Session session, String auth){
        Map<String, Session> sessionMap = new HashMap<>();
        gameMap.put(gameID, sessionMap);
        addSessionToGame(gameID, session, auth);
    }

    public void addSessionToGame(int gameID, Session session, String auth){
        gameMap.get(gameID).put(auth, session);
    }

    public void removeSessionFromGame(){}

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
            throw new RuntimeException(e);
        }

        String player;
        String username;
        try{
            username = auths.getAuth(auth).username();
            if(games.getGame(gameID).whiteUsername().equals(username)){
                player = "WHITE";
            }
            else if (games.getGame(gameID).blackUsername().equals(username)) {
                player = "BLACK";
            }
            else {
                player = "OBSERVER";
            }
        } catch (DataAccessException e) {
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
            throw new RuntimeException(e);
        }
    }

    public void makeMove(int gameID, Session session, String move) {
        //check the validity of the move.




        //update the game
        //send a load game message
        //send a notification to all other clients
        //send in check, checkmate, or stalemate notification to all clients if applicable
    }

    public void leave() {}

    public void resign() {}

    //helpful methods
    public void sendEveryone(int gameID, String auth, String message) throws IOException {
        Map<String, Session> authMap = gameMap.get(gameID);
        for (String authToken : authMap.keySet()){
            authMap.get(authToken).getRemote().sendString(message);
        }
    }

    public void sendMe(int gameID, String auth, String message) throws IOException {
        Session me = gameMap.get(gameID).get(auth);
        me.getRemote().sendString(message);
    }

    public void sendAllButMe(int gameID, String auth, String message) throws IOException {
        Map<String, Session> authMap = gameMap.get(gameID);
        for (String authToken : authMap.keySet()){
            if (!authToken.equals(auth)){
                authMap.get(authToken).getRemote().sendString(message);
            }
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

    public void clearServer(String adminPassword) {
        if (adminPassword.equals("Ruben is Awesome")) {
            users.clear();
            auths.clear();
            games.clear();
            System.out.println(":::Server cleared:::");
            System.out.println(":Terminating System:");
        }
        else {
            System.out.println("Wrong password");
        }
    }
}
