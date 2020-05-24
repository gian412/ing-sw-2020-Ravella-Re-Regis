package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;


public class CLIGame {

    public static final String[] GODS = {"APOLLO", "ARTEMIS", "ATHENA", "ATLAS", "CHARON", "CHRONUS", "DEMETER", "HEPHAESTUS", "HESTIA", "MINOTAUR", "PAN", "PROMETHEUS", "TRITON", "ZEUS"};

    static Scanner inputStream = new Scanner(System.in);
    static String playerName;
    static int numberOfPlayer;
    static CLIGame.readProxyBoard displayer;
    static ObjectOutputStream out;

    //internal class that allow to access to the BoardProxy and print the board when receive a notify
    static class readProxyBoard implements Observer<BoardProxy> {

        CliComposer out;
        BoardProxy localProxy;

        public readProxyBoard() {
            out = new CliComposer();
        }

        public BoardProxy getLocalProxy() {
            return localProxy;
        }

        @Override
        public void update(BoardProxy message) {
            localProxy = message;
            out.boardMaker(localProxy);
        }
    }


    static private void startPlaying(Socket connSocket, String name, int number) throws IOException {
        numberOfPlayer = number;
        playerName = name;
        String input;

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        listener.addObserver(displayer);
        Thread listen = new Thread(listener);
        listen.start();


        while (true) {

            String command = inputStream.nextLine().trim().toUpperCase();
            //check if everything is setted to start the game and check if it is your turn
            if (displayer.getLocalProxy() != null) {
                if (playerName.equals(displayer.getLocalProxy().getTurnPlayer())) {

                    System.out.print("Insert command:");

                    switch (command) {

                        //the first player chooses the gods to use
                        case "SETUPGODS":
                            StringBuilder selectedGods = new StringBuilder();

                            System.out.print("You are the youngest player and you have to choose the gods to use in the game\n" +
                                    "Chose the first god:  ");
                            input = inputStream.nextLine().trim().toUpperCase();
                            selectedGods.append(checkGod(input, selectedGods));

                            System.out.print("Ok now insert the second god:  ");
                            input = inputStream.nextLine().trim().toUpperCase();
                            selectedGods.append(checkGod(input, selectedGods));

                            if(numberOfPlayer == 3){
                                System.out.print("And now insert the last god:  ");
                                input = inputStream.nextLine().toUpperCase();
                                selectedGods.append(checkGod(input, selectedGods));
                            }

                            submitCommand(connSocket, playerName, new Pair(0,0), CommandType.SET_GODS, 0, selectedGods.toString());
                            remoteChangeTurn();
                            break;


                        // a player chooses his god from the list of possible gods which was chosen by the first player
                        case "SELECTGOD":
                            boolean check = false;

                            System.out.print("Choose your god: ");
                            input = inputStream.nextLine().trim().toUpperCase();

                            while(!check) {
                                if (displayer.getLocalProxy().getChoosingGods().contains(input)) {
                                    check = true;
                                } else {
                                    System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                    input = inputStream.nextLine().trim().toUpperCase();
                                }
                            }

                            submitCommand(connSocket, playerName, new Pair(0,0), CommandType.CHOOSE_GOD, 0, input);
                            remoteChangeTurn();
                            break;


                        case "ADDWORKER":

                            int row, column;
                            System.out.println("It's time to insert your workers.");

                            System.out.print("Insert the column of your first worker (from 1 to 5): ");
                            column = inputStream.nextInt() - 1;
                            inputStream.nextLine();

                            column = checkCoordinates(column);

                            System.out.print("Now insert the row of your first worker (from 1 to 5): ");
                            row = inputStream.nextInt() - 1;
                            inputStream.nextLine();

                            row = checkCoordinates(row);

                            submitCommand(connSocket, playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");

                            System.out.print("Insert the column of your second worker (from 1 to 5): ");
                            column = inputStream.nextInt() - 1;
                            inputStream.nextLine();

                            column = checkCoordinates(column);

                            System.out.print("Now insert the row of your second worker (from 1 to 5): ");
                            row = inputStream.nextInt() - 1;
                            inputStream.nextLine();

                            row = checkCoordinates(row);

                            submitCommand(connSocket, playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");

                            remoteChangeTurn();
                            break;

                    }
                }else {
                    System.out.println("IT IS NOT YOUR TURN !!!!!!!!");
                }
            }
        }
    }


    private static String checkGod(String in, StringBuilder selectedGods){

        String input = in;

        while (true) {

            for (String x : GODS) {
                if (input.equals(x) && !selectedGods.toString().contains(x)) {
                    return input;
                }
            }

            System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
            input = inputStream.nextLine().trim().toUpperCase();
        }
    }

    private static int checkCoordinates(int coordinate){

        while(coordinate < 0 || coordinate > 4 ){
            System.out.print("INVALID INPUT.\nReinsert a valid valor (from 1 to 5):  ");
            coordinate = inputStream.nextInt() -1;
            inputStream.nextLine();
        }
        return coordinate;
    }

    private static void remoteChangeTurn() throws IOException {

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);

        out.reset();
        out.writeObject(cmd);
        out.flush();
    }

    private static void submitCommand(Socket connSocket, String playerName, Pair pair, CommandType type,int workerIndex, String message) throws IOException {

        PlayerCommand cmd;
        out = new ObjectOutputStream(connSocket.getOutputStream());

        cmd = new PlayerCommand(playerName, new Command(pair, type), workerIndex);
        cmd.setMessage(message);

        out.reset();
        out.writeObject(cmd);
        out.flush();

    }

}


