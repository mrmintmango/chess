package websocket.messages;

import chess.ChessBoard;

public class LoadGameMessage extends ServerMessage{
    public ChessBoard board;

    public LoadGameMessage(ServerMessageType type, ChessBoard board) {
        super(type);
        this.board = board;
    }

    public ChessBoard getBoard(){
        return board;
    }
}
