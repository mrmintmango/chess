package handler;

import chess.ChessBoard;
import com.google.gson.*;
import dataaccess.GameDAOI;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.commands.*;
import websocket.messages.LoadGameMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.lang.reflect.Type;

@WebSocket
public class WebsocketHandler() {

    @OnWebSocketMessage
    public void onMessage(Session session, String userGameCommand) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(UserGameCommand.class, new CommandDeserializer());
        Gson gson = builder.create();

        UserGameCommand gameCommand = gson.fromJson(userGameCommand, UserGameCommand.class);
        switch (gameCommand.getCommandType()){
            case CONNECT -> connect(session);
            case MAKE_MOVE -> makeMove();
            case LEAVE -> leave();
            case RESIGN -> resign();
        }
        //session.getRemote().sendString("WebSocket response: " + userGameCommand);
    }

    public void connect(Session session) {
        ChessBoard board =
        LoadGameMessage loadGameMessage = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        String load = new Gson().toJson(loadGameMessage);

        try{
            session.getRemote().sendString(load);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove() {}

    public void leave() {}

    public void resign() {}


    private static class CommandDeserializer implements JsonDeserializer<UserGameCommand> {
        @Override
        public UserGameCommand deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String typeString = jsonObject.get("type").getAsString();
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
