package chess;

import java.util.ArrayList;
import java.util.Collection;

public class kingMovesCalculator extends ChessMovesCalculator {

    kingMovesCalculator() {
        super();
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check up move
        if (myPosition.getRow() != 8) {
            ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            if (board.getPiece(oneUp).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
            }
        }

        //Check down move
        if (myPosition.getRow() != 1) {
            ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            if (board.getPiece(oneDown).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDown,null);
                moves.add(e);
            }
        }

        //Check Left move
        if (myPosition.getColumn() != 1) {
            ChessPosition oneLeft = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
            if (board.getPiece(oneLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneLeft,null);
                moves.add(e);
            }
        }

        //Check Right move
        if (myPosition.getColumn() != 8) {
            ChessPosition oneRight = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);
            if (board.getPiece(oneRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneRight,null);
                moves.add(e);
            }
        }

        //Check Up Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 8) {
            ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
            if (board.getPiece(oneUpRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                moves.add(e);
            }
        }

        //Check Down Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 1) {
            ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            if (board.getPiece(oneDownRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                moves.add(e);
            }
        }

        //Check Up Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 8) {
            ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            if (board.getPiece(oneUpLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                moves.add(e);
            }
        }

        //Check Down Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 1) {
            ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            if (board.getPiece(oneDownLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                moves.add(e);
            }
        }

        return moves;
    }
}

