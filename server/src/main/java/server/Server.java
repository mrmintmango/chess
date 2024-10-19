package server;

import dataaccess.*;
import handler.ParentHandler;
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
        ParentHandler handler = new ParentHandler(parentService, gameService, userService);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //(request, response) -> handler.Login(request, response))

        Spark.delete("/db", (handler::ClearApplication));
        Spark.post("/session", (handler::Login)); //LOGIN

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
