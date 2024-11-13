package ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dataaccess.DataAccessException;
import model.AuthData;
import model.CreateGameRequest;
import model.CreateGameResponse;
import model.ListGamesResponse;
import spark.utils.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ClientCommunicator {

    public ClientCommunicator() {}

    public String delete(String urlString, Object deleteInfo) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);

        connection.addRequestProperty("authorization", deleteInfo.toString());

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            String response = new String(responseBody.readAllBytes());

            return response;
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

            try(OutputStream requestBody = connection.getOutputStream();) {
                JsonObject reqData = new JsonObject();
                reqData.addProperty("gameName", requestInfo.toString());
                requestBody.write(reqData.toString().getBytes());
            }
        }
        else {
            try(OutputStream requestBody = connection.getOutputStream();) {
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

        connection.addRequestProperty("Authorization", authToken);

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            ArrayList<String> answer = new ArrayList<>();

            ListGamesResponse response = new Gson().fromJson(reader, ListGamesResponse.class);
            for (int i = 0; i < response.games().size(); i++){
                answer.add(response.games().get(i).gameName());
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

    public void put() {}

}
