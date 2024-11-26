package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.*;
import java.io.PrintStream;

import static java.lang.System.out;

public class ChessBoard extends EscapeSequences {
    ArrayList<String> letters;
    ArrayList<Integer> numbers;
    private final ChessPiece[][] board;
    static Map<String, Integer> positionKey;
    private final Boolean[][] highlighted;

    public ChessBoard(ChessPiece[][] pieces){
        letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
        numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8));
        board = pieces;
        positionKey = new HashMap<>();
        setPositionKey();
        highlighted = new Boolean[8][8];
    }

    public void createBoard(String bw) {
        if(bw.equals("WHITE") || bw.equals("OBSERVER")){
            setBeige();
            drawHeader(out, false);
            out.println();

            setBeige();
            drawBoard(true);

            setBeige();
            drawHeader(out, false);
            out.println();
        }
        else if (bw.equals("BLACK")){
            setBeige();
            drawHeader(out, true);
            out.println();

            setBeige();
            drawBoard(false);

            setBeige();
            drawHeader(out, true);
            out.println();
        }
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
        //white = false, black = true
        if(!bw) {
            for(int i = 0; i <= 7; i++){
                setBeige();
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineTop(i);

                setBeige();
                out.print(" " + numbers.get(i) + " ");
                out.print(RESET_BG_COLOR);
                out.println();
            }
        }
        else{
            for(int i = 7; i >= 0; i--){
                setBeige();
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineBottom(i);

                setBeige();
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
                out.print(SET_TEXT_COLOR_BLUE); // green = black
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

    private static void setBeige() {
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print(SET_TEXT_COLOR_BLACK);
    }

    public void setBoolBoard(Collection<ChessMove> valid) {
        for (int row = 0; row <= 7; row++) { //revert to 0 and 7
            for (int column = 0; column <= 7; column++) {
                highlighted[row][column] = false;
                for (ChessMove move : valid){
                    if (move.getEndPosition().getRow()-1 == row && move.getEndPosition().getColumn()-1 == column) {
                        highlighted[row][column] = true;
                        break;
                    }
                    if (move.getStartPosition().getRow()-1 == row && move.getStartPosition().getColumn()-1 == column) {
                        highlighted[row][column] = true;
                        break;
                    }
                }
            }
        }
    }

    public void highlight(String bw, Collection<ChessMove> valid) {
        setBoolBoard(valid);

        if(bw.equals("WHITE") || bw.equals("OBSERVER")){
            setBeige();
            drawHeader(out, false);
            out.println();

            setBeige();
            drawBoardHighlighted(true);

            setBeige();
            drawHeader(out, false);
            out.println();
        }
        else if (bw.equals("BLACK")){
            setBeige();
            drawHeader(out, true);
            out.println();

            setBeige();
            drawBoardHighlighted(false);

            setBeige();
            drawHeader(out, true);
            out.println();
        }
        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }


    private void drawBoardLineTopHighlighted(int i) {
        if(i%2 == 0){
            for(int j = 7; j >= 0; j--){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_WHITE);
                    if(highlighted[i][j]){
                        out.print(SET_BG_COLOR_YELLOW);
                    }
                }
                else {
                    out.print(SET_BG_COLOR_BLACK);
                    if(highlighted[i][j]){
                        out.print(SET_BG_COLOR_GREEN);
                    }
                }
                boardSquares(i,j);
            }
        }
        else {
            for(int j = 7; j >= 0; j--){
                if(j%2!=0){
                    out.print(SET_BG_COLOR_BLACK);
                    if(highlighted[i][j]){
                        out.print(SET_BG_COLOR_GREEN);
                    }
                }
                else {
                    out.print(SET_BG_COLOR_WHITE);
                    if(highlighted[i][j]){
                        out.print(SET_BG_COLOR_YELLOW);
                    }
                }
                boardSquares(i,j);
            }
        }
    }

    private void drawBoardLineBottomHighlighted(int i) {
        if(i%2 == 0){
            boardDrawing(i, SET_BG_COLOR_WHITE, SET_BG_COLOR_YELLOW, SET_BG_COLOR_BLACK, SET_BG_COLOR_GREEN);
        }
        else {
            boardDrawing(i, SET_BG_COLOR_BLACK, SET_BG_COLOR_GREEN, SET_BG_COLOR_WHITE, SET_BG_COLOR_YELLOW);
        }
    }

    private void boardDrawing(int i, String setBgColorWhite, String setBgColorYellow, String setBgColorBlack, String setBgColorGreen) {
        for(int j = 0; j <= 7; j++){
            if(j%2!=0){
                out.print(setBgColorWhite);
                if(highlighted[i][j]){
                    out.print(setBgColorYellow);
                }
            }
            else {
                out.print(setBgColorBlack);
                if(highlighted[i][j]){
                    out.print(setBgColorGreen);
                }
            }
            boardSquares(i,j);
        }
    }

    public void drawBoardHighlighted(boolean bw){
        //white = true, black = false
        if(!bw) {
            for(int i = 0; i <= 7; i++){ //rows
                setBeige();
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineTopHighlighted(i);

                setBeige();
                out.print(" " + numbers.get(i) + " ");
                out.print(RESET_BG_COLOR);
                out.println();
            }
        }
        else{
            for(int i = 7; i >= 0; i--){
                setBeige();
                out.print(" " + numbers.get(i) + " ");
                drawBoardLineBottomHighlighted(i);

                setBeige();
                out.print(" " + numbers.get(i) + " ");
                out.print(RESET_BG_COLOR);
                out.println();
            }
        }
    }

    public boolean locationChecker(String location){
        boolean check1 = false;
        boolean check2 = false;

        if(location.length() != 2) {
            return false;
        }
        else {
            if (location.charAt(0) == 'a' || location.charAt(0) == 'b' || location.charAt(0) == 'c' ||
                    location.charAt(0) == 'd' || location.charAt(0) == 'e' || location.charAt(0) == 'f' ||
                    location.charAt(0) == 'g' || location.charAt(0) == 'h'){
                check1 = true;
            }
            if (location.charAt(1) == '1' || location.charAt(1) == '2' || location.charAt(1) == '3' ||
                    location.charAt(1) == '4' || location.charAt(1) == '5' || location.charAt(1) == '6' ||
                    location.charAt(1) == '7' || location.charAt(1) == '8'){
                check2 = true;
            }
            return (check1 && check2);
        }
    }

    private static void setPositionKey(){
        Client.posKeySet(positionKey);
    }
}
