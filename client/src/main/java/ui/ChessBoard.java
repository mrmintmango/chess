package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintStream;
import java.util.Objects;

import static java.lang.System.out;

public class ChessBoard extends EscapeSequences {
    ArrayList<String> letters;
    ArrayList<Integer> numbers;
    private final ChessPiece[][] board;

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 8;

    public ChessBoard(ChessPiece[][] pieces){
        letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        board = pieces;
    }

    public void createBoard() {
        setBeige(out);
        drawHeader(out, true);
        out.println();

        setBeige(out);
        drawBoard(true);

        setBeige(out);
        drawHeader(out, true);
        out.println();

        out.print(RESET_BG_COLOR); //middle bar
        out.println();

        setBeige(out);
        drawHeader(out, false);
        out.println();

        setBeige(out);
        drawBoard(false);

        setBeige(out);
        drawHeader(out, false);
        out.println();

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    public void drawHeader(PrintStream out, boolean topBottom) {
        out.print(SET_TEXT_COLOR_BLACK);

        //top = true, bottom = false
        if(topBottom){
            out.print("   ");
            for (int i = letters.size()-1; i >= 0; i--) {
                out.print(" " + letters.get(i) + " ");
            }
            out.print("   ");
            out.print(RESET_BG_COLOR);
        }
        else {
            out.print("   ");
            for (String letter : letters) {
                out.print(" " + letter + " ");
            }
            out.print("   ");
            out.print(RESET_BG_COLOR);
        }

    }

    public void drawBoard(boolean bw){
        //white = true, black = false
        if(bw) {
            for(int i = 0; i <= 7; i++){
                setBeige(out);
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineTop(i);

                setBeige(out);
                out.print(" " + numbers.get(i) + " ");
                out.print(RESET_BG_COLOR);
                out.println();
            }
        }
        else{
            for(int i = 7; i >= 0; i--){
                setBeige(out);
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineBottom(i);

                setBeige(out);
                out.print(" " + numbers.get(i) + " ");
                out.print(RESET_BG_COLOR);
                out.println();
            }
        }
    }

    private void drawBoardLineTop(int i) {
        if(i%2 == 0){
            for(int j = 7; j >= 0; j--){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_WHITE);
                }
                else {
                    out.print(SET_BG_COLOR_BLACK);
                }
                boardSquares(i,j);
            }
        }
        else {
            for(int j = 7; j >= 0; j--){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_BLACK);
                }
                else {
                    out.print(SET_BG_COLOR_WHITE);
                }
                boardSquares(i,j);
            }
        }
    }

    private void drawBoardLineBottom(int i) {
        if(i%2 == 0){
            for(int j = 0; j <= 7; j++){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_WHITE);
                }
                else {
                    out.print(SET_BG_COLOR_BLACK);
                }
                boardSquares(i,j);
            }
        }
        else {
            for(int j = 0; j <= 7; j++){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_BLACK);
                }
                else {
                    out.print(SET_BG_COLOR_WHITE);
                }
                boardSquares(i,j);
            }
        }
    }

    public void boardSquares(int i, int j) {
        if(board[i][j] == null){
            out.print("   ");
        }
        else {
            if (board[i][j].getTeamColor() == ChessGame.TeamColor.WHITE){
                out.print(SET_TEXT_COLOR_RED); // red = white
            }
            else {
                out.print(SET_TEXT_COLOR_GREEN); // green = black
            }
            out.print(" " + pieceIcon(board[i][j].getPieceType().toString()) + " ");
        }
    }

    private String pieceIcon(String pieceType){
        if (Objects.equals(pieceType, "QUEEN")){
            return "Q";
        }
        else if (Objects.equals(pieceType, "KING")){
            return "K";
        }
        else if (Objects.equals(pieceType, "PAWN")){
            return "P";
        }
        else if (Objects.equals(pieceType, "BISHOP")){
            return "B";
        }
        else if (Objects.equals(pieceType, "ROOK")){
            return "R";
        }
        else if (Objects.equals(pieceType, "KNIGHT")){
            return "N";
        }
        return "?";
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBeige(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

}
