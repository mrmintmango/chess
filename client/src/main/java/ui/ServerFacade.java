package ui;

import model.AuthData;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Objects;

public class ServerFacade {
    ClientCommunicator clientCom;
    String urlString;

    public ServerFacade(String urlString){
        clientCom = new ClientCommunicator();
        this.urlString = urlString;
    }

    //public void clear() {}

    public String register(String username, String password, String email) throws IOException {
        UserData newUser = new UserData(username, password, email);

        //first create a connection
        HttpURLConnection http;
        ArrayList<String> name = clientCom.post((urlString + "/user"), newUser, null, false);

        if (Objects.equals(name.getFirst(), username)) {
            return "GOOD";
        }
        else {
            return name.getFirst();
        }
    }

    public String login(String username, String password) throws IOException {
        UserData newUser = new UserData(username, password, null);

        //first create a connection
        HttpURLConnection http;
        ArrayList<String> name = clientCom.post((urlString + "/session"), newUser, null, false);

        if (Objects.equals(name.getFirst(), username)) {
            return ("GOOD" + name.get(1));
        }
        else {
            return name.getFirst();
        }
    }

    public String logout(String authToken) throws IOException {
        HttpURLConnection http;
        String response = clientCom.delete((urlString + "/session"), authToken);

        if (Objects.equals(response, "{}")) {
            return "GOOD";
        }
        else {
            return response;
        }
    }

    public ArrayList<String> listGames(String authToken) throws IOException {
        HttpURLConnection http;
        ArrayList<String> response = clientCom.get((urlString + "/game"), authToken);
        return response;

    }

    public String createGame(String gameName, String authToken) throws IOException {
        HttpURLConnection http;
        ArrayList<String> response = clientCom.post((urlString + "/game"), gameName, authToken, true);

        if (response.getFirst().equals(gameName)) {
            return ("GOOD");
        }
        else {
            return response.getFirst();
        }
    }

    public void joinGame() {}
}
