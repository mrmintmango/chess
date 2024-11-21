package websocket.messages;

import chess.ChessBoard;

public class LoadGameMessage extends ServerMessage{
    public LoadGameMessage(ServerMessageType type, ChessBoard board) {
        super(type);
    }
}
