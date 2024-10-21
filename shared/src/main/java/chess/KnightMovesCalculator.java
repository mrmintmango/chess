package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator extends ChessMovesCalculator {
    ChessGame.TeamColor teamColor;
    Collection<ChessMove> moves;
    public KnightMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        // up right moves
        if (up >= 2 && right == 1) {
            movement(2,1,myPosition,board);
        }
        if (up == 1 && right > 1) {
            movement(1,2,myPosition,board);
        }
        if (up >= 2 && right > 1) {
            movement(2,1,myPosition,board);
            movement(1,2,myPosition,board);
        }

        // up left moves
        if (up >= 2 && left == 1) {
            movement(2,-1,myPosition,board);
        }
        if (up == 1 && left > 1) {
            movement(1,-2,myPosition,board);
        }
        if (up >= 2 && left > 1) {
            movement(2,-1,myPosition,board);
            movement(1,-2,myPosition,board);
        }

        // down left moves
        if (down >= 2 && left == 1) {
            movement(-2,-1,myPosition,board);
        }
        if (down == 1 && left > 1) {
            movement(-1,-2,myPosition,board);
        }
        if (down >= 2 && left > 1) {
            movement(-2,-1,myPosition,board);
            movement(-1,-2,myPosition,board);
        }

        // down right moves
        if (down >= 2 && right == 1) {
            movement(-2,1,myPosition,board);
        }
        if (down == 1 && right > 1) {
            movement(-1,2,myPosition,board);
        }
        if (down >= 2 && right > 1) {
            movement(-2,1,myPosition,board);
            movement(-1,2,myPosition,board);
        }

        return moves;
    }

    public void movement(int row, int col, ChessPosition myPosition, ChessBoard board) {
        ChessPosition move = new ChessPosition(myPosition.getRow()+row, myPosition.getColumn()+col);
        if (board.getPiece(move) == null || board.getPiece(move).getTeamColor() != teamColor){
            ChessMove e = new ChessMove(myPosition, move,null);
            moves.add(e);
        }
    }

}
