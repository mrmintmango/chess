package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamColor;
    private ChessBoard theBoard;
    private boolean gameOver;

    public ChessGame() {
        teamColor = TeamColor.WHITE;
        theBoard = new ChessBoard();
        theBoard.resetBoard();
        gameOver = false;
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
        teamColor = team;
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
        Collection<ChessMove> valids = new ArrayList<>();
        ChessGame.TeamColor color = theBoard.getPiece(startPosition).getTeamColor();
        if (theBoard.getPiece(startPosition) == null){
            return null;
        }
        else {
            Collection<ChessMove> basicMoves = theBoard.getPiece(startPosition).pieceMoves(theBoard,startPosition);
            for (ChessMove i : basicMoves){
                ChessBoard copyBoard = new ChessBoard(theBoard);
                copyBoard.movePiece(i);
                if (!isInCheck(color,copyBoard)){
                    valids.add(i);
                }
            }
        }
        return valids;
        //get rid of any moves that will leave the king in check
        // do this by duplicating the board (cloning), and checking if the move is in check
        //make sure it's a deep copy not a shallow copy
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
        if (moves.contains(move)){
            valid = true;
        }
        if (valid){
            theBoard.movePiece(move);
        }
        else {
            throw new InvalidMoveException("Not a valid move");
        }
        if (teamColor == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(teamColor,theBoard);
    }

    public boolean isInCheck(TeamColor teamColor, ChessBoard board) {
        boolean answer = false;
        outerloop:
        for (int row = 1; row <= 8; row++) { //revert to 0 and 7
            for (int column = 1; column <= 8; column++) {
                answer = innerCheck(teamColor, board, row, column);
                if (answer) {
                    break outerloop;
                }
            }
        }
        return answer;
    }

    public boolean innerCheck (TeamColor teamColor, ChessBoard board, int row, int column) {
        ChessPosition testPosition = new ChessPosition(row,column);
        ChessPiece piece = board.getPiece(testPosition);
        if (piece != null && piece.getTeamColor() != teamColor){
            for (ChessMove move : piece.pieceMoves(board,testPosition)) {
                ChessPiece victim = board.getPiece(move.getEndPosition());
                if(victim != null && victim.getPieceType() == ChessPiece.PieceType.KING && victim.getTeamColor() == teamColor){
                    return true;
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
        boolean checkMate = false;
        for (int row = 1; row <= 8; row++) { //revert to 0 and 7
            for (int column = 1; column <= 8; column++) {
                ChessPosition pos = new ChessPosition(row,column);
                if (theBoard.getPiece(pos) != null && theBoard.getPiece(pos).getTeamColor() == teamColor){
                    if (validMoves(pos).isEmpty()){
                        checkMate = true;
                    }
                    else if (!validMoves(pos).isEmpty()){
                        return false;
                    }
                }
            }
        }
        return checkMate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean stale = false;
        for (int row = 1; row <= 8; row++) { //revert to 0 and 7
            for (int column = 1; column <= 8; column++) {
                ChessPosition pos = new ChessPosition(row,column);
                if (theBoard.getPiece(pos) != null && theBoard.getPiece(pos).getTeamColor() == teamColor){
                    if (validMoves(pos).isEmpty() && !isInCheck(teamColor)){
                        stale = true;
                    }
                    else if (!validMoves(pos).isEmpty()){
                        return false;
                    }
                }
            }
        }
        return stale;
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

    public void setGameOver(){
        gameOver = true;
    }
}
