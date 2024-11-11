package ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ServerFacade {
    ClientCommunicator clientCom;
    String urlString;

    public ServerFacade(String urlString){
        clientCom = new ClientCommunicator();
        this.urlString = urlString;
    }

    public void clear() {}

    public String register(String username, String password, String email) throws IOException {
        //first create a connection
        HttpURLConnection http;
        try {
            InputStream inputStream = clientCom.post(urlString);

            String input = String.valueOf(inputStream.read());
        }
        catch (IOException e) {
            return e.getMessage();
        }
        return "GOOD";
    }

    public void login() {

    }

    public void logout() {}

    public void listGames() {}

    public void createGame() {}

    public void joinGame() {}
}