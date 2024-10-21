package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    public PawnMovesCalculator(ChessGame.TeamColor teamColor) {
        super();
        ChessMovesCalculator.teamColor = teamColor;
    }

    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();

        //Black pawns can only move down
        if (teamColor == ChessGame.TeamColor.BLACK) {
            if (myPosition.getRow() == 7) {
                startingMove(-1, myPosition, board);
            }
            if (myPosition.getRow() < 7 && myPosition.getRow() != 2) { //has moved at least once and isn't in promotion zone
                ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.getPiece(oneDown) == null){
                    addMove(myPosition,oneDown);
                }
            }
            //In promotion Zone
            if (myPosition.getRow() == 2) {
                ChessPosition oneDown = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if (board.getPiece(oneDown) == null){
                    promotionMove(myPosition,oneDown);
                }
            }
            //Attack code!!
            if (myPosition.getColumn() > 1) {
                ChessPosition atkLeft = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-1);
                pawnBoard(board, myPosition, atkLeft);
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+1);
                pawnBoard(board, myPosition, atkRight);
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
                    addMove(myPosition,oneUp);
                }
            }
            //In promotion Zone
            if (myPosition.getRow() == 7) {
                ChessPosition oneUp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if (board.getPiece(oneUp) == null){
                    promotionMove(myPosition,oneUp);
                }
            }
            //Attack code!!
            if (myPosition.getColumn() > 1) {
                ChessPosition atkLeft = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-1);
                pawnAttack(board, myPosition, atkLeft);
            }
            if (myPosition.getColumn() < 8) {
                ChessPosition atkRight = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+1);
                pawnAttack(board, myPosition, atkRight);
            }
        }

        return moves;
    }

    private void pawnAttack(ChessBoard board, ChessPosition myPosition, ChessPosition atkLeft) {
        ChessPiece pieceL = board.getPiece(atkLeft);
        if (pieceL != null && pieceL.getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() != 7) {
            addMove(myPosition,atkLeft);
        }
        else if (pieceL != null && pieceL.getTeamColor() == ChessGame.TeamColor.BLACK && myPosition.getRow() == 7) {
            promotionMove(myPosition,atkLeft);
        }
    }

    private void pawnBoard(ChessBoard board, ChessPosition myPosition, ChessPosition atkLeft) {
        ChessPiece pieceL = board.getPiece(atkLeft);
        if (pieceL != null && pieceL.getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() != 2) {
            addMove(myPosition,atkLeft);
        }
        else if (pieceL != null && pieceL.getTeamColor() == ChessGame.TeamColor.WHITE && myPosition.getRow() == 2) {
            promotionMove(myPosition,atkLeft);
        }
    }

    public void startingMove(int wb, ChessPosition myPosition, ChessBoard board) {
        ChessPosition twoMove = new ChessPosition(myPosition.getRow()+(wb*2), myPosition.getColumn());
        ChessPosition oneMove = new ChessPosition(myPosition.getRow()+(wb), myPosition.getColumn());
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

    public void promotionMove(ChessPosition myPosition, ChessPosition move) {
        ChessMove e = new ChessMove(myPosition, move, ChessPiece.PieceType.QUEEN);
        moves.add(e);
        ChessMove i = new ChessMove(myPosition, move, ChessPiece.PieceType.ROOK);
        moves.add(i);
        ChessMove k = new ChessMove(myPosition, move, ChessPiece.PieceType.KNIGHT);
        moves.add(k);
        ChessMove l = new ChessMove(myPosition, move, ChessPiece.PieceType.BISHOP);
        moves.add(l);
    }

    public void addMove(ChessPosition myPosition, ChessPosition move) {
        ChessMove e = new ChessMove(myPosition, move,null);
        moves.add(e);
    }
}
