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
    private final ChessPiece[][] squares = new ChessPiece[8][8];
    public ChessBoard() {

    }

    public ChessBoard(ChessBoard copy){
        //squares = copy.squares.clone();
        for (int row = 0; row <= 7; row++) { //revert to 0 and 7
            for (int column = 0; column <= 7; column++) {
                if (copy.squares[row][column] != null){
                    squares[row][column] = new ChessPiece(copy.squares[row][column].getTeamColor(),copy.squares[row][column].getPieceType());
                }
            }
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    public void removePiece(ChessPosition position) {
        squares[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int row = 0; row <= 7; row++) { //revert to 0 and 7
            for (int column = 0; column <= 7; column++) {
                //Resets all Spaces
                if (squares[row][column] != null) {
                    squares[row][column] = null;
                }
                //Adds White Pieces
                if (row == 0 && (column == 0 || column == 7)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
                }
                if (row == 0 && (column == 1 || column == 6)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 0 && (column == 2 || column == 5)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
                }
                if (row == 0 && column == 3) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
                }
                if (row == 0 && column == 4) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
                }
                if (row == 1) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
                }
                //Adds Black Pieces
                if (row == 7 && (column == 0 || column == 7)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
                }
                if (row == 7 && (column == 1 || column == 6)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
                }
                if (row == 7 && (column == 2 || column == 5)) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
                }
                if (row == 7 && column == 3) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
                }
                if (row == 7 && column == 4) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
                }
                if (row == 6) {
                    addPiece(new ChessPosition(row+1,column+1),new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
                }
            }
        }
    }

    public void movePiece(ChessMove move) {
        ChessPiece piece = getPiece(move.getStartPosition());
        if (move.getPromotionPiece() != null){
            ChessPiece promotion = new ChessPiece(piece.getTeamColor(),move.getPromotionPiece());
            if(move.getEndPosition() != null){
                removePiece(move.getEndPosition());
            }
            addPiece(move.getEndPosition(), promotion);
            removePiece(move.getStartPosition());
        }
        else if (move.getPromotionPiece() == null){
            if(move.getEndPosition() != null){
                removePiece(move.getEndPosition());
            }
            addPiece(move.getEndPosition(), piece);
            removePiece(move.getStartPosition());
        }
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "squares=" + Arrays.toString(squares) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }
}
