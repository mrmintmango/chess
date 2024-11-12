package ui;

import model.AuthData;
import model.UserData;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Objects;

public class ServerFacade {
    ClientCommunicator clientCom;
    String urlString;

    public ServerFacade(String urlString){
        clientCom = new ClientCommunicator();
        this.urlString = urlString;
    }

    public void clear() {}

    public String register(String username, String password, String email) throws IOException {
        UserData newUser = new UserData(username, password, email);

        //first create a connection
        HttpURLConnection http;
        try {
            String name = clientCom.post(urlString, newUser);

            if (Objects.equals(name, username)) {
                return "GOOD";
            }
            else {
                return name;
            }
        }
        catch (IOException e) {
            return e.getMessage();
        }
        //return "GOOD";
    }

    public void login() {}

    public void logout() {}

    public void listGames() {}

    public void createGame() {}

    public void joinGame() {}
}
