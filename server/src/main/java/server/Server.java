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
        Spark.delete("/db", (handler::ClearApplication));
        Spark.post("/user", (handler::Register));
        Spark.post("/session", (handler::Login));
        Spark.delete("/session", handler::Logout);
        Spark.get("/game", handler::ListGames);
        Spark.post("/game", handler::CreateGame);
        Spark.put("/game", handler::JoinGame);

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
