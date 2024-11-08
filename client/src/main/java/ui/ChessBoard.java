package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintStream;

import static java.lang.System.out;

public class ChessBoard extends EscapeSequences {
    ArrayList<String> letters;
    ArrayList<Integer> numbers;

    //Board dimensions
    private static final int BOARD_SIZE_IN_SQUARES = 8;

    public ChessBoard(){
        letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));

    }

    public void createBoarder() {
        drawHeader(out, true);
        out.println();

        //Chess Board time


        drawHeader(out, false);
        out.println();
    }

    public void drawHeader(PrintStream out, boolean topBottom) {
        setWhite(out);
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

    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

}
