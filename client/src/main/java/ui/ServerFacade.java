package ui;

import chess.ChessMove;
import com.google.gson.Gson;
import model.UserData;
import websocket.commands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ServerFacade {
    ClientCommunicator clientCom;
    WebsocketCommunicator webCom;
    String urlString;

    public ServerFacade(String urlString, ServerMessageObserver serverMessageObserver) {
        clientCom = new ClientCommunicator();
        try {
            webCom = new WebsocketCommunicator(serverMessageObserver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.urlString = urlString;
    }

    public void clear(String destroy) {
        try{
            webCom.send(destroy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String register(String username, String password, String email) throws IOException {
        UserData newUser = new UserData(username, password, email);

        ArrayList<String> name = clientCom.post((urlString + "/user"), newUser, null, false);

        if (Objects.equals(name.getFirst(), username)) {
            return ("GOOD" + name.get(1));
        }
        else {
            return name.getFirst();
        }
    }

    public String login(String username, String password) throws IOException {
        UserData newUser = new UserData(username, password, null);

        ArrayList<String> name = clientCom.post((urlString + "/session"), newUser, null, false);

        if (Objects.equals(name.getFirst(), username)) {
            return ("GOOD" + name.get(1));
        }
        else {
            return name.getFirst();
        }
    }

    public String logout(String authToken) throws IOException {
        String response = clientCom.delete((urlString + "/session"), authToken);

        if (Objects.equals(response, "{}")) {
            return "GOOD";
        }
        else {
            return response;
        }
    }

    public ArrayList<String> listGames(String authToken) throws IOException {
        return clientCom.get((urlString + "/game"), authToken);
    }

    public String createGame(String gameName, String authToken) throws IOException {
        ArrayList<String> response = clientCom.post((urlString + "/game"), gameName, authToken, true);

        if (response.getFirst().equals(gameName)) {
            return ("GOOD");
        }
        else {
            return response.getFirst();
        }
    }

    public String joinGame(String playerColor, int gameID, String authToken) throws IOException {
        String response;
        if (Objects.equals(playerColor, "OBSERVER")){
            response = clientCom.put((urlString + "/game"), "WHITE", gameID, authToken);
        }
        else{
            response = clientCom.put((urlString + "/game"), playerColor, gameID, authToken);
        }

        if (Objects.equals(response, "{}")) {
            //websocket
            ConnectCommand connectCommand = new ConnectCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
            String connect = new Gson().toJson(connectCommand);
            try{
                webCom.send(connect);
            } catch (Exception e) {
                throw new RuntimeException(e); //update to correct error in the future.
            }
            return "GOOD";
        }
        else {
            return response;
        }
    }

    public void makeMove(ChessMove move, int gameID, String auth, String moveText) throws Exception {
        MakeMoveCommand moveCommand = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, auth, gameID, move, moveText);
        String command = new Gson().toJson(moveCommand);
        webCom.send(command);
    }

    public void leaveGame(int gameID, String auth) throws Exception {
        LeaveCommand leaveCommand = new LeaveCommand(UserGameCommand.CommandType.LEAVE, auth, gameID);
        String command = new Gson().toJson(leaveCommand);
        webCom.send(command);
    }

    public void resign(int gameID, String auth) throws Exception {
        ResignCommand resignCommand = new ResignCommand(UserGameCommand.CommandType.RESIGN, auth, gameID);
        String command = new Gson().toJson(resignCommand);
        webCom.send(command);
    }

    public void observe(int gameID, String authToken) {
        ConnectCommand connectCommand = new ConnectCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID);
        String connect = new Gson().toJson(connectCommand);
        try{
            webCom.send(connect);
        } catch (Exception e) {
            System.out.println("i'm in the server facade");
            throw new RuntimeException(e); //update to correct error in the future.
        }
    }

}
