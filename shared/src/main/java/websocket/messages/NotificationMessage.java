package websocket.messages;

public class NotificationMessage extends ServerMessage{
    private String notification;

    public NotificationMessage(ServerMessageType type, String notif) {
        super(type);
        notification = notif;
    }

    public String getNotification(){
        return notification;
    }
}
