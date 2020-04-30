package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.CliComposer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public void run() throws IOException {

        //reset the terminal
        System.out.println("\033[H\033[J");

        // Open the input streams with the user
        Scanner stdIn = new Scanner(System.in);

        //maker for the title
        CliComposer composer = new CliComposer();

        //create rhe title
        System.out.println(composer.titleMaker());
        System.out.println("\n\t\t\t\t\t\t----- Press ENTER to continue -----");
        System.in.read();

        //reset the terminal
        System.out.println("\033[H\033[J");


        //log moment where the player insert name, age, and number of player of the game if he is the first player
        //and says if he wants to use GUI or CLI
        System.out.println(composer.bannerMaker());
        String line;

        //ask how the player wants to play
        System.out.println(
                "Do you want to play with CLI or with GUI?" + // ... interface...
                        " (type \"CLI\" for CLI interface or \"GUI\" for GUI interface"); // ... preference

        line = stdIn.nextLine();
        boolean cliInterface;
        while (true) { // Wait a valid input
            if (line.toUpperCase().equals("CLI")){
                cliInterface = true; // Set interface to  CLI
                break;
            } else if (line.toUpperCase().equals("GUI")) {
                cliInterface = false; // Set interface to GUI
                break;
            } else {
                System.out.println("Invalid input.\n\n"+ // Ask...
                        "Do you want to play with CLI or with GUI?" + // ... interface...
                        " (type \"CLI\" for CLI interface or \"GUI\" for GUI interface)"); // ... preference
                line = stdIn.nextLine();
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------\n");

        //create the connection
        String IP = "127.0.0.1";
        int PORT = 1337;
        Socket socket = new Socket(IP, PORT); // Start the socket connection
        System.out.println("Connection established");
        // Open the input/output streams with the socket
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        System.out.println("-------------------------------------------------------------------------------------------\n");

        // Name request
        System.out.println("Insert your name, buddy!\n"); // Print name request
        line = stdIn.nextLine(); // Read name
        socketOut.println(line); // Write name on socket stream
        socketOut.flush(); // Send name
        System.out.println("-------------------------------------------------------------------------------------------\n");

        // Age request
        System.out.println("and now tell me, how old are you?\n"); // Print age request
        int number = stdIn.nextInt(); // Read age
        while (true) { // Wait a valid input
            if (number >= 1 && number <= 99) {
                socketOut.println(number); // Write age on socket stream
                socketOut.flush(); // Send age
                break;
            } else {
                System.out.println("Invalid input.\n\n" + line); // Print age request
                number = stdIn.nextInt(); // Read age
            }
        }
        System.out.println("-------------------------------------------------------------------------------------------\n");


        // Number of player request or confirm of the insertion in lobby
        line = socketIn.nextLine(); // Receive message
        if (line.equals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)")) { // If message is number of player request
            System.out.println(line); // Print number of player request

            number = stdIn.nextInt(); // Read number of player
            while (true) {
                if (number==2 || number==3) {
                    socketOut.println(number); // Write number of player on socket stream
                    socketOut.flush(); // Send number of player
                    break;
                } else {
                    System.out.println("Invalid input\n\n" +
                            "How many player do you want to play with? (2 or 3 player allowed)"); // Print number of player request
                    number = stdIn.nextInt(); // Read number of player
                }
            }

        } else {
            System.out.println(line); // Print message
        }

        line = socketIn.nextLine(); // Receive message
        System.out.println(line); // Print message
        System.out.println("-------------------------------------------------------------------------------------------\n");


        //parte la cli


        /*(cliInterface) {
            CLIView view = new CLIView(socket);
        } else {
            GUIView view = new GUIView(socket);
        }
        view.run();
        */



    }
}
