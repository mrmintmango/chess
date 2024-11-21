package server;

import com.google.gson.*;
import dataaccess.*;
import handler.Handler;
import handler.WebsocketHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import service.GameService;
import service.ParentService;
import service.UserService;
import spark.*;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.lang.reflect.Type;

public class Server {
    UserDAOI userDAOI = null;
    AuthDAOI authDAOI;
    GameDAOI gameDAOI;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        //creates a websocket handler class
        WebsocketHandler wsHandler = new WebsocketHandler();

        try {
            userDAOI = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authDAOI = new SQLAuthDAO();
        gameDAOI = new SQLGameDAO();

        ParentService parentService = new ParentService(authDAOI, gameDAOI, userDAOI);
        GameService gameService = new GameService(authDAOI, gameDAOI, gameDAOI.getGameSize()+1);
        UserService userService = new UserService(authDAOI, userDAOI);
        Handler handler = new Handler(parentService, gameService, userService);

        Spark.staticFiles.location("web");

        //websocket
        Spark.webSocket("/ws", wsHandler);
        Spark.get("/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (handler::clearApplication));
        Spark.post("/user", (handler::register));
        Spark.post("/session", (handler::login));
        Spark.delete("/session", handler::logout);
        Spark.get("/game", handler::listGames);
        Spark.post("/game", handler::createGame);
        Spark.put("/game", handler::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public void clearServer(String adminPassword) {
        if (adminPassword.equals("Ruben is Awesome")) {
            userDAOI.clear();
            authDAOI.clear();
            gameDAOI.clear();
        }
    }

    //Websocket implementation
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ServerMessage.class, new MessageDeserializer());
        Gson gson = builder.create();

        ServerMessage serverMessage = gson.fromJson(message, ServerMessage.class);
        switch (serverMessage.getServerMessageType()){
            case LOAD_GAME -> loadGame();
            case ERROR -> error();
            case NOTIFICATION -> notification();
        }
        //session.getRemote().sendString("WebSocket response: " + userGameCommand);
    }

    public void loadGame() {}

    public void error() {}

    public void notification() {}


    private static class MessageDeserializer implements JsonDeserializer<ServerMessage> {
        @Override
        public ServerMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String typeString = jsonObject.get("type").getAsString();
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
