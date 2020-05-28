package it.polimi.ingsw.view;

import it.polimi.ingsw.view.cli.CLIGame;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    private String playername;
    private int numberOfPlayers;

    public void setNumberOfPlayers(int num){
        this.numberOfPlayers = num;
    }

    public void setPlayername(String name){
        this.playername = name;
    }

    public String getPlayername() {
        return playername;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

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
        System.out.println("-------------------------------------------------------------------------------------------\n");

        //create the connection
        //String IP = "192.168.178.108";
        String IP = "127.0.0.1";
        int PORT = 13300;
        Socket socket = new Socket(IP, PORT); // Start the socket connection
        System.out.println("Connection established");
        // Open the input/output streams with the socket

        /*if (guiInterface) {
            guiView(Socket socket);
            return;
        }*/

        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        System.out.println("-------------------------------------------------------------------------------------------\n");

        // Name request and control
        String lobbyNames =  socketIn.nextLine(); // Receive the name of the other players in the lobby

        do {
            System.out.println("Insert your name, buddy!\n"); // Print name request
            line = stdIn.nextLine().toUpperCase();
        }while(lobbyNames.contains(line));

        System.out.println("Name accepted!\n");
        socketOut.println(line);
        socketOut.flush();
        playername = line;
        System.out.println("-------------------------------------------------------------------------------------------\n");

        // Age request
        int number;
        do {
            System.out.println("And now tell me, how old are you?\n"); // Print age request
            number = stdIn.nextInt(); // Read age
            stdIn.nextLine();
        }while (number < 1 || number > 99);

        System.out.println("Age valid!\n");
        socketOut.println(number);
        socketOut.flush();
        System.out.println("-------------------------------------------------------------------------------------------\n");

        // Number of player request or confirm of the insertion in lobby
        line = socketIn.nextLine(); // Receive message
        if (line.equals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)")) { // If message is number of player request
            System.out.println(line); // Print number of player request

            number = stdIn.nextInt(); // Read number of player
            stdIn.nextLine();
            while (true) {
                if (number==2 || number==3) {
                    socketOut.println(number); // Write number of player on socket stream
                    socketOut.flush(); // Send number of player
                    setNumberOfPlayers(number);
                    break;
                } else {
                    System.out.println("INVALID INPUT\n\n" +
                            "How many player do you want to play with? (2 or 3 player allowed)"); // Print number of player request
                    number = stdIn.nextInt(); // Read number of player
                }
            }

        } else {
            System.out.println(line); // Print message

            if(line.contains("2"))
                number = 2;
            else if (line.contains("3"))
                    number = 3;

            setNumberOfPlayers(number);
        }

        line = socketIn.nextLine(); // Receive message
        System.out.println(line); // Print message
        System.out.println("-------------------------------------------------------------------------------------------\n");

        CLIGame game = new CLIGame();
        game.startPlaying(socket, playername, number);
    }
}
