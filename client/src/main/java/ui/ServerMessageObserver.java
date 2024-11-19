package ui;

import websocket.messages.ServerMessage;

public interface ServerMessageObserver {

    private void notify(ServerMessage message){}
}
