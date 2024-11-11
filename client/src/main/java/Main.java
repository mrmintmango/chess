import chess.*;
import ui.ChessBoard;
import ui.Client;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        testingCode();
    }

    public static void testingCode(){
        //ChessPiece[][] pieces = new ChessPiece[8][8];
        chess.ChessBoard testBoard = new chess.ChessBoard();
        testBoard.resetBoard();
        ChessBoard board = new ChessBoard(testBoard.getSquares());

        //board.createBoard();
        Client menu = new Client();

    }
}