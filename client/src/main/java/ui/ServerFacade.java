package ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ServerFacade {
    ClientCommunicator clientCom;

    public ServerFacade(){
        clientCom = new ClientCommunicator();
    }

    public void clear() {}

    public String register(String username, String password, String email) throws IOException {
        //first create a connection
        HttpURLConnection http;
        try {
            http = clientCom.post("localhost:8080");
        }
        catch (IOException e) {
            return e.getMessage();
        }

        http.connect();

//        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
//            InputStream responseBody = connection.getInputStream();
//            // Read and process response body from InputStream ...
//
//        } else {
//            // SERVER RETURNED AN HTTP ERROR
//            InputStream responseBody = connection.getErrorStream();
//            // Read and process error response body from InputStream ...
//            throw new IOException("HTTP ERROR");
//        }

        //Then make the request for the actual server

        return "GOOD";
    }

    public void login() {

    }

    public void logout() {}

    public void listGames() {}

    public void createGame() {}

    public void joinGame() {}
}
