package ui;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import spark.utils.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

public class ClientCommunicator {

    public ClientCommunicator() {}

    public void delete() {
        //            if (requestBody != null) {  // this is only for other ones
//                //maybe alter the request property with stuff for the endpoints???
//                connection.addRequestProperty("Content-Type", "application/json");
//                String reqData = new Gson().toJson(requestBody);
//                try (OutputStream reqBody = connection.getOutputStream()) {
//                    reqBody.write(reqData.getBytes());
//                }
//            }
        // Write request body to OutputStream ...
    }

    public String post(String urlString, Object requestInfo) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try(OutputStream requestBody = connection.getOutputStream();) {
            String reqData = new Gson().toJson(requestInfo);
            requestBody.write(reqData.getBytes());
        }

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            AuthData response = new Gson().fromJson(reader, AuthData.class);
            //System.out.println(response.username());

            return response.username();
        }
        else {
            // SERVER RETURNED AN HTTP ERROR
            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            InputStreamReader reader = new InputStreamReader(responseBody);
            Map error = new Gson().fromJson(reader, Map.class);
            return error.get("message").toString();
        }

        //return null;
    }

    public void get(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET");

        // Set HTTP request headers, if necessary
        // connection.addRequestProperty("Accept", "text/html");
        // connection.addRequestProperty("Authorization", "fjaklc8sdfjklakl");

        connection.connect();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            //connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read and process response body from InputStream ...
        } else {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
        }
    }

    public void put() {}

}
