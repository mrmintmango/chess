package chess;

import java.util.Collection;

public interface QueenInterface {

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition);

    public void addMove(int iter, ChessPosition myPosition, ChessBoard board, int direction);

    public ChessPosition cPos(ChessPosition myPosition, int iter, int direction);
}
