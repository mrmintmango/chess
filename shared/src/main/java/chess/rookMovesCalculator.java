package chess;

import java.util.ArrayList;
import java.util.Collection;

public class rookMovesCalculator extends ChessMovesCalculator {
    public rookMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //check up
        for (int i = 1; i <= up; i++) {
            ChessPosition oneUp = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn());
            if (board.getPiece(oneUp) == null){
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() == teamColor) {
                break;
            }
        }

        //check down
        for (int i = 1; i <= down; i++) {
            ChessPosition oneDown = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn());
            if (board.getPiece(oneDown) == null){
                ChessMove e = new ChessMove(myPosition, oneDown,null);
                moves.add(e);
            }
            if (board.getPiece(oneDown) != null && board.getPiece(oneDown).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneDown,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneDown) != null && board.getPiece(oneDown).getTeamColor() == teamColor) {
                break;
            }
        }

        //check left
        for (int i = 1; i <= left; i++) {
            ChessPosition oneLeft = new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i);
            if (board.getPiece(oneLeft) == null){
                ChessMove e = new ChessMove(myPosition, oneLeft,null);
                moves.add(e);
            }
            if (board.getPiece(oneLeft) != null && board.getPiece(oneLeft).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneLeft,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneLeft) != null && board.getPiece(oneLeft).getTeamColor() == teamColor) {
                break;
            }
        }

        //check Right
        for (int i = 1; i <= right; i++) {
            ChessPosition oneRight = new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i);
            if (board.getPiece(oneRight) == null){
                ChessMove e = new ChessMove(myPosition, oneRight,null);
                moves.add(e);
            }
            if (board.getPiece(oneRight) != null && board.getPiece(oneRight).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneRight,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneRight) != null && board.getPiece(oneRight).getTeamColor() == teamColor) {
                break;
            }
        }


        return moves;
    }

}
