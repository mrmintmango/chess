package ui;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.out;

public class Client {

    public Client(){
        out.print("Welcome to 240 Chess. Type the corresponding number to get started");
        Scanner scanner = new Scanner(System.in);
        loggedOutMenu();
        menuCalculatorOut(scanner);
    }

//    public void printMenu1(Scanner scan) {
//        out.println();
//        out.println();
//        loggedOutMenu();
//        menuCalculatorOut(scan);
//    }
//
//    public void printMenu2(Scanner scan) {
//        out.println();
//        out.println();
//        loggedInMenu();
//        menuCalculatorIn(scan);
//    }


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
        if (Objects.equals(input, "1")){ //Register Input
            out.println("USERNAME: ");
            String username = scan.nextLine();
            out.println("PASSWORD: ");
            String password = scan.nextLine();
            out.println("EMAIL: ");
            String email = scan.nextLine();
            // create a register request thing for the server facade I think
            out.println("You've been registered!");

            menuCalculatorOut(scan);
        }
        else if(Objects.equals(input, "2")) { //Login Input
            out.println("USERNAME: ");
            String username = scan.nextLine();
            out.println("PASSWORD: ");
            String password = scan.nextLine();

            //First check if that players username and password is correct
            loggedInMenu();
            menuCalculatorIn(scan);
        }
        else if(Objects.equals(input, "3")) { //Quits the program
            out.println("Thanks for playing!");
            System.exit(0);
        }
        else if(Objects.equals(input, "4")) { //Help output
            out.println("1- Register yourself as a new user");
            out.println("2- Login as an existing user");
            out.println("3- Exit the program");
            out.println("4- Displays this help menu again");
            menuCalculatorOut(scan);
        }
        else {
            out.println("... please input a valid option:");
            out.println();
            menuCalculatorOut(scan);
        }

    }

    public void menuCalculatorIn(Scanner scan) {
        String input = scan.nextLine();
        if (Objects.equals(input, "1")){ //Help Output
            out.println("1- Displays this help menu again");
            out.println("2- Logout current user");
            out.println("3- Create a new chess game");
            out.println("4- List all of the existing games");
            out.println("5- Play one of the existing games");
            out.println("6- Spectate a game that is being played");
            menuCalculatorIn(scan);
        }
        else if(Objects.equals(input, "2")) { //Logout
            //logout request for server goes here
            out.println("You've been logged out.");
            loggedOutMenu();
            menuCalculatorOut(scan);
        }
        else if(Objects.equals(input, "3")) { //Create a new game
            out.println("Please name the new game: ");
            String gameName = scan.nextLine();
            // create game code goes here
            out.println("Game has been created");
            menuCalculatorIn(scan);
        }
        else if(Objects.equals(input, "4")) { //List Games
            out.println("List of existing games: ");
            //Server list game request and printing
            menuCalculatorIn(scan);
        }
        else if(Objects.equals(input, "5")) { //Play a game
            out.println("I don't have this functionality yet, \nbut check back in the next update");
            menuCalculatorIn(scan);
        }
        else if(Objects.equals(input, "6")) { //Observe a game
            out.println("I don't have this functionality yet, \nbut check back in the next update");
            out.println("\nIn the meantime, here is the chessboard you'd like to see: ");

            //output the given chessboard.

            menuCalculatorIn(scan);
        }
        else {
            out.println("... please input a valid option:");
            out.println();
            menuCalculatorIn(scan);
        }
    }
}
