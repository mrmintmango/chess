package chess;

import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor teamColor;
    ChessBoard theBoard;

    public ChessGame() {
        teamColor = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (team == TeamColor.WHITE){
            teamColor = TeamColor.BLACK;
        }
        else {
            teamColor = TeamColor.WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if (theBoard.getPiece(startPosition) == null){
            return null;
        }
        else {
            return theBoard.getPiece(startPosition).pieceMoves(theBoard,startPosition);
        }

        //get rid of any moves that will leave the king in check
        // do this by duplicating the board (cloning), and checking if the move is in check
        //make sure its a deep copy not a shallow copy
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = theBoard.getPiece(move.getStartPosition());
        if (piece == null){
            throw new InvalidMoveException("No piece detected");
        }
        if (piece.getTeamColor() != getTeamTurn()){
            throw new InvalidMoveException("Not your turn");
        }
        boolean valid = false;
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        // use collection.contains
        for (ChessMove checkMove : moves) {
            if (move == checkMove){
                valid = true;
            }
        }
        if (valid){

        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) { //revert to 0 and 7
            for (int column = 1; column <= 8; column++) {
                ChessPosition testPosition = new ChessPosition(row,column);
                ChessPiece piece = theBoard.getPiece(testPosition);
                if (piece != null && theBoard.getPiece(new ChessPosition(row,column)).getTeamColor() != teamColor){
                    Collection<ChessMove> moves = piece.pieceMoves(theBoard,testPosition);
                    for (ChessMove move : moves) {
                        if(theBoard.getPiece(move.getEndPosition()) != null && theBoard.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KING && theBoard.getPiece(move.getEndPosition()).getTeamColor() == teamColor){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        theBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return theBoard;
    }
}
