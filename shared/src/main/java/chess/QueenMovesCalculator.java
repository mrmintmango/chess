package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    ChessGame.TeamColor teamColor;
    Collection<ChessMove> moves;
    BishopMovesCalculator bcalc;
    RookMovesCalculator rcalc;

    public QueenMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
        bcalc = new BishopMovesCalculator(teamColor);
        rcalc = new RookMovesCalculator(teamColor);
    }

    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        moves.addAll(bcalc.diagonalMoves(board, myPosition));

        //Check how many spaces are up, down, and to the sides.
        int up = 8 - myPosition.getRow();
        int down = myPosition.getRow() - 1;
        int left = myPosition.getColumn() - 1;
        int right = 8 - myPosition.getColumn();

        moves.addAll(rcalc.rookMoves(board, myPosition));

        return moves;
    }

}

