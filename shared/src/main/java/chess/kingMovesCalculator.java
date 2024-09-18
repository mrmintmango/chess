package chess;

import java.util.ArrayList;
import java.util.Collection;

public class kingMovesCalculator extends ChessMovesCalculator {
    ChessGame.TeamColor teamColor;
    kingMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check up move
        if (myPosition.getRow() != 8) {
            ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
            if (board.getPiece(oneUp) == null || board.getPiece(oneUp).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
            }
        }

        //Check down move
        if (myPosition.getRow() != 1) {
            ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
            if (board.getPiece(oneDown) == null || board.getPiece(oneDown).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneDown,null);
                moves.add(e);
            }
        }

        //Check Left move
        if (myPosition.getColumn() != 1) {
            ChessPosition oneLeft = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-1);
            if (board.getPiece(oneLeft) == null || board.getPiece(oneLeft).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneLeft,null);
                moves.add(e);
            }
        }

        //Check Right move
        if (myPosition.getColumn() != 8) {
            ChessPosition oneRight = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+1);
            if (board.getPiece(oneRight) == null || board.getPiece(oneRight).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneRight,null);
                moves.add(e);
            }
        }

        //Check Up Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 8) {
            ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
            if (board.getPiece(oneUpRight) == null || board.getPiece(oneUpRight).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                moves.add(e);
            }
        }

        //Check Down Right move
        if (myPosition.getColumn() != 8 && myPosition.getRow() != 1) {
            ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
            if (board.getPiece(oneDownRight) == null || board.getPiece(oneDownRight).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                moves.add(e);
            }
        }

        //Check Up Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 8) {
            ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
            if (board.getPiece(oneUpLeft) == null || board.getPiece(oneUpLeft).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                moves.add(e);
            }
        }

        //Check Down Left move
        if (myPosition.getColumn() != 1 && myPosition.getRow() != 1) {
            ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
            if (board.getPiece(oneDownLeft) == null || board.getPiece(oneDownLeft).getTeamColor() != this.teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                moves.add(e);
            }
        }

        return moves;
    }
}

