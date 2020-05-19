package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
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


        public BoardProxy getLocalProxy() {
            return localProxy;
        }

        public readProxyBoard() {
            out = new CliComposer();
        }

        @Override
        public void update(BoardProxy message) {

            out.boardMaker(message);
            localProxy = message;
        }
    }


    static private void startPlaying(Socket connSocket, String name, int number) throws IOException {


        numberOfPlayer = number;
        playerName = name;

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        listener.addObserver(displayer);
        out = new ObjectOutputStream(connSocket.getOutputStream());
        Thread listen = new Thread(listener);
        listen.start();
        String input;

        while (true) {

            System.out.print("Insert command:");

            String command = inputStream.nextLine().trim().toUpperCase();
            PlayerCommand cmd;

            //check if everything is setted to start the game and check if it is your turn
            if (displayer.getLocalProxy() != null) {
                if (playerName.equals(displayer.getLocalProxy().getTurnPlayer())) {

                    switch (command) {

                        //the first player chooses the gods to use
                        case "SETUPGODS":
                            StringBuilder selectedGods = new StringBuilder();
                            boolean checked = false;

                            if(numberOfPlayer == 2) {
                                System.out.print("You are the youngest player and you have to choose the gods to use in the game\n" +
                                        "Chose the first god:  ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while (!checked) {

                                    for (String x : GODS) {
                                        if (input.equals(x)) {
                                            selectedGods.append(x + " ");
                                            checked = true;
                                        }
                                    }

                                    if (!checked) {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().trim().toUpperCase();
                                    }
                                }

                                System.out.print("Ok now insert the second god:  ");
                                input = inputStream.nextLine().trim().toUpperCase();
                                checked = false;

                                while (!checked) {

                                    for (String x : GODS) {
                                        if (input.equals(x) && !selectedGods.toString().contains(x)) {
                                            selectedGods.append(x);
                                            checked = true;
                                        }
                                    }

                                    if (!checked) {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().trim().toUpperCase();
                                    }
                                }


                                cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.SET_GODS), 0);
                                cmd.setMessage(selectedGods.toString());

                                out.reset();
                                out.writeObject(cmd);
                                out.flush();

                                remoteChangeTurn();
                                break;
                            }

                            else if(numberOfPlayer == 3){
                                System.out.print("You are the youngest player and you have to choose the gods to use in the game\n" +
                                        "Chose the first god:  ");
                                input = inputStream.nextLine().toUpperCase();

                                while (!checked) {

                                    for (String x : GODS) {
                                        if (input.equals(x)) {
                                            selectedGods.append(x + " ");
                                            checked = true;
                                        }
                                    }

                                    if (!checked) {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().toUpperCase();
                                    }
                                }

                                System.out.print("Ok now insert the second god:  ");
                                input = inputStream.nextLine().toUpperCase();
                                checked = false;

                                while (!checked) {

                                    for (String x : GODS) {
                                        if (input.equals(x) && !selectedGods.toString().contains(x)) {
                                            selectedGods.append(x);
                                            checked = true;
                                        }
                                    }

                                    if (!checked) {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().toUpperCase();
                                    }
                                }

                                System.out.print("And now insert the last god:  ");
                                input = inputStream.nextLine().toUpperCase();
                                checked = false;

                                while (!checked) {

                                    for (String x : GODS) {
                                        if (input.equals(x) && !selectedGods.toString().contains(x)) {
                                            selectedGods.append(x);
                                            checked = true;
                                        }
                                    }

                                    if (!checked) {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().toUpperCase();
                                    }
                                }

                                cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.SET_GODS), 0);
                                cmd.setMessage(selectedGods.toString());

                                out.reset();
                                out.writeObject(cmd);
                                out.flush();

                                remoteChangeTurn();
                                break;
                            }


                        // a player chooses his god from the list of possible gods which was chosen by the first player
                        case "SELECTGOD":
                            System.out.print("Choose your god: ");
                            input = inputStream.nextLine().trim().toUpperCase();

                            if(displayer.getLocalProxy().getChoosingGods().contains(input)) {

                                cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHOOSE_GOD), 0);
                                cmd.setMessage(input);

                                out.reset();
                                out.writeObject(cmd);
                                out.flush();

                            }

                            remoteChangeTurn();

                            break;
                    }
                } else {
                    System.out.println("IT IS NOT YOUR TURN !!!!!!!!");
                }
            }

        }
    }


    public static void remoteChangeTurn() throws IOException {

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);

        out.reset();
        out.writeObject(cmd);
        out.flush();

    }
}


