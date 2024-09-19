package chess;

import java.util.ArrayList;
import java.util.Collection;

public class bishopMovesCalculator extends ChessMovesCalculator {
    ChessGame.TeamColor teamColor;
    bishopMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //Check up right move if bishop is closer to the top
        if (up < right) {
            for (int i = 1; i <= up; i++) {
                ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
                if (board.getPiece(oneUpRight) == null){
                    ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneUpRight).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneUpRight).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }
        //Check up right move if bishop is closer to the right
        if (up >= right) {
            for (int i = 1; i <= right; i++) {
                ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()+i);
                if (board.getPiece(oneUpRight) == null){
                    ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneUpRight).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneUpRight).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }

        //Check up left move if bishop is closer to the top
        if (up < left) {
            for (int i = 1; i <= up; i++) {
                ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
                if (board.getPiece(oneUpLeft) == null){
                    ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneUpLeft).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneUpLeft).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }
        //Check up left move if bishop is closer to the left
        if (left <= up) {
            for (int i = 1; i <= left; i++) {
                ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+i, myPosition.getColumn()-i);
                if (board.getPiece(oneUpLeft) == null){
                    ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneUpLeft).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneUpLeft).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }

        //Check down left move if bishop is closer to the bottom
        if (down < left) {
            for (int i = 1; i <= down; i++) {
                ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
                if (board.getPiece(oneDownLeft) == null){
                    ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneDownLeft).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneDownLeft).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }
        //Check down left move if bishop is closer to the left
        if (left <= down) {
            for (int i = 1; i <= left; i++) {
                ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()-i);
                if (board.getPiece(oneDownLeft) == null){
                    ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneDownLeft).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneDownLeft).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }

        //Check down right move if bishop is closer to the bottom
        if (down < right) {
            for (int i = 1; i <= down; i++) {
                ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
                if (board.getPiece(oneDownRight) == null){
                    ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneDownRight).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneDownRight).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }
        //Check down right move if bishop is closer to the right
        if (right <= down) {
            for (int i = 1; i <= right; i++) {
                ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-i, myPosition.getColumn()+i);
                if (board.getPiece(oneDownRight) == null){
                    ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(oneDownRight).getTeamColor() != this.teamColor) {
                    ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                    moves.add(e);
                    break;
                }
                else if (board.getPiece(oneDownRight).getTeamColor() == this.teamColor) {
                    break;
                }
            }
        }

        return moves;
    }

}
