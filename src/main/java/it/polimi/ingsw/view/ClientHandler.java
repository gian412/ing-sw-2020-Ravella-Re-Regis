package it.polimi.ingsw.view;

import com.sun.tools.jdeprscan.scan.Scan;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final Server server;
    private String name;
    private int age;

    public Socket getSocket() {
        return socket;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }



    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            socketOut.println("Insert your name, buddy!");
            socketOut.flush();
            this.name = socketIn.nextLine();

            socketOut.println("and now tell me, how old are you?");
            socketOut.flush();
            this.age = socketIn.nextInt();

            if (server.isLobbyEmpty()){
                socketOut.println("Creating new game. How many player do you want to play with?");
                socketOut.flush();
                server.setClientsNumber(socketIn.nextInt());
            }
            server.lobby(this);

        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
