package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class ClientCommunicator {

    public ClientCommunicator() {}

    public String delete(String urlString, Object deleteInfo) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        connection.addRequestProperty("authorization", deleteInfo.toString());

        return getString(connection);
    }

    private String getString(HttpURLConnection connection) throws IOException {
        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...

            return new String(responseBody.readAllBytes());
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            Map error = new Gson().fromJson(reader, Map.class);
            return error.get("message").toString();
        }
    }

    public ArrayList<String> post(String urlString, Object requestInfo, String headerInfo, boolean isGame) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        if (headerInfo != null){
            connection.addRequestProperty("authorization", headerInfo);

            try(OutputStream requestBody = connection.getOutputStream()) {
                JsonObject reqData = new JsonObject();
                reqData.addProperty("gameName", requestInfo.toString());
                requestBody.write(reqData.toString().getBytes());
            }
        }
        else {
            try(OutputStream requestBody = connection.getOutputStream()) {
                String reqData = new Gson().toJson(requestInfo);
                requestBody.write(reqData.getBytes());
            }
        }

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            ArrayList<String> answer = new ArrayList<>();
            if (isGame){
                CreateGameResponse response = new Gson().fromJson(reader, CreateGameResponse.class);
                answer.add(requestInfo.toString());
                answer.add("" + response.gameID());
            }
            else {
                AuthData response = new Gson().fromJson(reader, AuthData.class);
                answer.add(response.username());
                answer.add(response.authToken());
            }
            return answer;
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            Map error = new Gson().fromJson(reader, Map.class);
            ArrayList<String> answer = new ArrayList<>();
            answer.add(error.get("message").toString());
            return answer;
        }
    }

    public ArrayList<String> get(String urlString, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        connection.addRequestProperty("authorization", authToken);

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            ArrayList<String> answer = new ArrayList<>();

            ListGamesResponse response = new Gson().fromJson(reader, ListGamesResponse.class);
            for (int i = 0; i < response.games().size(); i++){
                answer.add(String.valueOf(response.games().get(i).gameID()));
                answer.add(response.games().get(i).gameName());
                answer.add(response.games().get(i).whiteUsername());
                answer.add(response.games().get(i).blackUsername());
            }
            return answer;
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            Map error = new Gson().fromJson(reader, Map.class);
            ArrayList<String> answer = new ArrayList<>();
            answer.add("error");
            answer.add(error.get("message").toString());
            return answer;
        }
    }

    public String put(String urlString, String playerColor, int gameID, String authToken) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);

        connection.addRequestProperty("authorization", authToken);

        try (OutputStream requestBody = connection.getOutputStream()) {
            JoinGameRequest request = new JoinGameRequest(playerColor, gameID);
            String reqData = new Gson().toJson(request);
            requestBody.write(reqData.getBytes());
        }

        return getString(connection);
    }
}
