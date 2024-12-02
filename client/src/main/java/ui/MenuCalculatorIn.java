package ui;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.out;

public class MenuCalculatorIn extends Client{

    public MenuCalculatorIn(){}

    public void menuCalculatorInside(Scanner scan) throws IOException {
        String input = scan.nextLine();
        switch (input) {
            case "1" -> { //Help menu
                out.println("1- Displays this help menu again");
                out.println("2- Logout current user");
                out.println("3- Create a new chess game");
                out.println("4- List all of the existing games");
                out.println("5- Play one of the existing games");
                out.println("6- Spectate a game that is being played");
                menuCalculatorLoggedIn(scan);
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
                    menuCalculatorLoggedIn(scan);
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
                        menuCalculatorLoggedIn(scan);
                    } else if (Integer.parseInt(number) < 0 || Integer.parseInt(number) > gameList.size() / 4) {
                        out.println("Not a valid game number");
                        loggedInMenu();
                        menuCalculatorLoggedIn(scan);
                    } else {
                        out.println("Observing game: " + number);
                        playerColor = "OBSERVER";
                        //output the given chessboard.
                        int gameID=Integer.parseInt(gameList.get(((Integer.parseInt(number)-1)*4)));
                        currentGameID = gameID;
                        serverFacade.observe(gameID, playerAuthToken);
                        inGameMenu();
                        inGameMenuCalculator(scan);
                    }
                }
                catch (NumberFormatException e) {
                    out.println("Please input a valid game number.");
                    loggedInMenu();
                    menuCalculatorLoggedIn(scan);
                }
            }
            case null, default -> {
                out.println("... please input a valid option:");
                out.println();
                menuCalculatorLoggedIn(scan);
            }
        }
    }
}
