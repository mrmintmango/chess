package handler;

import chess.ChessBoard;
import com.google.gson.*;
import dataaccess.DataAccessException;
import dataaccess.GameDAOI;
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

@WebSocket
public class WebsocketHandler {
    GameDAOI games;

    public WebsocketHandler(GameDAOI gameDAOI) {
        this.games = gameDAOI;
        //map game id map authtoken sessions
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String userGameCommand) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserGameCommand.class, new CommandDeserializer());
        Gson gson = builder.create();

        UserGameCommand gameCommand = gson.fromJson(userGameCommand, UserGameCommand.class);
        switch (gameCommand.getCommandType()){
            case CONNECT -> connect(session, gameCommand.getGameID());
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }
    }

    public void connect(Session session, int gameID) {
        ChessBoard board;
        try{
            board = games.getGame(gameID).game().getBoard();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        //helpful methods
        //Send everyone
        //Send me
        //Send everyone but me

        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, board);
        NotificationMessage notificationMessage = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION);

        String load = new Gson().toJson(loadGameMessage);
        String notification = new Gson().toJson(notificationMessage);

        try{
            session.getRemote().sendString(load);
            //session.getRemote().sendString(notification);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove() {}

    public void leave() {}

    public void resign() {}

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

}
