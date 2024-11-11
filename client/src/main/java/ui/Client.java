package ui;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import static java.lang.System.out;

public class Client {

    public Client(){
        Scanner scanner = new Scanner(System.in);
        loggedOutMenu();
        menuCalculatorOut(scanner);
    }

    public void loggedOutMenu() {
        out.print(" Welcome to 240 Chess. Type the corresponding number to get started");
        out.println("\n[LOGGED OUT]");
        out.println("\n1. Register");
        out.println("2. Login");
        out.println("3. Quit");
        out.println("4. Help");
    }

    public void loggedInMenu() {
        out.println("[LOGGED IN]");
        out.println("/n 1. Help");
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
        }
        else if(Objects.equals(input, "2")) { //Login Input
            out.println("USERNAME: ");
            String username = scan.nextLine();
            out.println("PASSWORD: ");
            String password = scan.nextLine();
        }
        else if(Objects.equals(input, "3")) { //Quits the program
            System.exit(0);
        }
        else if(Objects.equals(input, "4")) { //Help output
            out.println("1- Register yourself as a new user");
            out.println("2- Login as an existing user");
            out.println("3- Exit the program");
            out.println("4- Displays this help menu again");
            out.println();
            out.println();
            loggedOutMenu();
            menuCalculatorOut(scan);
        }
    }

    public void menuCalculatorIn() {}
}
