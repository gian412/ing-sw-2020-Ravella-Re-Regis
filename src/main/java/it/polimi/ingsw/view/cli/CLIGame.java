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
    private CLIGame.ReadProxyBoard displayer;
    private ObjectOutputStream out;
    private Socket connSocket;

    private String playerName;
    private int numberOfPlayer;
    private String divinity;


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

        public BoardProxy getLocalProxy() {
            return localProxy;
        }

        @Override
        public void update(BoardProxy message) {
            localProxy = message;
            if (localProxy.getStatus().equals(GameState.SELECTING_GOD))
                out.godList(localProxy);
            if (localProxy.getStatus().equals(GameState.ADDING_WORKER))
                out.boardMaker(localProxy);
            if (localProxy.getStatus().equals(GameState.PLAYING))
                out.boardMaker(localProxy);
            if (localProxy.getStatus().equals(GameState.TERMINATOR))
                out.terminateGame(localProxy);
        }
    }


    public void startPlaying(Socket connection, String name, int number) throws IOException {

        //to manage the turn
        boolean hasMoved = false;
        boolean hasConfirmedMove = false;
        boolean hasBuilt = false;
        boolean hasConfirmedBuild = false;

        boolean hasReBuild = false;
        int[] coord;

        //general data of the player
        numberOfPlayer = number;
        playerName = name;
        displayer = new ReadProxyBoard(playerName, numberOfPlayer);
        connSocket = connection;

        //variables to manage inputs
        int row, column;
        String input;
        int index = 0;
        int choice = 0;

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        out = new ObjectOutputStream(connSocket.getOutputStream());
        listener.addObserver(displayer);
        Thread listen = new Thread(listener);
        listen.start();


        while (true) {
            System.in.read();

            if (displayer.localProxy != null) {
                if (displayer.getLocalProxy().getTurnPlayer().equals(playerName)) {
                    switch (displayer.getLocalProxy().getStatus()) {

                        case SELECTING_GOD:
                            if (displayer.getLocalProxy().getChoosingGods().equals("")) {
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

                            if (!displayer.getLocalProxy().getChoosingGods().equals("")) {
                                boolean check = false;

                                System.out.print("Choose your god: ");
                                input = inputStream.nextLine().trim().toUpperCase();

                                while (!check) {
                                    if (displayer.getLocalProxy().getChoosingGods().toUpperCase().contains(input) && (input.length() > 2)) {
                                        check = true;
                                    } else {
                                        System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
                                        input = inputStream.nextLine().trim().toUpperCase();
                                    }
                                }

                                divinity = input;

                                submitCommand(playerName, new Pair(0, 0), CommandType.CHOOSE_GOD, 0, toGod(input));
                                remoteChangeTurn();
                                System.out.println(displayer.getLocalProxy().getStatus().toString());
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
                            } while (checkWorker(column, row));

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
                            } while (checkWorker(column, row));

                            submitCommand(playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");
                            remoteChangeTurn();
                            break;














                        case PLAYING:

                            GodType god = GodType.getTypeFromString(divinity);

                            switch (god) {

                                case APOLLO:
                                case ATHENA:
                                case CHRONUS:
                                case MINOTAUR:
                                case PAN:
                                case ZEUS:

                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasBuilt) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn" +
                                                "\n    2 - If you can't move GIVE UP\n" +
                                                "\nIndicate the number of your choice: ");
                                        choice = inputStream.nextInt();
                                        inputStream.nextLine();

                                        //input validation
                                        choice = validation2(choice);

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //insert the index of the worker
                                                System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                index = inputStream.nextInt();
                                                inputStream.nextLine();

                                                //input validation
                                                index = validationIndex(index);

                                                System.out.print("\nInsert the cell where move your worker. " );
                                                //insert the cell
                                                coord = chooseCell();

                                                //submit the command and put has move to true
                                                hasMoved = true;
                                                submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                break;

                                            // if you can't move and you GIVE UP
                                            case 2:
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    if (hasMoved && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();

                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                // if you can't move and you GIVE UP
                                                case 2:
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice (Your worker is " + index + "):" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\n    3 - If you can't move GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();

                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. " );
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //insert the index of the worker
                                                    System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                    index = inputStream.nextInt();
                                                    inputStream.nextLine();

                                                    //input validation
                                                    index = validationIndex(index);

                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");
                                                    break;

                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    if(hasMoved && hasBuilt){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            remoteChangeTurn();
                                            hasBuilt = false;
                                            hasMoved = false;
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();

                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    // if you can't move and you GIVE UP
                                                case 2:
                                                    break;
                                            }
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt)
                                    break;




























                                case DEMETER:
                                case HEPHAESTUS:
                                case HESTIA:
                                    // action when you have never moved before in your turn
                                    if(!hasMoved && !hasBuilt && !hasReBuild) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn" +
                                                "\n    2 - If you can't move GIVE UP\n" +
                                                "\nIndicate the number of your choice: ");
                                        choice = inputStream.nextInt();
                                        inputStream.nextLine();

                                        //input validation
                                        choice = validation2(choice);

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //insert the index of the worker
                                                System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                index = inputStream.nextInt();
                                                inputStream.nextLine();
                                                //input validation
                                                index = validationIndex(index);

                                                //insert the cell
                                                System.out.println("\nInsert the cell where move your worker. ");
                                                //insert the cell
                                                coord = chooseCell();

                                                //submit the command and put has move to true
                                                hasMoved = true;
                                                submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                break;

                                            // if you can't move and you GIVE UP
                                            case 2:
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    if(hasMoved && !hasBuilt && !hasReBuild) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    // if you can't move and you GIVE UP
                                                case 2:
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + ");" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\n    3 - If you can't move GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //insert the index of the worker
                                                    System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                    index = inputStream.nextInt();
                                                    inputStream.nextLine();
                                                    //input validation
                                                    index = validationIndex(index);

                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to build
                                    if(hasMoved && hasBuilt && !hasReBuild){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {

                                            System.out.println("Make your choice: " +
                                                    "\n    1 - Build again" +
                                                    "\n    2 - Pass your turn" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation2(choice);

                                            switch(choice){
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level.");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasReBuild = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");
                                                    break;

                                                case 2:
                                                    remoteChangeTurn();
                                                    hasBuilt = false;
                                                    hasMoved = false;
                                                    hasReBuild = false;
                                                    break;
                                            }
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    // if you can't move and you GIVE UP
                                                case 2:
                                                    break;
                                            }
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt && hasReBuild)

                                    //after first try to build again
                                    if(hasMoved && hasBuilt && hasReBuild){
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            remoteChangeTurn();
                                            hasBuilt = false;
                                            hasMoved = false;
                                            hasReBuild = false;
                                            break;
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Pass your turn" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasReBuild = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");
                                                    break;

                                                // if you want pass your turn
                                                case 2:
                                                    remoteChangeTurn();
                                                    hasBuilt = false;
                                                    hasMoved = false;
                                                    hasReBuild = false;
                                                    break;                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && hasBuilt && hasReBuild)
                                    break;





                                case ARTEMIS:

                                    break;






                                case ATLAS:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasBuilt) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn" +
                                                "\n    2 - If you can't move GIVE UP\n" +
                                                "\nIndicate the number of your choice: ");
                                        choice = inputStream.nextInt();
                                        inputStream.nextLine();
                                        //input validation
                                        choice = validation2(choice);

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //insert the index of the worker
                                                System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                index = inputStream.nextInt();
                                                inputStream.nextLine();
                                                //input validation
                                                index = validationIndex(index);

                                                //insert the cell
                                                System.out.println("\nInsert the cell where move your worker. ");
                                                //insert the cell
                                                coord = chooseCell();

                                                //submit the command and put has move to true
                                                hasMoved = true;
                                                submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                break;

                                            // if you can't move and you GIVE UP
                                            case 2:
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    if (hasMoved && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Build a DOME"+
                                                    "\n    3 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation2(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    break;

                                                case 2:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build the DOME. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD_DOME, index, "");

                                                    break;

                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + "): " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\n    3 - If you can't move GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //insert the index of the worker
                                                    System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                    index = inputStream.nextInt();
                                                    inputStream.nextLine();
                                                    //input validation
                                                    index = validationIndex(index);

                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    if(hasMoved && hasBuilt){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            remoteChangeTurn();
                                            hasBuilt = false;
                                            hasMoved = false;
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Build a new DOME"+
                                                    "\n    2 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                case 2:
                                                    //insert the cell where build a dome
                                                    System.out.println("\nInsert the cell where build the DOME. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD_DOME, index, "");

                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt)
                                    break;






                                case CHARON:

                                    break;










                                case TRITON:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasConfirmedMove && !hasBuilt) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn" +
                                                "\n    2 - If you can't move GIVE UP\n" +
                                                "\nIndicate the number of your choice: ");
                                        choice = inputStream.nextInt();
                                        inputStream.nextLine();
                                        //input validation
                                        choice = validation2(choice);

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //insert the index of the worker
                                                System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                index = inputStream.nextInt();
                                                inputStream.nextLine();
                                                //input validation
                                                index = validationIndex(index);

                                                //insert the cell
                                                System.out.println("\nInsert the cell where move your worker. ");
                                                //insert the cell
                                                coord = chooseCell();

                                                //submit the command and put has move to true
                                                hasMoved = true;
                                                submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                break;

                                            // if you can't move and you GIVE UP
                                            case 2:
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    if (hasMoved && !hasConfirmedMove && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasConfirmedMove = true;
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\n    3 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                //move again
                                                case 2:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker ( the cell must be in the perimeter). ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                    break;
                                                    // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + "):" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\n    3 - If you can't build GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //insert the index of the worker
                                                    System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");
                                                    index = inputStream.nextInt();
                                                    inputStream.nextLine();
                                                    //input validation
                                                    index = validationIndex(index);

                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasMovedConfirmed && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    if(hasMoved && hasConfirmedMove && !hasBuilt){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\n    3 - If you can't build or move again GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();

                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    //move again
                                                case 2:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE NO illegal build

                                        //YES illegal move/build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE/BUILD.\n");
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\n    3 - If you can't build or move again GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                //move again
                                                case 2:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move/build
                                    }//CLOSE if(hasMoved && hasBuilt)

                                    if(hasMoved && hasConfirmedMove && hasBuilt){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            remoteChangeTurn();
                                            hasBuilt = false;
                                            hasMoved = false;
                                            hasConfirmedMove = false;

                                        }//CLOSE NO illegal build

                                        //YES illegal move/build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE/BUILD.\n");
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\n    3 - If you can't build or move again GIVE UP\n" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = inputStream.nextInt();
                                            inputStream.nextLine();
                                            //input validation
                                            choice = validation3(choice);

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where build a new level. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");

                                                //move again
                                                case 2:
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where move your worker. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasMoved = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[0]), CommandType.MOVE, index, "");

                                                    break;
                                                // if you can't move and you GIVE UP
                                                case 3:
                                                    break;
                                            }
                                        }//CLOSE yes illegal move/build
                                    }//CLOSE if(hasMoved && hasBuilt && hasConfirmedMoved)


                                    break;


                                case PROMETHEUS:

                                    break;

                            }
                            break;
                    }
                }

                if (displayer.getLocalProxy().getStatus().toString().equals("TERMINATOR")) {
                    return;
                }
            }
        }
    }

    private int[] chooseCell() {
        int column, row;

        System.out.println("Insert the COLUMN: ");
        column = inputStream.nextInt() - 1;
        inputStream.nextLine();
        column = checkCoordinates(column);

        System.out.println("Insert the ROW: ");
        row = inputStream.nextInt() - 1;
        inputStream.nextLine();
        row = checkCoordinates(row);

        int[] coord = new int[2];
        coord[0] = column;
        coord[1] = row;

        return coord;
    }

    private int validation3(int x){
        int choice = x;

        while(!(choice == 1) && !(choice == 2) && !(choice == 3)){
            if(!(choice == 1) && !(choice == 2) && !(choice == 3)) {
                System.out.println("INVALID INPUT. Reinsert a valid inpput: ");
                choice = inputStream.nextInt();
                inputStream.nextLine();
            }
        }

        return choice;
    }

    private int validation2(int x) {
        int choice = x;

        while(!(choice == 1) && !(choice == 2)){
            if(!(choice == 1) && !(choice == 2)) {
                System.out.println("INVALID INPUT. Reinsert a valid inpput: ");
                choice = inputStream.nextInt();
                inputStream.nextLine();
            }
        }

        return choice;
    }

    private int validationIndex(int x){
        int index = x;

        while(!(index == 1) && !(index == 0)){
            if(!(index == 1) && !(index == 2)) {
                System.out.println("INVALID INPUT. Reinsert a valid inpput: ");
                index = inputStream.nextInt();
                inputStream.nextLine();
            }
        }

        return index;
    }

    private String checkGod(String in, StringBuilder selectedGods) {

        String input = in;

        while (true) {
            for (String x : GODS) {
                if (input.equals(x.toUpperCase()) && !selectedGods.toString().contains(x.toUpperCase())) {
                    return x + " ";
                }
            }

            System.out.print("INVALID INPUT.\nReinsert a valid god:  ");
            input = inputStream.nextLine().trim().toUpperCase();
        }
    }

    private int checkCoordinates(int coordinate) {

        while (coordinate < 0 || coordinate > 4) {
            System.out.print("INVALID INPUT.\nReinsert a valid valor (from 1 to 5):  ");
            coordinate = inputStream.nextInt() - 1;
            inputStream.nextLine();
        }
        return coordinate;
    }

    private boolean checkWorker(int column, int row) {
        for (Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()) {
            if ((entry.getValue().y == column) && (entry.getValue().x == row)) {
                return true;
            }
        }
        return false;
    }

    private void remoteChangeTurn() throws IOException {

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);

        out.reset();
        out.writeObject(cmd);
        out.flush();
    }

    private void submitCommand(String playerName, Pair pair, CommandType type, int workerIndex, String message) throws IOException {

        PlayerCommand cmd = new PlayerCommand(playerName, new Command(pair, type), workerIndex);
        cmd.setMessage(message);

        out.reset();
        out.writeObject(cmd);
        out.flush();
    }

    private String toGod(String god) {

        switch (god.toUpperCase()) {
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