package chess;

import java.util.ArrayList;
import java.util.Collection;

public class kingMovesCalculator extends ChessMovesCalculator {

    kingMovesCalculator() {
        super();
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();
        ChessMove e = new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1),null);
        moves.add(e);

        return null;
    }
}

