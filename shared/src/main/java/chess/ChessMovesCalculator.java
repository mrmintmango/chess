package chess;

import java.util.Collection;

public class ChessMovesCalculator {
    public static ChessGame.TeamColor teamColor;
    public ChessMovesCalculator() {
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        teamColor = board.getPiece(myPosition).getTeamColor();

        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KING){
            KingMovesCalculator kingCalc = new KingMovesCalculator(teamColor);
            return kingCalc.kingMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.QUEEN){
            QueenMovesCalculator queenCalc = new QueenMovesCalculator(teamColor);
            return queenCalc.queenMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.BISHOP){
            BishopMovesCalculator bishopCalc = new BishopMovesCalculator(teamColor);
            return bishopCalc.bishopMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.ROOK){
            RookMovesCalculator rookCalc = new RookMovesCalculator(teamColor);
            return rookCalc.rookMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.KNIGHT){
            KnightMovesCalculator knightCalc = new KnightMovesCalculator(teamColor);
            return knightCalc.knightMoves(board, myPosition);
        }
        if (board.getPiece(myPosition).getPieceType() == ChessPiece.PieceType.PAWN){
            PawnMovesCalculator pawnCalc = new PawnMovesCalculator(teamColor);
            return pawnCalc.pawnMoves(board, myPosition);
        }

        return null;
    }

}



