package chess;

import java.util.ArrayList;
import java.util.Collection;

public class queenMovesCalculator extends ChessMovesCalculator {
    public queenMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
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

        //Basically Copied the Bishop Move code below:

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
