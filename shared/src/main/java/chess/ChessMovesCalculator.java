package chess;

import java.util.ArrayList;
import java.util.Collection;

public class ChessMovesCalculator {
    public ChessGame.TeamColor teamColor;
    public ChessMovesCalculator() {

    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        teamColor = board.getPiece(myPosition).getTeamColor();

        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){
            kingMovesCalculator kingCalc = new kingMovesCalculator(teamColor);
            return kingCalc.kingMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN){
            queenMovesCalculator queenCalc = new queenMovesCalculator();
            return null; //for now... mwahahha
            //return kingCalc.kingMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP){
            bishopMovesCalculator bishopCalc = new bishopMovesCalculator(teamColor);
            return bishopCalc.bishopMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK){
            rookMovesCalculator rookCalc = new rookMovesCalculator();
            return null;
            //return kingCalc.kingMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){
            knightMovesCalculator knightCalc = new knightMovesCalculator(teamColor);
            return knightCalc.knightMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN){
            pawnMovesCalculator pawnCalc = new pawnMovesCalculator(teamColor);
            return pawnCalc.pawnMoves(board, myPosition);
        }

        return null;
    }

}
