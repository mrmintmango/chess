package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.out;

public class Client {
    ServerFacade serverFacade;
    String playerAuthToken = null;

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

                    if (regResponse.equals("GOOD")){
                        out.println("You've been registered!");
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
                        playerAuthToken = regResponse.substring(4);
                        out.println("You've been logged in!");
                        out.println("----------------");
                        loggedInMenu();
                        menuCalculatorIn(scan);
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
            case "4" -> {
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
            case "5" -> {
                out.println("I don't have this functionality yet, \nbut check back in the next update");
                menuCalculatorIn(scan);
            }
            case "6" -> {
                out.println("I don't have this functionality yet, \nbut check back in the next update");
                out.println("\nIn the meantime, here is a chessboard that you can look at: ");

                //output the given chessboard.
                chess.ChessBoard testBoard = new chess.ChessBoard();
                testBoard.resetBoard();
                ChessBoard board = new ChessBoard(testBoard.getSquares());
                board.createBoard();
                out.println();
                out.println();

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
        int counter = 1;
        for (String s : list) {
            if (counter%4 == 0){
                out.println();
            }
            out.print(s + " ");
            counter++;
        }
        out.println();
    }
}
