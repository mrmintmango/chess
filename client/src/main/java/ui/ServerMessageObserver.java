package ui;

import websocket.messages.ServerMessage;

public interface ServerMessageObserver {

    default void notify(ServerMessage message){}
}
