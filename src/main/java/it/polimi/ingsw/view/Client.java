package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final String IP = "127.0.0.1";
    private final int PORT = 1337;


    public void run() throws IOException {

        Socket socket = new Socket(IP, PORT); // Start the socket connection
        System.out.println("Connection established");

        // Open the input/output streams with the socket
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());

        // Open the input/output streams with the user
        Scanner stdIn = new Scanner(System.in);
        PrintWriter stdOut = new PrintWriter(System.out);

        // Name request
        String line = socketIn.nextLine(); // Receive name request
        stdOut.println(line); // Print name request

        line = stdIn.nextLine(); // Read name
        socketOut.println(line); // Write name request on socket stream
        socketOut.flush(); // Send name request

        // Age request
        line = socketIn.nextLine(); // Receive age request
        stdOut.println(line); // Print age request

        int number = stdIn.nextInt(); // Read age
        socketOut.println(number); // Write age on socket stream
        socketOut.flush(); // Send age

        // Number of player request or confirm of the insertion in lobby
        line = socketIn.nextLine(); // Receive message
        if (line.equals("Creating new game. How many player do you want to play with?")) { // If message is number of player request
            stdOut.println(line); // Print number of player request

            number = stdIn.nextInt(); // Read number of player
            socketOut.println(number); // Write number of player on socket stream
            socketOut.flush(); // Send number of player
        } else {
            line = socketIn.nextLine(); // Receive message
            stdOut.println(line); // Print message
        }
        line = socketIn.nextLine(); // Receive message
        stdOut.println(line); // Print message

    }
}
