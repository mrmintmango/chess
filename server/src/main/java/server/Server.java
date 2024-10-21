package server;

import dataaccess.*;
import handler.Handler;
import service.GameService;
import service.ParentService;
import service.UserService;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        //make the handler
        GameDAOI gameDAOI = new MemoryGameDAO();
        UserDAOI userDAOI = new MemoryUserDAO();
        AuthDAOI authDAOI = new MemoryAuthDAO();

        ParentService parentService = new ParentService(authDAOI, gameDAOI, userDAOI);
        GameService gameService = new GameService(authDAOI, gameDAOI);
        UserService userService = new UserService(authDAOI, userDAOI);
        Handler handler = new Handler(parentService, gameService, userService);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", (handler::clearApplication));
        Spark.post("/user", (handler::register));
        Spark.post("/session", (handler::login));
        Spark.delete("/session", handler::logout);
        Spark.get("/game", handler::listGames);
        Spark.post("/game", handler::createGame);
        Spark.put("/game", handler::joinGame);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
