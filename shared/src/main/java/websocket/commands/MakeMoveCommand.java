package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    ChessMove move;
    String moveText;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move, String moveText) {
        super(commandType, authToken, gameID);
        this.move = move;
        this.moveText = moveText;
    }

    public ChessMove getMove(){
        return move;
    }

    public String getMoveText(){
        return moveText;
    }

}
