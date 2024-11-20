package handler;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;

public class WebsocketHandler {

    @WebSocket
    public static class websocketHandler {
        public static void main(String[] args) {
            Spark.port(8080);
            Spark.webSocket("/connect", websocketHandler.class);
            Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
        }

        @OnWebSocketMessage
        public void onMessage(Session session, String userGameCommand) throws Exception {

            session.getRemote().sendString("WebSocket response: " + userGameCommand);
        }
    }

}
