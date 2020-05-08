package it.polimi.ingsw.view;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientHandlerTest {
    @Test
    @DisplayName("basic testing of the function")
    public void basicTest(){
        // setting up and starting the server, waiting on port 1337
        Thread server = new Thread(new Server());
        server.start();

        try {
            // initializing of the connection, creating a simple player
            Socket clientSocket = new Socket("127.0.0.1", 1337);
            PrintStream ps = new PrintStream(clientSocket.getOutputStream());
            Scanner in = new Scanner(clientSocket.getInputStream());

            // all the instructions are managed by the view: we don't need that here
            ps.println("Marco");
            ps.println(15);
            in.nextLine();

            // with the first player, i'm expecting to create a new game, so
            assertEquals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)", in.nextLine());

            // then I say I want a 2 player game
            ps.println(2);

            // then I should get
            assertEquals("The game will start when all the players will be connected. Please wait...", in.nextLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
