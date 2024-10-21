package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.RookMovesCalculator.getChessPositionRook;

public class QueenMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    public QueenMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //Check up right move if bishop is closer to the top
        if (up < right) {
            addMoves(up,1,1,myPosition,board);
        }
        //Check up right move if bishop is closer to the right
        if (up >= right) {
            addMoves(right,1,1,myPosition,board);
        }
        //Check up left move if bishop is closer to the top
        if (up < left) {
            addMoves(up,1,-1,myPosition,board);
        }
        //Check up left move if bishop is closer to the left
        if (left <= up) {
            addMoves(left,1,-1,myPosition,board);
        }
        //Check down left move if bishop is closer to the bottom
        if (down < left) {
            addMoves(down,-1,-1,myPosition,board);
        }
        //Check down left move if bishop is closer to the left
        if (left <= down) {
            addMoves(left,-1,-1,myPosition,board);
        }
        //Check down right move if bishop is closer to the bottom
        if (down < right) {
            addMoves(down,-1,1,myPosition,board);
        }
        //Check down right move if bishop is closer to the right
        if (right <= down) {
            addMoves(right,-1,1,myPosition,board);
        }

        //check up
        addRMove(up,myPosition,board,1);

        //check down
        addRMove(down,myPosition,board,2);

        //check left
        addRMove(left,myPosition,board,3);

        //check Right
        addRMove(right,myPosition,board,4);

        return moves;
    }

    public void addMoves(int iter, int row, int col, ChessPosition myPosition, ChessBoard board){
        for (int i = 1; i <= iter; i++) {
            ChessPosition move = new ChessPosition(myPosition.getRow()+(row*i), myPosition.getColumn()+(col*i));
            if (board.getPiece(move) == null){
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
            }
            else if (board.getPiece(move).getTeamColor() != this.teamColor) {
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
                break;
            }
            else if (board.getPiece(move).getTeamColor() == this.teamColor) {
                break;
            }
        }
    }

    public void addRMove(int iter, ChessPosition myPosition, ChessBoard board, int direction) {
        for (int i = 1; i <= iter; i++) {
            ChessPosition oneUp = cPos(myPosition, i, direction);
            if (board.getPiece(oneUp) == null) {
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
        else {
            return null;
        }
    }

}
