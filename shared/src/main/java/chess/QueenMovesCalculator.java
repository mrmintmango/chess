package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    public QueenMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        ChessMovesCalculator.teamColor = teamColor;
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        BishopMovesCalculator.bishopMoves(board, myPosition);

        //check up
        addMoves(up,myPosition,board,1);
        //check down
        addMoves(down,myPosition,board,2);
        //check left
        addMoves(left,myPosition,board,3);
        //check Right
        addMoves(right,myPosition,board,4);

        return moves;
    }

    public void addMoves(int iter, ChessPosition myPosition, ChessBoard board, int direction) {
        ArrayList<ChessMove> calcMoves = addDPadMove(iter, myPosition, board, direction);
        moves.addAll(calcMoves);
    }

}
