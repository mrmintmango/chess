package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.out;

public class Client {
    ServerFacade serverFacade;
    String playerAuthToken = null;
    ArrayList<String> gameList = null;

    public Client(){
        serverFacade = new ServerFacade("http://localhost:8080"); //later switch this to user input
        out.print("Welcome to 240 Chess. Type the corresponding number to get started");
        Scanner scanner = new Scanner(System.in);
        loggedOutMenu();
        menuCalculatorOut(scanner);
    }

    public void loggedOutMenu() {
        out.println("\n\n[LOGGED OUT]");
        out.println("1. Register");
        out.println("2. Login");
        out.println("3. Quit");
        out.println("4. Help");
    }

    public void loggedInMenu() {
        out.println("[LOGGED IN]");
        out.println("1. Help");
        out.println("2. Logout");
        out.println("3. Create Game");
        out.println("4. List Games");
        out.println("5. Play Game");
        out.println("6. Observe Game");
    }

    public void menuCalculatorOut(Scanner scan) {
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
            case null, default -> {
                out.println("... please input a valid option:");
                out.println();
                menuCalculatorOut(scan);
            }
        }

    }

    public void menuCalculatorIn(Scanner scan) throws IOException {
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
                String gameName = scan.nextLine();
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
            case "5" -> { //join game
                int gameID = 0;
                String response = "unreached server";
                out.println("Which game would you like to join?:");
                String number = scan.nextLine();
                out.println("Which Color player would you like? (all caps):");
                String color = scan.nextLine();

                try {
                    gameList=serverFacade.listGames(playerAuthToken);
                    if (gameList!=null && Integer.parseInt(number) > 0 && Integer.parseInt(number) < gameList.size()/4){
                        gameID=Integer.parseInt(gameList.get(((Integer.parseInt(number)-1)*4)));
                    }
                    else if (gameList!=null && (Integer.parseInt(number) < 0 || Integer.parseInt(number) > gameList.size()/4)){
                        out.println("Not a valid game number");
                        loggedInMenu();
                        menuCalculatorIn(scan);
                    }
                    else if(color == null || !Objects.equals(color, "WHITE") && !Objects.equals(color, "BLACK")){
                        out.println("Error: Invalid player color.");
                        loggedInMenu();
                        menuCalculatorIn(scan);
                    }
                    else {
                        out.println("There are no games to join.");
                        loggedInMenu();
                        menuCalculatorIn(scan);
                    }

                    response = serverFacade.joinGame(color, gameID, playerAuthToken);
                } catch (NumberFormatException e) {
                    out.println("invalid game number.");
                    loggedInMenu();
                    menuCalculatorIn(scan);
                }

                if (response.equals("GOOD")){
                    out.println("You've joined the game!");
                    printChess();

                    menuCalculatorIn(scan);
                }
                else  {
                    out.println("woopsie, there was a problem");
                    out.println(response);
                }

                menuCalculatorIn(scan);
            }
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
                        printChess();
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

    public void listPrinter(ArrayList<String> list) {
        out.println();
        int counter = 0;
        for (int i = 0; i < list.size()/4; i++){
            out.println("Game number: " + list.get(counter));
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

    public void printChess() {
        //output the given chessboard.
        chess.ChessBoard testBoard = new chess.ChessBoard();
        testBoard.resetBoard();
        ChessBoard board = new ChessBoard(testBoard.getSquares());
        board.createBoard();
        out.println();
        out.println();
    }

    public void caseFour(Scanner scan) throws IOException {
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

    public void loggedIn(String regResponse, Scanner scan) throws IOException {
        playerAuthToken = regResponse.substring(4);
        out.println("You've been logged in!");
        out.println("----------------");
        loggedInMenu();
        menuCalculatorIn(scan);
    }
}
