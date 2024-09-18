package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessMovesCalculator {
    public ChessMovesCalculator() {

    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){
            //KingMovesCalc kingcalc = new KingMovesCals();

        }

        return null;
    }

}
