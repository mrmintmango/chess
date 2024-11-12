package ui;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientCommunicator {

    public ClientCommunicator() {}

    public void delete() {}

    public InputStream post(String urlString, Object requestInfo) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        connection.connect();

        try(OutputStream requestBody = connection.getOutputStream();) {
            //connection.addRequestProperty("Content-Type", "application/json");  I think this is for headers
            String reqData = new Gson().toJson(requestInfo);
            requestBody.write(reqData.getBytes());
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

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            // Get HTTP response headers, if necessary
            // Map<String, List<String>> headers = connection.getHeaderFields();

            // OR

            connection.getHeaderField("Content-Length");

            InputStream responseBody = connection.getInputStream();
            // Read response body from InputStream ...
            return responseBody;
        }
        else {
            // SERVER RETURNED AN HTTP ERROR

            InputStream responseBody = connection.getErrorStream();
            // Read and process error response body from InputStream ...
            return responseBody;
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
