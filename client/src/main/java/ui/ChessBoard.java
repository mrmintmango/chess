package ui;

import chess.ChessPiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintStream;

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

        //Chess Board time
        setBeige(out);
        drawBoard(true);

        drawHeader(out, false);
        out.println();
    }

    public void drawHeader(PrintStream out, boolean topBottom) {
        out.print(SET_TEXT_COLOR_BLACK);

        //top = true, bottom = false
        if(topBottom){
            out.print("   ");
            for (String letter : letters) {
                out.print(" " + letter + " ");
            }
            out.print("   ");
            setBlack(out);
        }
        else {
            out.print("   ");
            for (int i = letters.size()-1; i >= 0; i--) {
                out.print(" " + letters.get(i) + " ");
            }
            out.print("   ");
            setBlack(out);
        }

    }

    public void drawBoard(boolean bw){
        //white = true, black = false
        if(bw) {
            for(int i = 7; i >= 0; i--){
                out.print(" " + numbers.get(i) + " ");
                if (i%2 == 0){
                    drawBoardLine(i);
                }
                else {
                    drawBoardLine(i);
                }
                setBeige(out);
                out.print(" " + numbers.get(i) + " ");
                out.println();
            }
        }
    }

    private void drawBoardLine(int i) {
        if(i%2 == 0){
            for(int j = 0; j < 8; j++){
                if(j%2==0){
                    out.print(SET_BG_COLOR_WHITE);
                }
                else {
                    out.print(SET_BG_COLOR_BLACK);
                }
                boardSquares(i,j);
            }
        }
        else {
            for(int j = 0; j < 8; j++){
                if(j%2==0){
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
            out.print(" " + board[i][j] + " ");
        }
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
