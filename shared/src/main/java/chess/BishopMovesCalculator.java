package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessGame.*;

public class BishopMovesCalculator extends ChessMovesCalculator {
    static TeamColor teamColor;
    static Collection<ChessMove> moves;

    public BishopMovesCalculator(TeamColor teamC) {
        super();
        teamColor = teamC;
    }

    public static Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
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

        return moves;
    }

    public static void addMoves(int iter, int row, int col, ChessPosition myPosition, ChessBoard board){
        bishopMoves(iter, row, col, myPosition, board, moves, teamColor);
    }

    static void bishopMoves(int iter, int row, int col, ChessPosition myPosition, ChessBoard board, Collection<ChessMove> moves, TeamColor teamColor) {
        for (int i = 1; i <= iter; i++) {
            ChessPosition move = new ChessPosition(myPosition.getRow()+(row*i), myPosition.getColumn()+(col*i));
            if (board.getPiece(move) == null){
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
            }
            else if (board.getPiece(move).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, move,null);
                moves.add(e);
                break;
            }
            else if (board.getPiece(move).getTeamColor() == teamColor) {
                break;
            }
        }
    }

}
