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
        moves.addAll(rcalc.rookMoves(board, myPosition));

        return moves;
    }

}

