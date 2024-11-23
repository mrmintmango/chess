package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    ChessMove move;
    String moveText;
    Boolean resigned;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move, String moveText, Boolean resigned) {
        super(commandType, authToken, gameID);
        this.move = move;
        this.moveText = moveText;
        this.resigned = resigned;
    }

    public ChessMove getMove(){
        return move;
    }

    public String getMoveText(){
        return moveText;
    }

    public boolean getResigned(){
        return resigned;
    }

}
