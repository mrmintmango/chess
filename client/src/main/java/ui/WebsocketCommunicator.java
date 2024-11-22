package ui;

import chess.ChessBoard;
import com.google.gson.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.lang.reflect.Type;
import java.net.URI;

public class WebsocketCommunicator extends Endpoint {
    private final Session session;

    public WebsocketCommunicator(ServerMessageObserver serverMessageObserver) throws Exception {

        URI uri = new URI("ws://localhost:8080/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(ServerMessage.class, new MessageDeserializer());
                Gson gson = builder.create();

                ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()){
                    case LOAD_GAME -> loadGame(session, ((LoadGameMessage) serverMessage).getBoard());
                    case ERROR -> error();
                    case NOTIFICATION -> notification();
                }
            }
        });
    }

    public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void loadGame(Session session, ChessBoard board) {
        //notify the client through Observer interface notify method.
        System.out.println(board);
    }

    public void error() {}

    public void notification() {}


    private static class MessageDeserializer implements JsonDeserializer<ServerMessage> {
        @Override
        public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String typeString = jsonObject.get("serverMessageType").getAsString();
            ServerMessage.ServerMessageType messageType = ServerMessage.ServerMessageType.valueOf(typeString);

            return switch(messageType) {
                case LOAD_GAME -> context.deserialize(jsonElement, LoadGameMessage.class);
                case ERROR -> context.deserialize(jsonElement, ErrorMessage.class);
                case NOTIFICATION -> context.deserialize(jsonElement, NotificationMessage.class);
                case null, default -> throw new JsonIOException("Invalid Message Type");
            };
        }
    }
}

