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

    public ArrayList<ChessMove> addDPadMove(int iter, ChessPosition myPosition, ChessBoard board, int direction) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        for (int i = 1; i <= iter; i++) {
            ChessPosition oneUp = cPos(myPosition, i, direction);
            if (board.getPiece(oneUp) == null){
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() != teamColor) {
                ChessMove e = new ChessMove(myPosition, oneUp,null);
                moves.add(e);
                break;
            }
            if (board.getPiece(oneUp) != null && board.getPiece(oneUp).getTeamColor() == teamColor) {
                break;
            }
        }
        return moves;
    }

    public ChessPosition cPos(ChessPosition myPosition, int iter, int direction) {
        return getChessPositionRook(myPosition, iter, direction);
    }

    static ChessPosition getChessPositionRook(ChessPosition myPosition, int iter, int direction) {
        if (direction == 1){
            return new ChessPosition(myPosition.getRow()+iter, myPosition.getColumn());
        }
        else if (direction == 2){
            return new ChessPosition(myPosition.getRow()-iter, myPosition.getColumn());
        }
        else if (direction == 3){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()-iter);
        }
        else if (direction == 4){
            return new ChessPosition(myPosition.getRow(), myPosition.getColumn()+iter);
        }
        else return null;
    }

}



