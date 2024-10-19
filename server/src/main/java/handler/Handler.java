package handler;

import java.util.ArrayList;
import java.util.Map;

import chess.ChessGame;
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
            res.body(null);
            return null;
        } catch (Exception e) {
            return ExceptionCatcher(e, req, res);
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
            return ExceptionCatcher(e, req, res);
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
            return ExceptionCatcher(e, req, res);
        }
    }

    //LOGOUT
    public Object Logout(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            userService.logout(authToken);
            res.body(gson.toJson(null));
            return gson.toJson(null);
        }
        catch (Exception e) {
            return ExceptionCatcher(e, req, res);
        }
    }

    //LIST GAMES
    public Object ListGames(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            ArrayList<GameData> list = gameService.ListGames(authToken);
            String body = "games" + gson.toJson(list);
            res.body(body);
            return body;
        }
        catch (Exception e) {
            return ExceptionCatcher(e, req, res);
        }
    }

    //CREATE GAME
    public Object CreateGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            CreateGameRequest request = new CreateGameRequest(authToken, req.body());
            ChessGame game = gameService.CreateGame(request);
            String body = gson.toJson(game);
            res.body(body);
            return body;
        } catch (Exception e) {
            return ExceptionCatcher(e, req, res);
        }
    }

    //JOIN GAME
    public Object JoinGame(Request req, Response res) {
        try  {
            String authToken = req.headers("authorization");
            JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
            gameService.JoinGame(authToken, request);
            res.body(gson.toJson(null));
            return gson.toJson(null);
        } catch (Exception e) {
            return ExceptionCatcher(e, req, res);
        }
    }


    public Object ExceptionCatcher(Exception e, Request req, Response res) {
        String body = gson.toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        if (e.getMessage().equals("unauthorized")) {
            res.status(401);
        }
        else if (e.getMessage().equals("already taken")) {
            res.status(403);
        }
        else if (e.getMessage().equals("bad request")) {
            res.status(400);
        }
        else {
            res.status(500);
        }
        res.body(body);
        return body;
    } //womp womp


    //res.status 401
}