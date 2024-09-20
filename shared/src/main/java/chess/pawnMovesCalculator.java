package chess;

import java.util.ArrayList;
import java.util.Collection;

public class pawnMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    public pawnMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        this.teamColor = teamColor;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<ChessMove>();

        //Check how many spaces are up, down, and to the sides.
        int up = 8-myPosition.getRow();
        int down = myPosition.getRow()-1;
        int left = myPosition.getColumn()-1;
        int right = 8-myPosition.getColumn();

        //Black pawns can only move down
        if (teamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.getRow() == 7) {
                startingMove(-1, myPosition, board);
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
                    promotionMove(myPosition,oneDown,board);
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
                    promotionMove(myPosition,atkLeft,board);
                }
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() != 2) {
                    ChessMove e = new ChessMove(myPosition, atkRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) {
                    promotionMove(myPosition,atkRight,board);
                }
            }
        }

        //White pawns can only move up
        if (teamColor == ChessGame.TeamColor.WHITE) {
            if (myPosition.getRow() == 2) {
                startingMove(1, myPosition, board);
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
                    promotionMove(myPosition,oneUp,board);
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
                    promotionMove(myPosition,atkLeft,board);
                }
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() != 7) {
                    ChessMove e = new ChessMove(myPosition, atkRight,null);
                    moves.add(e);
                }
                else if (board.getPiece(atkRight) != null && board.getPiece(atkRight).getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7) {
                    promotionMove(myPosition,atkRight,board);
                }
            }
        }

        return moves;
    }

    public void startingMove(int wb, ChessPosition myPosition, ChessBoard board) {
        ChessPosition twoMove = new ChessPosition(myPosition.getRow()+(wb*2), myPosition.getColumn());
        ChessPosition oneMove = new ChessPosition(myPosition.getRow()+(wb*1), myPosition.getColumn());
        if (board.getPiece(twoMove) == null && board.getPiece(oneMove) == null){
            ChessMove e = new ChessMove(myPosition, twoMove,null);
            moves.add(e);
            ChessMove g = new ChessMove(myPosition, oneMove,null);
            moves.add(g);
        }
        else if (board.getPiece(twoMove) != null && board.getPiece(oneMove) == null){
            ChessMove e = new ChessMove(myPosition, oneMove,null);
            moves.add(e);
        }
    }

    public void promotionMove(ChessPosition myPosition, ChessPosition move, ChessBoard board) {
        ChessMove e = new ChessMove(myPosition, move, ChessPiece.PieceType.QUEEN);
        moves.add(e);
        ChessMove i = new ChessMove(myPosition, move, ChessPiece.PieceType.ROOK);
        moves.add(i);
        ChessMove k = new ChessMove(myPosition, move, ChessPiece.PieceType.KNIGHT);
        moves.add(k);
        ChessMove l = new ChessMove(myPosition, move, ChessPiece.PieceType.BISHOP);
        moves.add(l);
    }
}
