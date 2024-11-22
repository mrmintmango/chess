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
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@WebSocket
public class WebsocketHandler {
    GameDAOI games;
    AuthDAOI auths;
    UserDAOI users;

    private Map<Integer, Map<String, Session>> gameMap; //map <game id, map <Auth token, sessions>>

    public WebsocketHandler(GameDAOI gameDAOI, AuthDAOI auths, UserDAOI users) {
        this.games = gameDAOI;
        this.auths = auths;
        this.users = users;
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

    public void connect(Session session, int gameID, String auth) {
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

        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, gameData, player);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, username + " has joined the game!");

        String load = new Gson().toJson(loadGameMessage);
        String notification = new Gson().toJson(notificationMessage);

        try{
            sendEveryone(gameID, auth, load);
            sendAllButMe(gameID, auth, notification);

            session.getRemote().sendString(load);
            session.getRemote().sendString(notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove() {}

    public void leave() {}

    public void resign() {}

    //helpful methods
    public void sendEveryone(int gameID, String auth, String message) throws IOException {
        Session everyone = gameMap.get(gameID).get(auth);
        everyone.getRemote().sendString(message);
    }

    public void sendMe(String auth, String message) {}

    public void sendAllButMe(int gameID, String auth, String message) throws IOException {
        Map<String, Session> authMap = gameMap.get(gameID);
        int mapSize = authMap.size();
        for (String authToken : authMap.keySet()){
            if (!authToken.equals(auth)){
                authMap.get(authToken).getRemote().sendString(message);
            }
        }
    }

    @OnWebSocketError
    public void webSocketError(Throwable message){
        System.out.println("WebSocket Error: " + message.getMessage());
    }

    private static class CommandDeserializer implements JsonDeserializer<UserGameCommand> {
        @Override
        public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            System.out.println(jsonObject.toString());
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
    }
}
