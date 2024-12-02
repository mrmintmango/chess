package ui;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.*;

import static java.lang.System.out;

public class Client implements ServerMessageObserver {
    static ServerFacade serverFacade;
    static String playerAuthToken = null;
    static ArrayList<String> gameList = null;
    static chess.ChessBoard mainBoard;
    static String playerColor;
    static Map<String, Integer> positionKey;
    static int currentGameID;
    static GameData mainGame;
    static MenuCalculatorIn menuCalculatorIn;

    public static void main(String[] args) {
        var ws = new Client();
        serverFacade = new ServerFacade("http://localhost:8080", ws); //later switch this to user input
        out.print("Welcome to 240 Chess!");
        out.println();
        Scanner scanner = new Scanner(System.in);
        mainBoard = new chess.ChessBoard();
        playerColor = null;
        positionKey = new HashMap<>();
        currentGameID = 0;
        setPositionKey();
        menuCalculatorIn = new MenuCalculatorIn();

        loggedOutMenu();
        menuCalculatorOut(scanner);
    }

    public static void loggedOutMenu() {
        out.println("\n\n[LOGGED OUT] \n1. Register \n2. Login \n3. Quit \n4. Help \n");
    }
    public static void loggedInMenu() {
        out.println("[LOGGED IN] \n1. Help \n2. Logout \n3. Create Game");
        out.println("4. List Games \n5. Play Game \n6. Observe Game");
    }
    public static void inGameMenu() {
        out.println("[IN GAME] \n1. Help \n2. Redraw Board \n3. Leave \n4. Make Move \n5. Resign \n6. Highlight Legal Moves");
    }

    public static void inGameMenuCalculator(Scanner scan) {
        String input = scan.nextLine();
        switch (input) {
            case "1" -> { //print help menu
                out.println("1. reprints this help menu");
                out.println("2. draws the board again");
                out.println("3. Leave the match, can be rejoined");
                out.println("4. Make a move against your opponent");
                out.println("5. Admit defeat");
                out.println("6. Highlight the possible moves a piece can make");
                out.println();
                inGameMenuCalculator(scan);
            }
            case "2" -> { //redraw board
                if (playerColor.equals("WHITE")){
                    printChess(mainBoard, "WHITE");
                }
                else if (playerColor.equals("BLACK")) {
                    printChess(mainBoard, "BLACK");
                }
                else {
                    printChess(mainBoard, "WHITE");
                }
                inGameMenuCalculator(scan);
            }
            case "3" -> {
                try{
                    serverFacade.leaveGame(currentGameID, playerAuthToken);
                    loggedInMenu();
                    menuCalculatorLoggedIn(scan);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "4" -> {
                out.println("Insert a valid move to make (in the form a2a4)");
                out.println("name the promotion piece afterwards if your pawn is promoting (a7a8 QUEEN)");
                String move = scan.nextLine();

                //check the moves validity
                if (move.length() == 4){
                    if (moveFormatCheck(move)){
                        moveConverter(move, null);
                    }
                    else{
                        out.println("wrong move format");
                    }
                }
                else if (move.length() < 4){
                    out.println("Invalid move format");
                }
                else if (move.length() > 5){
                   upgradePawn(move);
                }
                inGameMenuCalculator(scan);
            } //Make move functionality
            case "5" -> {
                resignCalc(scan);
            }
            case "6" -> {
                ChessBoard chessBoard = new ChessBoard(mainBoard.getSquares());
                out.println("input the location of the piece (in the form a2):");
                String location = scan.nextLine();
                if(!chessBoard.locationChecker(location)){
                    out.println("That's not a valid piece location");
                    inGameMenuCalculator(scan);
                }
                else {
                    int col = positionKey.get(location.substring(0,1));
                    int row = (Integer.parseInt(location.substring(1,2)));

                    ChessPosition start = new ChessPosition(row, col);
                    if(mainGame.game().getBoard().getPiece(start) != null){
                        Collection<ChessMove> valid = mainGame.game().validMoves(start);
                        chessBoard.highlight(Objects.requireNonNullElse(playerColor, "OBSERVER"), valid);
                    }
                    else {
                        out.println("There is no piece there.");
                    }
                    inGameMenuCalculator(scan);
                }
            }
            case null, default -> {
                out.println("... please input a valid option: \n");
                inGameMenu();
                inGameMenuCalculator(scan);
            }
        }
    }

    public static void moveConverter(String move, ChessPiece.PieceType upgrade){
        String justTheMove = move.substring(0,4);

        int col = positionKey.get(move.substring(0,1));
        int row = Integer.parseInt(move.substring(1,2));
        int endCol = positionKey.get(move.substring(2,3));
        int endRow = Integer.parseInt(move.substring(3,4));

        ChessPosition start = new ChessPosition(row, col);
        ChessPosition end = new ChessPosition(endRow, endCol);
        ChessMove chessMove = new ChessMove(start, end, upgrade);
        try{
            serverFacade.makeMove(chessMove, currentGameID, playerAuthToken, justTheMove);
        } catch (Exception e) {
            out.println("Client 176 bug bug");
            throw new RuntimeException(e);
        }
    }

    public static void upgradePawn(String move){
        ChessPiece.PieceType upgrade = null;

        switch (move.substring(5)) {
            case "QUEEN" -> { upgrade = ChessPiece.PieceType.QUEEN; }
            case "KNIGHT" -> { upgrade = ChessPiece.PieceType.KNIGHT; }
            case "BISHOP" -> { upgrade = ChessPiece.PieceType.BISHOP; }
            case "ROOK" -> { upgrade = ChessPiece.PieceType.ROOK; }
            default -> out.println("Invalid promotion piece");
        }
        if (upgrade != null){
            moveConverter(move, upgrade);
        }
    }

    public static void resignCalc(Scanner scan){
        out.print("Are you sure you want to resign? (yes/no)");
        String resign = scan.nextLine();
        if (resign.equals("yes") || resign.equals("YES")){
            try{ //check if the user really wants to resign
                serverFacade.resign(currentGameID, playerAuthToken);
                inGameMenuCalculator(scan);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (resign.equals("no") || resign.equals("NO")){
            inGameMenu();
            inGameMenuCalculator(scan);
        }
        else {
            out.print("That wasn't a valid option");
            inGameMenuCalculator(scan);
        }
    }

    public static boolean moveFormatCheck(String move){
        boolean check1 = new ChessBoard(mainBoard.getSquares()).locationChecker(move.substring(0,2));
        boolean check3 = false;
        boolean check4 = false;
        if (move.charAt(2) == 'a' || move.charAt(2) == 'b' || move.charAt(2) == 'c' ||
                move.charAt(2) == 'd' || move.charAt(2) == 'e' || move.charAt(2) == 'f' ||
                move.charAt(2) == 'g' || move.charAt(2) == 'h'){
            check3 = true;
        }
        if (move.charAt(3) == '1' || move.charAt(3) == '2' || move.charAt(3) == '3' ||
                move.charAt(3) == '4' || move.charAt(3) == '5' || move.charAt(3) == '6' ||
                move.charAt(3) == '7' || move.charAt(3) == '8'){
            check4 = true;
        }
        return (check1 && check3 && check4);
    }

    public static void menuCalculatorOut(Scanner scan) {
        String input = scan.nextLine();
        switch (input) {
            case "1" -> { //Register Input
                out.println("USERNAME: ");
                String username = scan.nextLine();
                out.println("PASSWORD: ");
                String password = scan.nextLine();
                out.println("EMAIL: ");
                String email = scan.nextLine();
                try {
                    String regResponse = serverFacade.register(username, password, email);

                    if (regResponse.contains("GOOD")){
                        out.println("You've been registered!");
                        loggedIn(regResponse, scan);
                    }
                    else  {
                        out.println("woopsie, there was a problem \n" + regResponse);
                    }
                } catch (IOException e) {
                    out.println("woopsie, there was a problem");
                    out.println("!!! " + e.getMessage() + " !!!");
                }
                menuCalculatorOut(scan);
            }
            case "2" -> { //Login Input
                out.println("USERNAME: ");
                String username = scan.nextLine();
                out.println("PASSWORD: ");
                String password = scan.nextLine();
                try {
                    String regResponse = serverFacade.login(username, password);

                    if (regResponse.contains("GOOD")){
                        loggedIn(regResponse, scan);
                    }
                    else  {
                        out.println("woopsie, there was a problem");
                        out.println(regResponse);
                        out.println("----------------");
                        loggedOutMenu();
                        menuCalculatorOut(scan);
                    }
                } catch (IOException e) {
                    out.println("woopsie, there was a problem");
                    out.println("!!! " + e.getMessage() + " !!!");
                }
            }
            case "3" -> {
                out.println("Thanks for playing!");
                System.exit(0);
            }
            case "4" -> {
                out.println("1- Register yourself as a new user");
                out.println("2- Login as an existing user");
                out.println("3- Exit the program");
                out.println("4- Displays this help menu again");
                menuCalculatorOut(scan);
            }
            case "ServerOverride12345" -> clearServer(scan);
            case null, default -> {
                out.println("... please input a valid option:");
                out.println();
                menuCalculatorOut(scan);
            }
        }
    }

    public static void menuCalculatorLoggedIn(Scanner scan) throws IOException {
        menuCalculatorIn.menuCalculatorInside(scan);
    }

    public static void listPrinter(ArrayList<String> list) {
        out.println();
        int counter = 0;

        for (int i = 0; i < list.size()/4; i++){
            out.println("Game number: " + (i+1));
            out.println("Game Name: " + list.get(counter+1));
            if (list.get(counter+2)!=null){
                out.println("White player: " + list.get(counter+2));
            }
            else {
                out.println("White player: EMPTY");
            }
            if (list.get(counter+3)!=null){
                out.println("Black player: " + list.get(counter+3));
            }
            else {
                out.println("Black player: EMPTY");
            }
            counter=counter+4;
            out.println();
        }
        out.println();
    }

    public static void caseFour(Scanner scan) throws IOException {
        out.println("List of existing games: ");
        //Server list game request and printing
        ArrayList<String> response = serverFacade.listGames(playerAuthToken);

        if (response.isEmpty()){
            out.println("No games created yet");
            menuCalculatorLoggedIn(scan);
        }
        else if (Objects.equals(response.getFirst(), "error")) {
            out.println("woopsie, there was a problem");
            out.println(response.get(1));
        }
        else  {
            listPrinter(response);
        }
        menuCalculatorLoggedIn(scan);
    }

    public static void loggedIn(String regResponse, Scanner scan) throws IOException {
        playerAuthToken = regResponse.substring(4);
        out.println("You've been logged in!");
        out.println("----------------");
        loggedInMenu();
        menuCalculatorLoggedIn(scan);
    }

    public static void joinGame(Scanner scan) throws IOException {
        int gameID = 0;
        String response = "unreached server";
        out.println("Which game would you like to join?:");
        String number = scan.nextLine();
        out.println("Which Color player would you like? (all caps):");
        String color = scan.nextLine();

        try {
            gameList=serverFacade.listGames(playerAuthToken);
            if(!Objects.equals(color, "WHITE") && !Objects.equals(color, "BLACK")) {
                out.println("Error: Invalid player color.");
                loggedInMenu();
                menuCalculatorLoggedIn(scan);
            }
            else if (gameList!=null && Integer.parseInt(number) > 0 && Integer.parseInt(number) <= gameList.size()/4){
                gameID=Integer.parseInt(gameList.get(((Integer.parseInt(number)-1)*4)));
                currentGameID = gameID;
            }
            else if (gameList!=null && (Integer.parseInt(number) <= 0 || Integer.parseInt(number) > gameList.size()/4)){
                out.println("Not a valid game number");
                loggedInMenu();
                menuCalculatorLoggedIn(scan);
            }
            else {
                out.println("There are no games to join.");
                loggedInMenu();
                menuCalculatorLoggedIn(scan);
            }

            response = serverFacade.joinGame(color, gameID, playerAuthToken);
        } catch (NumberFormatException | IOException e) {
            out.println("invalid game number.");
            loggedInMenu();
            menuCalculatorLoggedIn(scan);
        }

        playerColor = color;
        if (response.equals("GOOD")){
            out.println("You've joined the game!");

            inGameMenu();
            inGameMenuCalculator(scan);
        }
        else  {
            out.println("woopsie, there was a problem");
            out.println(response);
        }

        menuCalculatorLoggedIn(scan);
    } //Phase 6

    public void notify(ServerMessage message){
        ServerMessage.ServerMessageType type = message.getServerMessageType();
        switch (type){
            case LOAD_GAME -> loadGame(((LoadGameMessage) message).getGame(), ((LoadGameMessage) message).getPlayerType());
            case ERROR -> error(((ErrorMessage) message).getError());
            case NOTIFICATION -> notification(((NotificationMessage) message).getNotification());
        }
    }

    public void loadGame(GameData game, String playerType) {
        printChess(game.game().getBoard(), playerType);
        mainGame = game;
        out.println();
    }

    public void error(String message) {
        out.println("Websocket Error: " + message);
    }

    public void notification(String message) {
        out.println(message);
    }

    public static void printChess(chess.ChessBoard board, String player) {
        mainBoard = board;
        ChessBoard chessBoard = new ChessBoard(board.getSquares());

        switch (playerColor) {
            case "WHITE", "OBSERVER" -> {
                chessBoard.createBoard("WHITE");
                out.println();

            }
            case "BLACK" -> {
                chessBoard.createBoard("BLACK");
                out.println();
            }
            case null, default -> out.println("Incorrect User Type");
        }
    }

    private static void setPositionKey(){
        posKeySet(positionKey);
    }

    static void posKeySet(Map<String, Integer> positionKey) {
        positionKey.put("a", 1);
        positionKey.put("b", 2);
        positionKey.put("c", 3);
        positionKey.put("d", 4);
        positionKey.put("e", 5);
        positionKey.put("f", 6);
        positionKey.put("g", 7);
        positionKey.put("h", 8);
    }

    private static void clearServer(Scanner scanner){
        out.println("What's the password?: ");
        String password = scanner.nextLine();
        serverFacade.clear(password);
    }
}