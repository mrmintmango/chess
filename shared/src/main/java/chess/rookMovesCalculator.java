package chess;

import java.util.ArrayList;
import java.util.Collection;

public class rookMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    public rookMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //check up
        addMove(up,myPosition,board,1);

        //check down
        addMove(down,myPosition,board,2);

        //check left
        addMove(left,myPosition,board,3);

        //check Right
        addMove(right,myPosition,board,4);

        return moves;
    }

    public void addMove(int iter, ChessPosition myPosition, ChessBoard board, int direction) {
        for (int i = 1; i <= iter; i++) {
            ChessPosition oneUp = cPos(myPosition, i, direction);
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
    }

    public ChessPosition cPos(ChessPosition myPosition, int iter, int direction) {
        if (direction == 1){
            return new ChessPosition(myPosition.getRow()+iter, myPosition.getColumn());
        }
        else if (direction == 2){
            return new ChessPosition(myPosition.getRow()-iter, myPosition.getColumn());
        }
        else if (direction == 3){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()-iter);
        }
        else if (direction == 4){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()+iter);
        }
        else return null;
    }

}
