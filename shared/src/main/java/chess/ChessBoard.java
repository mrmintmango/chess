package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private ChessPiece[][] squares = new ChessPiece[9][9];
    public ChessBoard() {
        
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                //Resets all Spaces
                if (squares[row][column] != null) {
                    squares[row][column] = null;
                }
                //Adds White Pieces
                if (row == 1 && (column == 1 || column == 8)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
                }
                if (row == 1 && (column == 2 || column == 7)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 1 && (column == 3 || column == 6)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                }
                if (row == 1 && column == 4) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
                }
                if (row == 1 && column == 5) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
                }
                if (row == 2) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
                }
                //Adds Black Pieces
                if (row == 8 && (column == 1 || column == 8)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
                }
                if (row == 8 && (column == 2 || column == 7)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 8 && (column == 3 || column == 6)) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                }
                if (row == 8 && column == 4) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
                }
                if (row == 8 && column == 5) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
                }
                if (row == 7) {
                    addPiece(new ChessPosition(row,column),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
