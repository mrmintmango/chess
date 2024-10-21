package handler;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;
import model.*;
import spark.*;
import service.GameService;
import service.ParentService;
import service.UserService;

public class Handler {
    private final Gson gson;
    private final ParentService parentService;
    private final GameService gameService;
    private final UserService userService;

    public Handler(ParentService parentService, GameService gameService, UserService userService) {
        this.parentService = parentService;
        this.gameService = gameService;
        this.userService = userService;
        this.gson = new Gson();
    }

    //CLEAR APPLICATION
    public Object ClearApplication(Request req, Response res) {
        try {
            parentService.ClearApplication();
            res.body("{}");
            return "{}";
        } catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //REGISTER
    public Object Register(Request req, Response res) {
        try {
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = userService.register(user);
            res.body(gson.toJson(auth));
            return gson.toJson(auth);
        }
        catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //LOGIN
    public Object Login(Request req, Response res) {
        try { //to get auth header its req.headers("authorization") -> results in a single string
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = userService.login(user);
            res.body(gson.toJson(auth));
            return gson.toJson(auth);
        }
        catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //LOGOUT
    public Object Logout(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            userService.logout(authToken);
            res.body("{}");
            return "{}";
        }
        catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //LIST GAMES
    public Object ListGames(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            ArrayList<GameData> list = gameService.ListGames(authToken);
            ListGamesResponse response = new ListGamesResponse(list);
            res.body(gson.toJson(response));
            return gson.toJson(response);
        }
        catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //CREATE GAME
    public Object CreateGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            CreateGameRequest request = new CreateGameRequest(authToken, req.body());
            GameData game = gameService.CreateGame(request);
            CreateGameResponse response = new CreateGameResponse(game.gameID());
            res.body(gson.toJson(response));
            return gson.toJson(response);
        } catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }

    //JOIN GAME
    public Object JoinGame(Request req, Response res) {
        try  {
            String authToken = req.headers("authorization");
            JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
            gameService.JoinGame(authToken, request);
            res.body("{}");
            return "{}";
        } catch (Exception e) {
            return ExceptionCatcher(e, res);
        }
    }


    public Object ExceptionCatcher(Exception e, Response res) {
        String body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        if (e.getMessage().equals("unauthorized")) {
            res.status(401);
        }
        else if (e.getMessage().equals("already taken")) {
            res.status(403);
        }
        else if (e.getMessage().equals("Incorrect Password")) {
            res.status(401);
        }
        else if (e.getMessage().equals("bad request")) {
            res.status(400);
        }
        else {
            res.status(500);
        }
        res.body(body);
        return body;
    }


    //res.status 401
}