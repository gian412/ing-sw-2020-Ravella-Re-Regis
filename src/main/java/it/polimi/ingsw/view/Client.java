package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.Ansi;
import it.polimi.ingsw.view.cli.CLIGame;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    private String ip = "127.0.0.1";
    private int port = 13300;
    private String playername;
    private int numberOfPlayers;

    public void setNumberOfPlayers(int num) {
        this.numberOfPlayers = num;
    }

    public void setPlayername(String name) {
        this.playername = name;
    }

    public String getPlayername() {
        return playername;
    }

    public void setIP(String IP) {
        this.ip = IP;
    }

    public void setPORT(int PORT) {
        this.port = PORT;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * implementation of the Runnable.run() method, runs in a different thread than
     * the caller. Guides the user through the login procedure in a CLI environment
     * 
     * @throws IOException when dealing with socket
     */
    public void run() throws IOException {

        final String LINE = "-------------------------------------------------------------------------------------------";

        // reset the terminal
        System.out.println("\033[H\033[J");

        // Open the input streams with the user
        Scanner stdIn = new Scanner(System.in);

        // maker for the title
        CliComposer composer = new CliComposer();

        // create rhe title
        System.out.println(composer.titleMaker());
        System.out.println("\n\t\t\t\t\t\t----- Press ENTER to continue -----");
        System.in.read();

        // reset the terminal
        System.out.println(Ansi.RESET_SCREEN);

        // log moment where the player insert name, age, and number of player of the
        // game if he is the first player
        // and says if he wants to use GUI or CLI
        System.out.println(composer.bannerMaker());
        String line;
        System.out.println(LINE + "\n");

        // create the connection
        Socket socket = new Socket(ip, port); // Start the socket connection
        System.out.println("Connection established");
        // Open the input/output streams with the socket

        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        System.out.println(LINE + "\n");

        // Name request and control
        String lobbyNames = socketIn.nextLine(); // Receive the name of the other players in the lobby
        String[] names = lobbyNames.split(" ");

        do {
            System.out.print("Insert your name, buddy!   "); // Print name request
            line = stdIn.nextLine().toUpperCase();
        } while (checkNames(names, line) || line.equals(""));

        System.out.println("Name accepted!\n");
        socketOut.println(line);
        socketOut.flush();
        playername = line;
        System.out.println(LINE + "\n");

        // Age request
        int number = 0;
        do {
            System.out.print("And now tell me, how old are you?    "); // Print age request
            try {
                number = stdIn.nextInt(); // Read age
            } catch (InputMismatchException e) {
                System.out.println("INPUT ERROR. Insert an Integer\n");
                stdIn.nextLine();
            }
        } while (number < 1 || number > 99);

        stdIn.nextLine();
        System.out.println("Age valid!\n");
        socketOut.println(number);
        socketOut.flush();
        System.out.println(LINE + "\n");

        // Number of player request or confirm of the insertion in lobby
        line = socketIn.nextLine(); // Receive message
        number = 99;
        if (line.equals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)")) { // If
                                                                                                                   // message
                                                                                                                   // is
                                                                                                                   // number
                                                                                                                   // of
                                                                                                                   // player
                                                                                                                   // request
            System.out.print(line + ":   "); // Print number of player request

            try {
                number = stdIn.nextInt(); // Read number of player
            } catch (InputMismatchException e) {
                System.out.println("INPUT ERROR. Insert an Integer");
                stdIn.nextLine();
            }

            while (true) {
                if (number == 2 || number == 3) {
                    socketOut.println(number); // Write number of player on socket stream
                    socketOut.flush(); // Send number of player
                    setNumberOfPlayers(number);
                    break;
                } else {
                    System.out.print("INVALID INPUT\n\n"
                            + "How many player do you want to play with? (2 or 3 player allowed):   "); // Print number
                                                                                                        // of player
                                                                                                        // request
                    try {
                        number = stdIn.nextInt(); // Read number of player
                    } catch (InputMismatchException e) {
                        System.out.println("INPUT ERROR. Insert an Integer");
                        stdIn.nextLine();
                    }
                }
            }

        } else {
            System.out.println(line); // Print message

            if (line.contains("2"))
                number = 2;
            else if (line.contains("3"))
                number = 3;

            setNumberOfPlayers(number);
        }

        line = socketIn.nextLine(); // Receive message
        System.out.println(line); // Print message
        System.out.println(LINE + "\n");

        CLIGame game = new CLIGame();
        game.startPlaying(socket, playername, numberOfPlayers);
    }

    private boolean checkNames(String[] names, String line) {

        for (String x : names) {
            if (x.equals(line)) {
                return true;
            }
        }
        return false;
    }

}
