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

    }
}
