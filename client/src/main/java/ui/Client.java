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
    static boolean myTurn = false; //Fix to actually change to true when it's the players turn.

    public static void main(String[] args) {
        var ws = new Client();
        serverFacade = new ServerFacade("http://localhost:8080", ws); //later switch this to user input
        out.print("Welcome to 240 Chess!");
        out.println();
        Scanner scanner = new Scanner(System.in);
        mainBoard = new chess.ChessBoard(null);
        playerColor = null;
        positionKey = new HashMap<>();
        currentGameID = 0;
        setPositionKey();

        loggedOutMenu();
        menuCalculatorOut(scanner);
    }

    public static void loggedOutMenu() {
        out.println("\n\n[LOGGED OUT]");
        out.println("1. Register");
        out.println("2. Login");
        out.println("3. Quit");
        out.println("4. Help");
    }

    public static void loggedInMenu() {
        out.println("[LOGGED IN]");
        out.println("1. Help");
        out.println("2. Logout");
        out.println("3. Create Game");
        out.println("4. List Games");
        out.println("5. Play Game");
        out.println("6. Observe Game");
    }

    public static void inGameMenu() {
        out.println("[IN GAME]");
        out.println("1. Help");
        out.println("2. Redraw Board");
        out.println("3. Leave");
        out.println("4. Make Move");
        out.println("5. Resign");
        out.println("6. Highlight Legal Moves");
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
            }
            case "3" -> {
                //leave the match method here
            }
            case "4" -> {
                if(!myTurn && playerColor!=null){
                    out.println("You can't make a move, it's not your turn");
                    inGameMenuCalculator(scan);
                }
                if(playerColor==null){
                    out.println("You can't make a move, you're just a silly observer");
                    inGameMenuCalculator(scan);
                }


                out.println("Insert a valid move to make (in the form a2a4)");
                out.println("name the promotion piece afterwards if your pawn is promoting (a7a8 QUEEN)");
                String move = scan.nextLine();
                ChessPiece.PieceType upgrade = null;

                //check the moves validity
                if (move.length() == 4){
                    if (moveFormatCheck(move)){
                        moveConverter(move, null);
                    }
                }
                else if (move.length() < 4){
                    out.println("Invalid move format");
                }
                else if (move.length() > 5){
                    switch (move.substring(5)) {
                        case "QUEEN" -> { upgrade = ChessPiece.PieceType.QUEEN;
                        }
                        case "KNIGHT" -> { upgrade = ChessPiece.PieceType.KNIGHT;
                        }
                        case "BISHOP" -> { upgrade = ChessPiece.PieceType.BISHOP;
                        }
                        case "ROOK" -> { upgrade = ChessPiece.PieceType.ROOK;
                        }
                        default -> out.println("Invalid promotion piece");
                    }
                    if (upgrade != null){
                        moveConverter(move, upgrade);
                    }
                }
            } //Make move functionality
            case "5" -> {
                //Resign from the game
            }
            case "6" -> {
                //highlightBoard();
            }
            case null, default -> {
                out.println("... please input a valid option:");
                out.println();
                inGameMenu();
                inGameMenuCalculator(scan);
            }
        }
    }

    public static void moveConverter(String move, ChessPiece.PieceType upgrade){
        String justTheMove = move.substring(0,3);

        int row = positionKey.get(move.substring(0,1));
        int col = Integer.parseInt(move.substring(1,2));
        int endRow = positionKey.get(move.substring(2,3));
        int endCol = Integer.parseInt(move.substring(3,4));

        ChessPosition start = new ChessPosition(row, col);
        ChessPosition end = new ChessPosition(endRow, endCol);

        ChessMove chessMove = new ChessMove(start, end, upgrade);

        //send that move to the server:
        try{
            serverFacade.makeMove(chessMove, currentGameID, playerAuthToken, justTheMove, Objects.requireNonNullElse(playerColor, "OBSERVER"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean moveFormatCheck(String move){
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
        boolean check4 = false;

        if (move.charAt(0) == 'a' || move.charAt(0) == 'b' || move.charAt(0) == 'c' ||
                move.charAt(0) == 'd' || move.charAt(0) == 'e' || move.charAt(0) == 'f' ||
                move.charAt(0) == 'g' || move.charAt(0) == 'h'){
            check1 = true;
        }
        if (move.charAt(1) == '1' || move.charAt(1) == '2' || move.charAt(1) == '3' ||
                move.charAt(1) == '4' || move.charAt(1) == '5' || move.charAt(1) == '6' ||
                move.charAt(1) == '7' || move.charAt(1) == '8'){
            check2 = true;
        }
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
        return (check1 && check2 && check3 && check4);
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

                // create a register request thing for the server facade I think
                try {
                    String regResponse = serverFacade.register(username, password, email);

                    if (regResponse.contains("GOOD")){
                        out.println("You've been registered!");
                        loggedIn(regResponse, scan);
                    }
                    else  {
                        out.println("woopsie, there was a problem");
                        out.println(regResponse);
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

                //First check if that players username and password is correct
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

    public static void menuCalculatorIn(Scanner scan) throws IOException {
        String input = scan.nextLine();
        switch (input) {
            case "1" -> { //Help menu
                out.println("1- Displays this help menu again");
                out.println("2- Logout current user");
                out.println("3- Create a new chess game");
                out.println("4- List all of the existing games");
                out.println("5- Play one of the existing games");
                out.println("6- Spectate a game that is being played");
                menuCalculatorIn(scan);
            }
            case "2" -> {
                //logout request for server goes here
                String response = serverFacade.logout(playerAuthToken);

                if (response.equals("GOOD")){
                    out.println("You've been logged out!");
                    playerAuthToken = null;
                    loggedOutMenu();
                    menuCalculatorOut(scan);
                }
                else  {
                    out.println("woopsie, there was a problem");
                    out.println(response);
                }
            }
            case "3" -> {
                out.println("Please name the new game: ");
                String gameName = scan.nextLine(); //make sure name isn't null
                // create game code goes here
                String response = serverFacade.createGame(gameName, playerAuthToken);

                if (response.equals("GOOD")){
                    out.println("Game has been created");
                    menuCalculatorIn(scan);
                }
                else  {
                    out.println("woopsie, there was a problem");
                    out.println(response);
                }
            }
            case "4" -> caseFour(scan); //list game
            case "5" -> joinGame(scan); //join game method below
            case "6" -> {
                out.println("Which game would you like to observe?");
                String number = scan.nextLine();

                try {
                    gameList = serverFacade.listGames(playerAuthToken);
                    if (gameList == null) {
                        out.println("No games to observe");
                        loggedInMenu();
                        menuCalculatorIn(scan);
                    } else if (Integer.parseInt(number) < 0 || Integer.parseInt(number) > gameList.size() / 4) {
                        out.println("Not a valid game number");
                        loggedInMenu();
                        menuCalculatorIn(scan);
                    } else {
                        out.println("Observing game: " + number);
                        //output the given chessboard.
                        int gameID=Integer.parseInt(gameList.get(((Integer.parseInt(number)-1)*4)));
                        //printChess(gameID);
                    }
                }
                catch (NumberFormatException e) {
                out.println("Please input a valid game number.");
                loggedInMenu();
                menuCalculatorIn(scan);
            }


                menuCalculatorIn(scan);
            }
            case null, default -> {
                out.println("... please input a valid option:");
                out.println();
                menuCalculatorIn(scan);
            }
        }
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
            menuCalculatorIn(scan);
        }
        else if (Objects.equals(response.getFirst(), "error")) {
            out.println("woopsie, there was a problem");
            out.println(response.get(1));
        }
        else  {
            listPrinter(response);
        }
        menuCalculatorIn(scan);
    }

    public static void loggedIn(String regResponse, Scanner scan) throws IOException {
        playerAuthToken = regResponse.substring(4);
        out.println("You've been logged in!");
        out.println("----------------");
        loggedInMenu();
        menuCalculatorIn(scan);
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
                menuCalculatorIn(scan);
            }
            else if (gameList!=null && Integer.parseInt(number) > 0 && Integer.parseInt(number) <= gameList.size()/4){
                gameID=Integer.parseInt(gameList.get(((Integer.parseInt(number)-1)*4)));
                currentGameID = gameID;
            }
            else if (gameList!=null && (Integer.parseInt(number) <= 0 || Integer.parseInt(number) > gameList.size()/4)){
                out.println("Not a valid game number");
                loggedInMenu();
                menuCalculatorIn(scan);
            }
            else {
                out.println("There are no games to join.");
                loggedInMenu();
                menuCalculatorIn(scan);
            }

            response = serverFacade.joinGame(color, gameID, playerAuthToken);
        } catch (NumberFormatException | IOException e) {
            out.println("invalid game number.");
            loggedInMenu();
            menuCalculatorIn(scan);
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

        menuCalculatorIn(scan);
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

        switch (player) {
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
