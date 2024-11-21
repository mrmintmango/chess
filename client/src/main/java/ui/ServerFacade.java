package ui;

import model.UserData;

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

    //public void clear() {}

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
        String response = clientCom.put((urlString + "/game"), playerColor, gameID, authToken);



        if (Objects.equals(response, "{}")) {
            return "GOOD";
        }
        else {
            return response;
        }
    }
}
