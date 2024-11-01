package handler;

import java.util.ArrayList;
import java.util.Map;

import com.google.gson.Gson;

import com.google.gson.JsonObject;
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
    public Object clearApplication(Request req, Response res) {
        try {
            parentService.clearApplication();
            res.body("{}");
            return "{}";
        } catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //REGISTER
    public Object register(Request req, Response res) {
        try {
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = userService.register(user);
            res.body(gson.toJson(auth));
            return gson.toJson(auth);
        }
        catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //LOGIN
    public Object login(Request req, Response res) {
        try { //to get auth header its req.headers("authorization") -> results in a single string
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = userService.login(user);
            res.body(gson.toJson(auth));
            return gson.toJson(auth);
        }
        catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //LOGOUT
    public Object logout(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            userService.logout(authToken);
            res.body("{}");
            return "{}";
        }
        catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //LIST GAMES
    public Object listGames(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            ArrayList<GameData> list = gameService.listGames(authToken);
            ListGamesResponse response = new ListGamesResponse(list);
            res.body(gson.toJson(response));
            return gson.toJson(response);
        }
        catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //CREATE GAME
    public Object createGame(Request req, Response res) {
        try {
            String authToken = req.headers("authorization");
            JsonObject gameName = new Gson().fromJson(req.body(), JsonObject.class);
            gameName.get("gameName");
            String reqBod = gameName.get("gameName").getAsString();

            CreateGameRequest request = new CreateGameRequest(authToken, reqBod);
            GameData game = gameService.createGame(request);
            CreateGameResponse response = new CreateGameResponse(game.gameID());
            res.body(gson.toJson(response));
            return gson.toJson(response);
        } catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }

    //JOIN GAME
    public Object joinGame(Request req, Response res) {
        try  {
            String authToken = req.headers("authorization");
            JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);
            gameService.joinGame(authToken, request);
            res.body("{}");
            return "{}";
        } catch (Exception e) {
            return exceptionCatcher(e, res);
        }
    }


    public Object exceptionCatcher(Exception e, Response res) {
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