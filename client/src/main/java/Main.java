import chess.*;
import ui.ChessBoard;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        testingCode();
    }

    public static void testingCode(){
        ChessPiece[][] pieces = new ChessPiece[8][8];
        ChessBoard board = new ChessBoard(pieces);

        //add actual pieces to pieces;
        board.createBoard();
    }
}