package server;

import dataaccess.*;
import handler.Handler;
import handler.WebsocketHandler;
import service.GameService;
import service.ParentService;
import service.UserService;
import spark.*;

public class Server {
    UserDAOI userDAOI = null;
    AuthDAOI authDAOI;
    GameDAOI gameDAOI;

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        try {
            userDAOI = new SQLUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        authDAOI = new SQLAuthDAO();
        gameDAOI = new SQLGameDAO();

        //creates a websocket handler class
        WebsocketHandler wsHandler = new WebsocketHandler(gameDAOI, authDAOI);

        ParentService parentService = new ParentService(authDAOI, gameDAOI, userDAOI);
        GameService gameService = new GameService(authDAOI, gameDAOI, gameDAOI.getGameSize()+1);
        UserService userService = new UserService(authDAOI, userDAOI);
        Handler handler = new Handler(parentService, gameService, userService);

        Spark.staticFiles.location("web");

        //websocket
        Spark.webSocket("/ws", wsHandler);

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
}
