package chess;

import java.util.ArrayList;
import java.util.Collection;

public class kingMovesCalculator extends ChessMovesCalculator {
    ChessGame.TeamColor teamColor;
    Collection<ChessMove> moves;
    kingMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<ChessMove>();

        //Check up move
        if (myPosition.getRow() != 8) {
            addMove(1,0,myPosition,board);
        }

        //Check down move
        if (myPosition.getRow() != 1) {
            addMove(-1,0,myPosition,board);
        }

        //Check Left move
        if (myPosition.getColumn() != 1) {
            addMove(0,-1,myPosition,board);
        }

        //Check Right move
        if (myPosition.getColumn() != 8) {
            addMove(0,1,myPosition,board);
        }

        //Check Up Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 8) {
            addMove(1,1,myPosition,board);
        }

        //Check Down Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 1) {
            addMove(-1,1,myPosition,board);
        }

        //Check Up Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 8) {
            addMove(1,-1,myPosition,board);
        }

        //Check Down Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 1) {
            addMove(-1,-1,myPosition,board);
        }

        return moves;
    }

    public void addMove(int row, int column, ChessPosition myPosition, ChessBoard board) {
        ChessPosition move = new ChessPosition(myPosition.getRow()+row, myPosition.getColumn()+column);
        if (board.getPiece(move) == null || board.getPiece(move).getTeamColor() != this.teamColor){
            ChessMove e = new ChessMove(myPosition, move,null);
            moves.add(e);
        }
    }
}

