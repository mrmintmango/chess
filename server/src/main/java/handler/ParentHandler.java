package handler;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dataaccess.DataAccessException;
import model.AuthData;
import model.JoinGameRequest;
import model.UserData;
import spark.*;
import service.GameService;
import service.ParentService;
import service.UserService;

public class ParentHandler {
    private Gson gson;
    private final ParentService parentService;
    private final GameService gameService;
    private final UserService userService;

    public ParentHandler(ParentService parentService, GameService gameService, UserService userService){
        this.parentService = parentService;
        this.gameService = gameService;
        this.userService = userService;
        this.gson = new Gson();
    }

    //LOGIN
    public Object Login(Request req, Response res) {
        try { //to get auth header its req.headers("authorization") -> results in a single string
            UserData user = gson.fromJson(req.body(), UserData.class);
            AuthData auth = userService.login(user);
            res.body(gson.toJson(auth));
            return gson.toJson(auth);
        } catch (Exception e) {
            return gson.toJson(); // start from here and maybe the server too.
        }

    }

    public String LoginToJson(UserData user) throws DataAccessException {
        AuthData auth;
        try {
            auth = userService.login(user);
        } catch (DataAccessException e) {
            return gson.toJson(e);
        }
        return gson.toJson(auth);
    }

    public UserData LoginFromJson(String message){
        try {
            return gson.fromJson(message, UserData.class);
        }
        catch (JsonSyntaxException e){
            //maybe throw an exception?
            return null;
        }
    }
    //res.status 401

    //LOGOUT
    public String LogoutToJson(AuthData auth) throws DataAccessException {
        try{
            userService.logout(auth);
        } catch (DataAccessException e) {
            return gson.toJson(e);
        }
        return gson.toJson(null);
    }

    public AuthData LogoutFromJson(String message) {
        try {
            return gson.fromJson(message, AuthData.class);
        }
        catch (JsonSyntaxException e){
            //maybe throw an exception?
            return null;
        }
    }


    //REGISTER
    public String RegisterToJson(UserData user) throws DataAccessException {
        AuthData auth = userService.register(user);
        return gson.toJson(auth);
    }

    public UserData RegisterFromJson(String message){
        try {
            return gson.fromJson(message, UserData.class);
        }
        catch (JsonSyntaxException e){
            //maybe throw an exception?
            return null;
        }
    }

    //JOIN GAME
    public String JoinGameToJson(JoinGameRequest request) throws DataAccessException {
        try{
            gameService.JoinGame(request);
        } catch (DataAccessException e) {
            return gson.toJson(e);
        }
        return null;
    }

    public JoinGameRequest JoinGameFromJson(String message){
        try {
            return gson.fromJson(message, JoinGameRequest.class);
        }
        catch (JsonSyntaxException e){
            //maybe throw an exception?
            return null;
        }
    }

    //CLEAR APPLICATION
    public String ClearApplicationToJson() {
        parentService.ClearApplication();
        return gson.toJson(null);
        //might need to implement a failure response
    }

    public void ClearApplicationFromJson() {
        //???
    }



    public void ListGamesHandler() {}

    public void CreateGameHandler() {}
}
