package chess;

import java.util.ArrayList;
import java.util.Collection;

public class bishopMovesCalculator extends ChessMovesCalculator {
    ChessGame.TeamColor teamColor;
    bishopMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition){
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check up right move
        for (int i = myPosition.getColumn(); i <= 8; i++) {
            if (myPosition.getRow() != 8) {
                ChessPosition oneUpRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                if (board.getPiece(oneUpRight) == null || board.getPiece(oneUpRight).getTeamColor() != this.teamColor){
                    ChessMove e = new ChessMove(myPosition, oneUpRight,null);
                    moves.add(e);
                }
            }
        }

        //Check up left move
        for (int i = myPosition.getColumn(); i >= 1; i--) {
            if (myPosition.getRow() != 8) {
                ChessPosition oneUpLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
                if (board.getPiece(oneUpLeft) == null || board.getPiece(oneUpLeft).getTeamColor() != this.teamColor){
                    ChessMove e = new ChessMove(myPosition, oneUpLeft,null);
                    moves.add(e);
                }
            }
        }

        //Check down left move
        for (int i = myPosition.getColumn(); i >= 1; i--) {
            if (myPosition.getRow() != 1) {
                ChessPosition oneDownLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
                if (board.getPiece(oneDownLeft) == null || board.getPiece(oneDownLeft).getTeamColor() != this.teamColor){
                    ChessMove e = new ChessMove(myPosition, oneDownLeft,null);
                    moves.add(e);
                }
            }
        }

        //Check down right move
        for (int i = myPosition.getColumn(); i <= 8; i++) {
            if (myPosition.getRow() != 1) {
                ChessPosition oneDownRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                if (board.getPiece(oneDownRight) == null || board.getPiece(oneDownRight).getTeamColor() != this.teamColor){
                    ChessMove e = new ChessMove(myPosition, oneDownRight,null);
                    moves.add(e);
                }
            }
        }

        return moves;
    }

}
