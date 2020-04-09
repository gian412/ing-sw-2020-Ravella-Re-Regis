package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final Server server;
    private String name;
    private int age;

    /**
     * Getter of ClientHandler.socket
     *
     * @author Elia Ravella
     * @return a Socket that represent ClientHandler.socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Getter of ClientHandler.age
     *
     * @author Elia Ravella
     * @return an integer that represent ClientHandler.age
     */
    public int getAge() {
        return age;
    }

    /**
     * Getter of ClientHandler.name
     *
     * @author Elia Ravella
     * @return a String that represent ClientHandler.name
     */
    public String getName() {
        return name;
    }

    /**
     * Class constructor with the initialization of the socket and the server for the specific user
     * @param socket the socket of the user
     * @param server the server which the user is connected to
     */
    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    /**
     * Run method for the runnable class ClientHandler.
     * This method open the input and output steams of the socket, asks and receives user's name and age
     * and, if the player is the first of the game to connect, the number of players. Then it call
     * Server.lobby method in order to insert the player in the lobby
     */
    @Override
    public void run() {

        try {
            Scanner socketIn = new Scanner(socket.getInputStream()); // Open input stream with socket
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream()); // Open output stream with socket
            socketOut.println("Insert your name, buddy!"); // Write name request
            socketOut.flush(); // Send name request through socket stream
            this.name = socketIn.nextLine(); // Receive name

            socketOut.println("and now tell me, how old are you?"); // Write age request
            socketOut.flush(); // Send age request through socket stream
            this.age = socketIn.nextInt(); // Receive age

            if (server.isLobbyEmpty()){
                socketOut.println("Creating new game. How many player do you want to play with? (2 or 3 player allowed)");
                socketOut.flush();
                server.setClientsNumber(socketIn.nextInt());
            } else {
                socketOut.println("Adding you to an existing game. The game is composed by "+server.getClientsNumber()+" player.");
            }
            socketOut.println("The game will start when all the players will be connected. Please wait...");
            socketOut.flush();
            server.lobby(this);

        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
