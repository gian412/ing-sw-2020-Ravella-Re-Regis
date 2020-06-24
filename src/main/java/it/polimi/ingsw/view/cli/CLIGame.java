package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;


public class CLIGame {

    public final String[] GODS = {"Apollo", "Artemis", "Athena", "Atlas", "Charon", "Chronus", "Demeter", "Hephaestus", "Hestia", "Minotaur", "Pan", "Prometheus", "Triton", "Zeus"};

    private Scanner inputStream = new Scanner(System.in);
    private String playerName;
    private int numberOfPlayer;
    private CLIGame.ReadProxyBoard displayer;
    private ObjectOutputStream out;
    private Socket connSocket;

    //internal class that allow to access to the BoardProxy and print the board when receive a notify
    public class ReadProxyBoard implements Observer<BoardProxy> {

        private CliComposer out;
        private String playerName;
        private int numberOfPlayer;
        private BoardProxy localProxy;

        public ReadProxyBoard(String name, int number) {
            playerName = name;
            numberOfPlayer = number;
            out = new CliComposer(playerName, numberOfPlayer);
        }

        public BoardProxy getLocalProxy(){
            return localProxy;
        }

        @Override
        public void update(BoardProxy message) {
            localProxy = message;
            if(localProxy.getStatus().equals(GameState.SELECTING_GOD))
                out.godList(localProxy);
            if(localProxy.getStatus().equals(GameState.ADDING_WORKER))
                out.boardMaker(localProxy);
            if(localProxy.getStatus().equals(GameState.PLAYING)){
                out.boardMaker(localProxy);
                System.out.println("ilvshbkshfbveuhfvhn");
            }

        }
    }


    public void startPlaying(Socket connection, String name, int number) throws IOException {
        numberOfPlayer = number;
        playerName = name;
        displayer = new ReadProxyBoard(playerName, numberOfPlayer);
        connSocket = connection;
        int row, column;
        String input;

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        out = new ObjectOutputStream(connSocket.getOutputStream());
        listener.addObserver(displayer);
        Thread listen = new Thread(listener);
        listen.start();


        while (true) {
            System.in.read();
            if (displayer.localProxy != null){
                if (displayer.getLocalProxy().getTurnPlayer().equals(playerName)){
                    switch (displayer.getLocalProxy().getStatus()){

                        case SELECTING_GOD:
                            if (displayer.getLocalProxy().getChoosingGods().equals("")){
                                StringBuilder selectedGods = new StringBuilder();

                                System.out.print("You are the youngest player and you have to choose the gods to use in the game\n" +
                                        "Chose the first god:  ");
                                input = inputStream.nextLine().toUpperCase().trim();
                                selectedGods.append(checkGod(input, selectedGods));

                                System.out.print("Ok now insert the second god:  ");
                                input = inputStream.nextLine().trim().toUpperCase();
                                selectedGods.append(checkGod(input, selectedGods));

                                if (numberOfPlayer == 3) {
                                    System.out.print("And now insert the last god:  ");
                                    input = inputStream.nextLine().toUpperCase();
                                    selectedGods.append(checkGod(input, selectedGods));
                                }

                                submitCommand(playerName, new Pair(0, 0), CommandType.SET_GODS, 0, selectedGods.toString());
                                remoteChangeTurn();
                                break;
                            }

                            if(!displayer.getLocalProxy().getChoosingGods().equals("")){
                                boolean check = false;

                                System.out.print("Choose your god: ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while(!check) {
                                    if (displayer.getLocalProxy().getChoosingGods().toUpperCase().contains(input) && (input.length() > 2)) {
                                        check = true;
                                    } else {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().trim().toUpperCase();
                                    }
                                }

                                submitCommand(playerName, new Pair(0,0), CommandType.CHOOSE_GOD, 0, toGod(input));
                                remoteChangeTurn();
                                break;
                            }

                        case ADDING_WORKER:

                            System.out.println("It's time to insert your workers.");

                            do {
                                System.out.print("Insert the column of your first worker (from 1 to 5): ");
                                column = inputStream.nextInt() - 1;
                                inputStream.nextLine();

                                column = checkCoordinates(column);

                                System.out.print("Now insert the row of your first worker (from 1 to 5): ");
                                row = inputStream.nextInt() - 1;
                                inputStream.nextLine();
                            }while(checkWorker(column, row));

                            submitCommand(playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");

                            do {
                                System.out.print("Insert the column of your second worker (from 1 to 5): ");
                                column = inputStream.nextInt() - 1;
                                inputStream.nextLine();

                                column = checkCoordinates(column);

                                System.out.print("Now insert the row of your second worker (from 1 to 5): ");
                                row = inputStream.nextInt() - 1;
                                inputStream.nextLine();

                                row = checkCoordinates(row);
                            }while(checkWorker(column, row));

                            submitCommand(playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");

                            System.out.print("Number of workers: " + displayer.getLocalProxy().getWorkers().size());

                            remoteChangeTurn();
                            break;



                        case PLAYING:


                            System.out.print("udajsvbgslihcvglbi.hjsvbòi.bre" +
                                    "bearb" +
                                    "etdbtae" +
                                    "bte" +
                                    "ntreanaetbfztr" +
                                    "nrtnrnarngtrntg");
                            break;


                        case TERMINATOR:
                            break;
                    }
                }
            }































/*

            System.out.print("Insert command:");
            String command = inputStream.nextLine().trim().toUpperCase();
            //check if everything is setted to start the game and check if it is your turn
            if (displayer.getLocalProxy() != null) {
                if (playerName.equals(displayer.getLocalProxy().getTurnPlayer())) {

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

                            System.out.print("Insert the column of your second worker : ");
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

                        case "MOVE":
                            int workerIndex;

                            while(true) {
                                System.out.print("Choose the worker you want to move (indicate the index of the worker: 1 or 2) or QUIT to change your choice: ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while (!input.equals(1) || !input.equals(2) || input.equals("CHANGE")) {
                                    System.out.print("INVALID INPUT.\nReinsert a valid index (1 or 2) or QUIT to change your choice:  ");
                                    input = inputStream.nextLine().trim().toUpperCase();
                                }

                                if (input.equals("QUIT")) {
                                    break;
                                } else {
                                    workerIndex = Integer.parseInt(input);
                                }

                                System.out.print("Insert the cell in which move your worker. Insert the row (from 1 to 5) or QUIT to change your choice: ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while (!input.equals(1) || !input.equals(2) || !input.equals(3) || !input.equals(4) || !input.equals(5) || input.equals("CHANGE")) {
                                    System.out.print("INVALID INPUT.\nReinsert a valid row (from 1 to 5) or QUIT to change your choice:  ");
                                    input = inputStream.nextLine().trim().toUpperCase();
                                }

                                if (input.equals("QUIT")) {
                                    break;
                                } else {
                                    row = Integer.parseInt(input) - 1;
                                }

                                System.out.print("Insert the column (from 1 to 5) or QUIT to change your choice: ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while (!input.equals(1) || !input.equals(2) || !input.equals(3) || !input.equals(4) || !input.equals(5) || input.equals("CHANGE")) {
                                    System.out.print("INVALID INPUT.\nReinsert a valid column (from 1 to 5) or QUIT to change your choice:  ");
                                    input = inputStream.nextLine().trim().toUpperCase();
                                }

                                if (input.equals("QUIT")) {
                                    break;
                                } else {
                                    column = Integer.parseInt(input) - 1;
                                }

                                submitCommand(connSocket, playerName, new Pair(column, row), CommandType.MOVE, workerIndex, "");



                                break;
                            }

                    }
                }else {
                    System.out.println("IT IS NOT YOUR TURN !!!!!!!!");
                }
            }*/
        }
    }


    private String checkGod(String in, StringBuilder selectedGods){

        String input = in;

        while (true) {

            for (String x : GODS) {
                if (input.equals(x.toUpperCase()) && !selectedGods.toString().contains(x.toUpperCase())) {
                    return x;
                }
            }

            System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
            input = inputStream.nextLine().trim().toUpperCase();
        }
    }

    private int checkCoordinates(int coordinate){

        while(coordinate < 0 || coordinate > 4 ){
            System.out.print("INVALID INPUT.\nReinsert a valid valor (from 1 to 5):  ");
            coordinate = inputStream.nextInt() -1;
            inputStream.nextLine();
        }
        return coordinate;
    }

    private boolean checkWorker(int column, int row){
            for(Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()){
                if((entry.getValue().y == column) && (entry.getValue().x == row)) {
                    return true;
                }
            }
            return false;
        }


    private void remoteChangeTurn() throws IOException{

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);

        out.reset();
        out.writeObject(cmd);
        out.flush();
    }

    private void submitCommand(String playerName, Pair pair, CommandType type,int workerIndex, String message) throws IOException {

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(pair, type), workerIndex);
        cmd.setMessage(message);

        out.reset();
        out.writeObject(cmd);
        out.flush();

    }

    private String toGod(String god){

        switch(god.toUpperCase()){

            case "APOLLO":
                return GODS[0];
            case "ARTEMIS":
                return GODS[1];
            case "ATHENA":
                return GODS[2];
            case "ATLAS":
                return GODS[3];
            case "CHARON":
                return GODS[4];
            case "CHRONUS":
                return GODS[5];
            case "DEMETER":
                return GODS[6];
            case "HEPHAESTUS":
                return GODS[7];
            case "HESTIA":
                return GODS[8];
            case "MINOTAUR":
                return GODS[9];
            case "PAN":
                return GODS[10];
            case "PROMETHEUS":
                return GODS[11];
            case "TRITON":
                return GODS[12];
            case "ZEUS":
                return GODS[13];
        }
        return "error";
    }

}


