package chess;

import java.util.ArrayList;
import java.util.Collection;

public class pawnMovesCalculator extends ChessMovesCalculator {
    public pawnMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<ChessMove>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //Black pawns can only move down
        if (teamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.getRow() == 7) {
                ChessPosition twoDown = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.getPiece(twoDown) == null && board.getPiece(oneDown) == null){
                    ChessMove e = new ChessMove(myPosition, twoDown,null);
                    moves.add(e);
                    ChessMove g = new ChessMove(myPosition, oneDown,null);
                    moves.add(g);
                }
                else if (board.getPiece(twoDown) != null && board.getPiece(oneDown) == null){
                    ChessMove e = new ChessMove(myPosition, oneDown,null);
                    moves.add(e);
                }
            }
            if (myPosition.getRow() < 7 && myPosition.getRow() != 2) { //has moved at least once and isn't in promotion zone
                ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.getPiece(oneDown) == null){
                    ChessMove e = new ChessMove(myPosition, oneDown,null);
                    moves.add(e);
                }
            }
            //In promotion Zone
            if (myPosition.getRow() == 2) {
                ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.getPiece(oneDown) == null){
                    ChessMove e = new ChessMove(myPosition, oneDown, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, oneDown, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, oneDown, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, oneDown, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
            //Attack code!!
            if (myPosition.getColumn() > 1) {
                ChessPosition atkLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
                if (board.getPiece(atkLeft) != null && board.getPiece(atkLeft).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() != 2) {
                    ChessMove e = new ChessMove(myPosition, atkLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkLeft) != null && board.getPiece(atkLeft).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) {
                    ChessMove e = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() != 2) {
                    ChessMove e = new ChessMove(myPosition, atkRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) {
                    ChessMove e = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
        }

        //White pawns can only move up
        if (teamColor == ChessGame.TeamColor.WHITE) {
            if (myPosition.getRow() == 2) {
                ChessPosition twoUp = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if (board.getPiece(twoUp) == null && board.getPiece(oneUp) == null){
                    ChessMove e = new ChessMove(myPosition, twoUp,null);
                    moves.add(e);
                    ChessMove g = new ChessMove(myPosition, oneUp,null);
                    moves.add(g);
                }
                else if (board.getPiece(twoUp) != null && board.getPiece(oneUp) == null){
                    ChessMove e = new ChessMove(myPosition, oneUp,null);
                    moves.add(e);
                }
            }
            if (myPosition.getRow() > 2 && myPosition.getRow() != 7) {
                ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if (board.getPiece(oneUp) == null){
                    ChessMove e = new ChessMove(myPosition, oneUp,null);
                    moves.add(e);
                }
            }
            //In promotion Zone
            if (myPosition.getRow() == 7) {
                ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if (board.getPiece(oneUp) == null){
                    ChessMove e = new ChessMove(myPosition, oneUp, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, oneUp, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, oneUp, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, oneUp, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
            //Attack code!!
            if (myPosition.getColumn() > 1) {
                ChessPosition atkLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
                if (board.getPiece(atkLeft) != null && board.getPiece(atkLeft).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() != 7) {
                    ChessMove e = new ChessMove(myPosition, atkLeft,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkLeft) != null && board.getPiece(atkLeft).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7) {
                    ChessMove e = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, atkLeft, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() != 7) {
                    ChessMove e = new ChessMove(myPosition, atkRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7) {
                    ChessMove e = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.QUEEN);
                    moves.add(e);
                    ChessMove i = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.ROOK);
                    moves.add(i);
                    ChessMove k = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.KNIGHT);
                    moves.add(k);
                    ChessMove l = new ChessMove(myPosition, atkRight, ChessPiece.PieceType.BISHOP);
                    moves.add(l);
                }
            }
        }

        return moves;
    }
}
