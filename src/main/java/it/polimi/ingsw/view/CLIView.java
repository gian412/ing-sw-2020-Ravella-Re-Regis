package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.cli.CliComposer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CLIView {
    static Scanner inputStream = new Scanner(System.in);
    static Scanner inputNet;
    static PrintStream outNet;
    static Socket socket;
    static String playerName;
    static int playerAge;
    static readProxyBoard displayer;

    static ObjectOutputStream out;

    static class readProxyBoard implements Observer<BoardProxy>{

        CliComposer out;
        BoardProxy localProxy;

        public BoardProxy getLocalProxy() {
            return localProxy;
        }

        public readProxyBoard(){
            out = new CliComposer();
        }

        @Override
        public void update(BoardProxy message) {

            out.boardMaker(message);
            localProxy = message;
        }
    }


    public static void main(String[] args) {
        String command = "";
        displayer = new readProxyBoard();

        while(true){
            command = inputStream.next();
            switch(command){
                case "setname":
                    playerName = inputStream.next();

                    System.out.print("Insert age: ");
                     playerAge = inputStream.nextInt();
                     inputStream.nextLine();

                    break;
                case "connect":
                    inputStream.nextLine();
                    try {
                        socket = new Socket("127.0.0.1", 13300);
                        inputNet = new Scanner(socket.getInputStream());
                        outNet = new PrintStream(socket.getOutputStream());

                        String connectedPlayers = inputNet.nextLine();
                        outNet.println(playerName);
                        outNet.flush();
                        outNet.println(playerAge);
                        outNet.flush();

                        String message = inputNet.nextLine();
                        if(message.equals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)")) {
                            System.out.print("How many Players: ");
                            outNet.println(inputStream.nextInt());
                            inputStream.nextLine();
                            outNet.flush();
                        }else{
                            System.out.println(message);
                        }

                        System.out.println(inputNet.nextLine());

                        startPlaying(socket);

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }
    }

    static private void startPlaying(Socket connSocket) throws IOException {
        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        listener.addObserver(displayer);
        out = new ObjectOutputStream(connSocket.getOutputStream());
        Thread listen = new Thread(listener);
        listen.start();

        while(true){

            System.out.println("Insert command:");
            String command[] = inputStream.nextLine().split(" ");
            PlayerCommand cmd;

            if(displayer.getLocalProxy() != null) {

                if (playerName.equals(displayer.getLocalProxy().getTurnPlayer())) {


                    switch (command[0]) {

                        // un array con tutti i nomi numero dei dei giusto

                        case "setupgods":
                            StringBuilder string = new StringBuilder("");

                            string.append(command[1] + " ");
                            string.append(command[2]);

                            cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.SET_GODS), 0);
                            cmd.setMessage(string.toString());

                            out.reset();
                            out.writeObject(cmd);
                            out.flush();

                            remoteChangeTurn();

                            break;

                        // controllo dio
                        case "selectgods":
                            cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHOOSE_GOD), 0);
                            cmd.setMessage(command[1]);

                            out.reset();
                            out.writeObject(cmd);
                            out.flush();

                            remoteChangeTurn();

                            break;
                    }
                } else {
                    System.out.println("IT IS NOT YOUR TURN !!!!!!!!");
                }
            }
            /*}
            else{
                System.out.println("IT IS NOT YOUR TURN !!!!!!!!");
            }*/

        }

    }

    public static void remoteChangeTurn() throws IOException{

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(new Pair(0,0), CommandType.CHANGE_TURN), 0);

        out.reset();
        out.writeObject(cmd);
        out.flush();

    }
}