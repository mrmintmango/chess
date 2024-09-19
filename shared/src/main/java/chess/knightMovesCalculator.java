package chess;

import java.util.ArrayList;
import java.util.Collection;

public class knightMovesCalculator extends ChessMovesCalculator {
    public knightMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        // up right moves
        if (up >= 2 && right == 1) {
            ChessPosition twoUpRight = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1);
            if (board.getPiece(twoUpRight) == null || board.getPiece(twoUpRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoUpRight,null);
                moves.add(e);
            }
        }
        if (up == 1 && right > 1) {
            ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2);
            if (board.getPiece(oneUpRight) == null || board.getPiece(oneUpRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                moves.add(e);
            }
        }
        if (up >= 2 && right > 1) {
            ChessPosition twoUpRight = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1);
            if (board.getPiece(twoUpRight) == null || board.getPiece(twoUpRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoUpRight,null);
                moves.add(e);
            }
            ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2);
            if (board.getPiece(oneUpRight) == null || board.getPiece(oneUpRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                moves.add(e);
            }
        }

        // up left moves
        if (up >= 2 && left == 1) {
            ChessPosition twoUpLeft = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1);
            if (board.getPiece(twoUpLeft) == null || board.getPiece(twoUpLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoUpLeft,null);
                moves.add(e);
            }
        }
        if (up == 1 && left > 1) {
            ChessPosition twoUpLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2);
            if (board.getPiece(twoUpLeft) == null || board.getPiece(twoUpLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoUpLeft,null);
                moves.add(e);
            }
        }
        if (up >= 2 && left > 1) {
            ChessPosition twoUpLeft = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1);
            if (board.getPiece(twoUpLeft) == null || board.getPiece(twoUpLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoUpLeft,null);
                moves.add(e);
            }
            ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2);
            if (board.getPiece(oneUpLeft) == null || board.getPiece(oneUpLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                moves.add(e);
            }
        }

        // down left moves
        if (down >= 2 && left == 1) {
            ChessPosition twoDownLeft = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1);
            if (board.getPiece(twoDownLeft) == null || board.getPiece(twoDownLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoDownLeft,null);
                moves.add(e);
            }
        }
        if (down == 1 && left > 1) {
            ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2);
            if (board.getPiece(oneDownLeft) == null || board.getPiece(oneDownLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                moves.add(e);
            }
        }
        if (down >= 2 && left > 1) {
            ChessPosition twoDownLeft = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1);
            if (board.getPiece(twoDownLeft) == null || board.getPiece(twoDownLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoDownLeft,null);
                moves.add(e);
            }
            ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2);
            if (board.getPiece(oneDownLeft) == null || board.getPiece(oneDownLeft).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                moves.add(e);
            }
        }

        // down right moves
        if (down >= 2 && right == 1) {
            ChessPosition twoDownRight = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1);
            if (board.getPiece(twoDownRight) == null || board.getPiece(twoDownRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoDownRight,null);
                moves.add(e);
            }
        }
        if (down == 1 && right > 1) {
            ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2);
            if (board.getPiece(oneDownRight) == null || board.getPiece(oneDownRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                moves.add(e);
            }
        }
        if (down >= 2 && right > 1) {
            ChessPosition twoDownRight = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1);
            if (board.getPiece(twoDownRight) == null || board.getPiece(twoDownRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, twoDownRight,null);
                moves.add(e);
            }
            ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2);
            if (board.getPiece(oneDownRight) == null || board.getPiece(oneDownRight).getTeamColor() != teamColor){
                ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                moves.add(e);
            }
        }

        return moves;
    }


}
