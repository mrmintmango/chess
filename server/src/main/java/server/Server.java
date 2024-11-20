package server;

import dataaccess.*;
import handler.Handler;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
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

        ParentService parentService = new ParentService(authDAOI, gameDAOI, userDAOI);
        GameService gameService = new GameService(authDAOI, gameDAOI, gameDAOI.getGameSize()+1);
        UserService userService = new UserService(authDAOI, userDAOI);
        Handler handler = new Handler(parentService, gameService, userService);
        //creates a websocket handler class
        WebSocketHandler wsHandler = new WebSocketHandler();

        //Updated stuff for Websocket, but might not need it because it's in my wsHandler???
        Spark.webSocket("/ws", Server.class);
        //might not need this below?
        //Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));

        Spark.staticFiles.location("web");

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
