package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.controller.Command;
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
    int index = 0;


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

    //to manage the turn
    boolean hasMoved = false;
    boolean hasConfirmedMove = false;
    boolean hasBuilt = false;
    boolean hasReMoved = false;
    boolean hasForced = false;
    boolean hasReBuild = false;


    public void startPlaying(Socket connection, String name, int number) throws IOException {

        //general data of the player
        numberOfPlayer = number;
        playerName = name;
        displayer = new ReadProxyBoard(playerName, numberOfPlayer);
        connSocket = connection;

        //variables to manage inputs
        int choice;
        int[] coord;

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        out = new ObjectOutputStream(connSocket.getOutputStream());
        listener.addObserver(displayer);
        Thread listen = new Thread(listener);
        listen.start();


        while (true) {
            System.in.read();

            if (displayer.localProxy != null) {
                if(displayer.getLocalProxy().getStatus().equals(GameState.TERMINATOR)){
                    return;
                }

                if (displayer.getLocalProxy().getTurnPlayer().equals(playerName)) {
                    switch (displayer.getLocalProxy().getStatus()) {

                        case SELECTING_GOD:
                            selectingGod();
                            break;

                        case ADDING_WORKER:
                            addingWorker();
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
                                        System.out.println("Start your turn");

                                        //submit the command and put has move to true
                                        index = workerIndex();
                                        move(index);
                                        hasMoved = true;
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //when you have already tried to move
                                    else if (hasMoved && !hasBuilt) {
                                            // NO illegal move
                                            if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                                System.out.println("Build a new level");

                                                //submit the command and put hasBuilt to true
                                                build(index);
                                                hasBuilt = true;
                                            }//CLOSE no illegal move

                                            //YES illegal move
                                            if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                                System.out.println("ILLEGAL MOVE.\n" +
                                                        "Make your choice (Your worker is " + index + "):" +
                                                        "\n    1 - Insert a new cell" +
                                                        "\n    2 - Change the worker" +
                                                        "\nIndicate the number of your choice: ");
                                                choice = chooseOption();

                                                //possible options
                                                switch (choice) {
                                                    //if you want to move
                                                    case 1:
                                                        //submit the command and put has move to true
                                                        move(index);
                                                        break;

                                                    // if you want to change worker to move and then move him
                                                    case 2:
                                                        //submit the command
                                                        index = workerIndex();
                                                        move(index);
                                                        break;
                                                }
                                            }//CLOSE yes illegal move
                                        }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    else if (hasMoved && hasBuilt) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasMoved = false;
                                            remoteChangeTurn();
                                            break;
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n"+
                                                    "\n    Insert a new cell");

                                            //submit the command and put has move to true
                                            build(index);
                                            break;
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt)
                                    break;

                                case DEMETER:
                                case HEPHAESTUS:
                                case HESTIA:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasBuilt && !hasReBuild)  {
                                        System.out.println("Start your turn");

                                        //submit the command and put has move to true
                                        index = workerIndex();
                                        move(index);
                                        hasMoved = true;
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    else if (hasMoved && !hasBuilt && !hasReBuild) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");

                                            //submit the command and put has move to true
                                            hasBuilt = true;
                                            build(index);
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + ");" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    move(index);
                                                    break;

                                                // if you want to change worker to move and then move
                                                case 2:
                                                    //submit the command and put has move to true
                                                    index = workerIndex();
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to build
                                    else if (hasMoved && hasBuilt && !hasReBuild) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice: " +
                                                    "\n    1 - Build again" +
                                                    "\n    2 - Pass your turn" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            switch (choice) {
                                                case 1:
                                                    //submit the command and put has move to true
                                                    hasReBuild = true;
                                                    build(index);
                                                    break;

                                                case 2:
                                                    hasBuilt = false;
                                                    hasMoved = false;
                                                    hasReBuild = false;
                                                    remoteChangeTurn();
                                                    break;
                                            }
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Insert a new cell");

                                            //submit the command and put has move to true
                                            build(index);
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt && hasReBuild)

                                    //after first try to build again
                                    else if (hasMoved && hasBuilt && hasReBuild) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasMoved = false;
                                            hasReBuild = false;
                                            remoteChangeTurn();
                                            break;
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Pass your turn" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    build(index);
                                                    break;
                                                // if you want pass your turn
                                                case 2:
                                                    hasBuilt = false;
                                                    hasMoved = false;
                                                    hasReBuild = false;
                                                    remoteChangeTurn();
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }// CLOSE if(hasMoved && hasBuilt && hasReBuild)
                                    break;

                                case ARTEMIS:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasReMoved && !hasBuilt) {
                                        System.out.println("Start your turn");

                                        //submit the command and put has move to true
                                        index = workerIndex();
                                        hasMoved = true;
                                        move(index);
                                    }//CLOSE if(!hasMoved && !hasReMoved && !hasBuilt)

                                    else if (hasMoved && !hasBuilt && !hasReMoved) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    build(index);
                                                    break;

                                                case 2:
                                                    hasReMoved = true;
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + "):" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command
                                                    move(index);
                                                    break;

                                                // if you want to change worker to move and then move
                                                case 2:
                                                    //submit the command
                                                    index = workerIndex();
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasReMoved && !hasBuilt)

                                    else if (hasMoved && hasReMoved && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");

                                            //submit the command and put has move to true
                                            build(index);
                                            hasBuilt = true;
                                        }

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasReMoved = false;
                                            System.out.println("ILLEGAL MOVE\n" +
                                                    "Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //submit the command
                                                    hasBuilt = true;
                                                    build(index);
                                                    break;

                                                // move again
                                                case 2:
                                                    //submit the command
                                                    hasReMoved = true;
                                                    move(index);
                                                    break;
                                            }
                                        }
                                    }//Close if(hasMoved && hasReMoved && !hasBuilt)

                                    else if ((hasMoved && !hasReMoved && hasBuilt)) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasConfirmedMove = false;
                                            hasMoved = false;
                                            remoteChangeTurn();
                                        }

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            System.out.println("ILLEGAL BUILD\n" +
                                                    "Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    build(index);
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //submit the command and put has move to true
                                                    move(index);
                                                    hasReMoved = true;
                                                    break;
                                            }
                                        }
                                    }//Close if(hasMoved && !hasConfirmedMove && hasBuilt)

                                    else if(hasMoved && hasReMoved && hasBuilt){
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasConfirmedMove = false;
                                            hasMoved = false;
                                            remoteChangeTurn();
                                        }

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD\n"+
                                                    "Build a new level");
                                            build(index);
                                        }
                                    }//CLOSE if(hasMoved && hasReMoved && hasBuilt)
                                    break;

                                case ATLAS:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasBuilt) {
                                        System.out.println("Start your turn");

                                        //submit the command and put has move to true
                                        index = workerIndex();
                                        hasMoved = true;
                                        move(index);
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    else if (hasMoved && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Build a DOME" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    build(index);
                                                    break;

                                                case 2://insert the cell

                                                    //check that you are not building under yourself
                                                    boolean underYou;
                                                    String workerName;
                                                    do {
                                                        underYou = false;
                                                        System.out.println("\nInsert the cell where build a new level");
                                                        //insert the cell
                                                        coord = chooseCell();

                                                        for(Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()){
                                                            workerName = playerName.toUpperCase() + index;
                                                            if(entry.getKey().equals(workerName)){
                                                                if(entry.getValue().x == coord[1] && entry.getValue().y == coord[0] && !toGod(divinity).equals(GODS[13])) {
                                                                    underYou = true;
                                                                }
                                                            }
                                                        }
                                                    }while(underYou);

                                                    //submit the command and put has move to true
                                                    hasBuilt = true;
                                                    submitCommand(playerName, new Pair(coord[1], coord[0]), CommandType.BUILD_DOME, index, "");

                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + "): " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    move(index);
                                                    break;

                                                // if you want to change worker to move and then move him
                                                case 2:
                                                    //insert the index of the worker
                                                    index = workerIndex();
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    else if (hasMoved && hasBuilt) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasMoved = false;
                                            remoteChangeTurn();
                                        }//CLOSE NO illegal build

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Make your choice: " +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Build a new DOME" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //submit the command and put has move to true
                                                    build(index);
                                                    break;

                                                case 2:
                                                    //check that you are not building under yourself
                                                    boolean underYou;
                                                    String workerName;
                                                    do {
                                                        underYou = false;
                                                        System.out.println("\nInsert the cell where build a new level");
                                                        //insert the cell
                                                        coord = chooseCell();

                                                        for(Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()){
                                                            workerName = playerName.toUpperCase() + index;
                                                            if(entry.getKey().equals(workerName)){
                                                                if(entry.getValue().x == coord[1] && entry.getValue().y == coord[0] && !toGod(divinity).equals(GODS[13])) {
                                                                    underYou = true;
                                                                }
                                                            }
                                                        }
                                                    }while(underYou);

                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD_DOME, index, "");
                                                    break;
                                            }
                                        }//CLOSE YES illegal build
                                    }//CLOSE if(hasMoved && hasBuilt
                                    break;

                                case CHARON:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasBuilt && !hasForced) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn and MOVE" +
                                                "\n    2 - Start your turn and FORCE" +
                                                "\nIndicate the number of your choice: ");
                                        choice = chooseOption();

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //submit the command and put has move to true
                                                index = workerIndex();
                                                hasMoved = true;
                                                move(index);
                                                break;

                                            case 2:
                                                //insert the index of the worker
                                                index = workerIndex();

                                                //insert the cell
                                                System.out.println("\nInsert the cell where is the worker to force. ");
                                                //insert the cell
                                                coord = chooseCell();

                                                //submit the command and put has move to true
                                                hasForced = true;
                                                submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.FORCE, index, "");
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt && !hasForced)

                                    else if (hasMoved && !hasBuilt && !hasForced) {
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");
                                            hasBuilt = true;
                                            build(index);
                                        }//CLOSE no illegal move

                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasMoved = false;
                                            System.out.println("ILLEGAL MOVE" +
                                                    " Make your choice : " +
                                                    "\n    1 - MOVE" +
                                                    "\n    2 - FORCE" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    index = workerIndex();
                                                    hasMoved = true;
                                                    move(index);
                                                    break;

                                                case 2:
                                                    //insert the index of the worker
                                                    index = workerIndex();
                                                    //insert the cell
                                                    System.out.println("\nInsert the cell where is the worker to force. ");
                                                    //insert the cell
                                                    coord = chooseCell();

                                                    //submit the command and put has move to true
                                                    hasForced = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.FORCE, index, "");
                                                    break;
                                            }// close switch when you don't have moved before
                                        }
                                    }

                                    else if (!hasMoved && !hasBuilt && hasForced) {
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Make your move");
                                                    hasMoved = true;
                                                    move(index);
                                                    break;
                                        }

                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasForced = false;
                                            System.out.println("ILLEGAL FORCE" +
                                                    " Make your choice : " +
                                                    "\n    1 - MOVE" +
                                                    "\n    2 - FORCE" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    index = workerIndex();
                                                    hasMoved = true;
                                                    move(index);
                                                    break;

                                                case 2:
                                                    index = workerIndex();
                                                    System.out.println("\nInsert the cell where is the worker to force. ");
                                                    //insert the cell
                                                    coord = chooseCell();
                                                    //submit the command and put has move to true
                                                    hasForced = true;
                                                    submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.FORCE, index, "");
                                                    break;
                                            }// close switch
                                        }
                                    }

                                    else if (hasMoved && !hasBuilt && hasForced) {
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");
                                            //submit the command and put has move to true
                                            hasBuilt = true;
                                            build(index);
                                            break;
                                        }

                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE\n" +
                                                    "Insert a new cell");
                                            move(index);
                                        }
                                    }

                                    else if (hasBuilt && hasMoved) {
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasForced = false;
                                            hasMoved = false;
                                            remoteChangeTurn();
                                        }

                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD" +
                                                    "\nInsert a new cell");
                                            build(index);
                                        }
                                    }
                                    break;

                                case TRITON:
                                    // action when you have never moved before in your turn
                                    if (!hasMoved && !hasConfirmedMove && !hasBuilt) {
                                        System.out.println("Start your turn");

                                        //submit the command and put has move to true
                                        index = workerIndex();
                                        hasMoved = true;
                                        move(index);
                                    }//CLOSE if(!hasMoved && !hasBuilt)

                                    //after first try to move
                                    else if (hasMoved && !hasConfirmedMove && !hasBuilt) {
                                        // NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasConfirmedMove = true;
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    hasBuilt = true;
                                                    build(index);

                                                //move again
                                                case 2:
                                                    //submit the command and put has move to true
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice (Your worker is " + index + "):" +
                                                    "\n    1 - Insert a new cell" +
                                                    "\n    2 - Change the worker" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command
                                                    move(index);
                                                    break;

                                                // if you want to change worker to move and then move
                                                case 2:
                                                    //submit the command
                                                    index = workerIndex();
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE yes illegal move
                                    }//CLOSE if(hasMoved && !hasMovedConfirmed && !hasBuilt)

                                    //if you have moved successfully and you have already tried to built
                                    else if (hasMoved && hasConfirmedMove && !hasBuilt) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Build a new level" +
                                                "\n    2 - Move again" +
                                                "\nIndicate the number of your choice: ");
                                        choice = chooseOption();

                                        //possible options
                                        switch (choice) {
                                            //if you want to build
                                            case 1:
                                                //submit the command and put has move to true
                                                build(index);

                                                //move again
                                            case 2:
                                                //submit the command and put hasBuilt to true
                                                hasBuilt = true;
                                                build(index);
                                                break;
                                        }
                                    }//CLOSE if(hasMoved && hasBuilt)

                                    else if (hasMoved && hasConfirmedMove && hasBuilt) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasMoved = false;
                                            hasConfirmedMove = false;
                                            remoteChangeTurn();
                                        }//CLOSE NO illegal build

                                        //YES illegal move/build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            System.out.println("ILLEGAL MOVE/BUILD.\n");
                                            System.out.println("Make your choice : " +
                                                    "\n    1 - Build a new level" +
                                                    "\n    2 - Move again" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to build
                                                case 1:
                                                    //submit the command and put has move to true
                                                    build(index);
                                                    hasBuilt = true;
                                                    break;

                                                //move again
                                                case 2:
                                                    //submit the command and put has move to true
                                                    move(index);
                                                    break;
                                            }
                                        }//CLOSE yes illegal move/build
                                    }//CLOSE if(hasMoved && hasBuilt && hasConfirmedMoved)
                                    break;


                                case PROMETHEUS:
                                    if (!hasMoved && !hasBuilt && !hasReBuild) {
                                        System.out.println("Make your choice : " +
                                                "\n    1 - Start your turn and MOVE" +
                                                "\n    2 - Start your turn and BUILD" +
                                                "\nIndicate the number of your choice: ");
                                        choice = chooseOption();

                                        //possible options
                                        switch (choice) {
                                            //if you want to move
                                            case 1:
                                                //submit the command and put has move to true
                                                index = workerIndex();
                                                hasMoved = true;
                                                move(index);
                                                break;

                                            case 2:
                                                index =workerIndex();
                                                hasBuilt = true;
                                                build(index);
                                                break;
                                        }// close switch when you don't have moved before
                                    }//CLOSE if(!hasMoved && !hasBuilt && !hasRebuild)

                                    else if (!hasMoved && hasBuilt && !hasReBuild) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Move your worker");
                                            hasMoved = true;
                                            move(index);
                                        }//CLOSE no illegal move

                                        //YES illegal move/build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            System.out.println("BUILD.\n" +
                                                    "Make your choice : " +
                                                    "\n    1 - MOVE" +
                                                    "\n    2 - BUILD" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    index = workerIndex();
                                                    hasMoved = true;
                                                    move(index);
                                                    break;

                                                case 2:
                                                    index = workerIndex();
                                                    build(index);
                                                    break;
                                            }// close switch when you don't have moved before
                                        }//CLOSE (!hasMoved && hasBuilt && !hasReBuild)
                                    }

                                    else if (hasMoved && !hasBuilt && !hasReBuild) {
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");
                                            hasReBuild = true;
                                            build(index);
                                        }//CLOSE no illegal move

                                        //YES illegal move/build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasMoved = false;
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Make your choice : " +
                                                    "\n    1 - MOVE" +
                                                    "\n    2 - BUILD" +
                                                    "\nIndicate the number of your choice: ");
                                            choice = chooseOption();

                                            //possible options
                                            switch (choice) {
                                                //if you want to move
                                                case 1:
                                                    //submit the command and put has move to true
                                                    index = workerIndex();
                                                    hasMoved = true;
                                                    move(index);
                                                    break;

                                                case 2:
                                                    index = workerIndex();
                                                    hasBuilt = true;
                                                    build(index);
                                                    break;
                                            }// close switch when you don't have moved before
                                        }
                                    }//CLOSE (hasMoved && !hasBuilt && !hasReBuild)

                                    else if (hasMoved && hasBuilt && !hasReBuild) {
                                        //NO illegal move
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("Build a new level");
                                            hasReBuild = true;
                                            build(index);
                                        }//CLOSE no illegal move

                                        //YES illegal move
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL MOVE.\n" +
                                                    "Insert a new cell");
                                                    move(index);
                                        }
                                    }//CLOSE if(hasMoved && hasBuilt && !hasReBuild)

                                    else if (hasMoved && hasBuilt && hasReBuild){
                                        //NO illegal build
                                        if (displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            hasBuilt = false;
                                            hasMoved = false;
                                            hasReBuild = false;
                                            remoteChangeTurn();
                                        }

                                        //YES illegal build
                                        if (!displayer.getLocalProxy().getIllegalMoveString().equals("")) {
                                            System.out.println("ILLEGAL BUILD.\n" +
                                                    "Insert a new cell ");
                                            build(index);
                                        }
                                    }//CLOSE if (hasMoved && hasBuilt && hasReBuild)
                                    break;
                            }
                            break;
                    }
                }
            }
        }
    }


    private void move(int index) throws IOException {
        int[] coord;

        System.out.print("\nInsert the cell where move your worker. " );
        //insert the cell
        coord = chooseCell();

        submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.MOVE, index, "");
        return;
    }

    private void build(int index) throws IOException {
        int[] coord;
        boolean underYou;
        String workerName;

        do {
            underYou = false;
            //insert the cell
            System.out.println("\nInsert the cell where build a new level");
            //insert the cell
            coord = chooseCell();

            //control that you are not building under yourself
            for(Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()){
                workerName = playerName.toUpperCase() + index;
                if(entry.getKey().equals(workerName)){
                    if(entry.getValue().x == coord[1] && entry.getValue().y == coord[0] && !toGod(divinity).equals(GODS[13])) {
                        underYou = true;
                    }
                }
            }
        }while(underYou);

        submitCommand(playerName, new Pair(coord[0], coord[1]), CommandType.BUILD, index, "");
        return;
    }

    private int chooseOption(){
        int choice;

        choice = inputStream.nextInt();
        inputStream.nextLine();
        //input validation
        choice = validation(choice);

        return choice;
    }

    private int[] chooseCell() {

        int[] coord = new int[2];
        int input;
        int x = 0,y = 1;
        String workerName;

        do {
            workerName = playerName + index;
            for(Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()){
                if(entry.getKey().equals(workerName)) {
                    x = entry.getValue().x;
                    y = entry.getValue().y;
                    break;
                }
            }

            do {
                System.out.print("Choose the direction:" +
                        " \n  1 - North-West" +
                        " \n  2 - North" +
                        " \n  3 - North-East" +
                        " \n  4 - West" +
                        " \n  5 - Your cell (if your god permit it to you)" +
                        " \n  6 - West" +
                        " \n  7 - South-West" +
                        " \n  8 - South" +
                        " \n  9 - South-East" +
                        "\nMake your choice:  ");

                input = inputStream.nextInt();
                inputStream.nextLine();
            } while (input <= 0 || input > 9);

            if (input == 1) {
                x = x - 1;
                y = y - 1;
            } else if (input == 2) {
                x = x;
                y = y - 1;
            } else if (input == 3) {
                x = x + 1;
                y = y - 1;
            } else if (input == 4) {
                x = x - 1;
                y = y;
            } else if (input == 5) {
                x = x;
                y = y;
            } else if (input == 6) {
                x = x + 1;
                y = y;
            } else if (input == 7) {
                x = x - 1;
                y = y + 1;
            } else if (input == 8) {
                x = x
                ;
                y = y + 1;
            } else if (input == 9) {
                x = x + 1;
                y = y + 1;
            }
        }while(!checkCoordinates(x) && !checkCoordinates(y));

        coord[0] = x;
        coord[1] = y;
        return coord;

        /*int column, row;

        do{
            System.out.println("Insert the COLUMN: ");
            column = inputStream.nextInt() - 1;
            inputStream.nextLine();
        }while (checkCoordinates(column));

        do {
            System.out.println("Insert the ROW: ");
            row = inputStream.nextInt() - 1;
            inputStream.nextLine();
        }while (checkCoordinates(row));


        int[] coord = new int[2];
        coord[0] = column;
        coord[1] = row;

        return coord;*/
    }


    private int validation(int x) {
        int choice = x;

        while(!(choice == 1) && !(choice == 2)){
            System.out.println("INVALID INPUT. Reinsert a valid input: ");
            choice = inputStream.nextInt();
            inputStream.nextLine();
        }

        return choice;
    }

    private int validationIndex(int x){
        int index = x;

        while(!(index == 1) && !(index == 0)){
            System.out.println("INVALID INPUT. Reinsert a valid inpput: ");
            index = inputStream.nextInt();
            inputStream.nextLine();
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

    private int checkCoordinatesWorker(int coordinate) {

        while (coordinate < 0 || coordinate > 4) {
            System.out.print("INVALID INPUT.\nReinsert a valid valor (from 1 to 5):  ");
            coordinate = inputStream.nextInt();
            coordinate = coordinate -1;
            inputStream.nextLine();
        }
        return coordinate;
    }

    private boolean checkCoordinates(int coordinate) {

        if(coordinate >= 0 && coordinate <= 4)
            return true;
        return false;
    }

    private boolean checkWorker(int column, int row) {
        for (Map.Entry<String, Pair> entry : displayer.getLocalProxy().getWorkers().entrySet()) {
            if ((entry.getValue().y == row) && (entry.getValue().x == column)) {
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

    private void selectingGod() throws IOException {
        String input;

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
            return;
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
            return;
        }
    }

    private void addingWorker() throws IOException {
        int row, column;

        System.out.println("It's time to insert your workers.");

        do {
            System.out.print("Insert the column of your first worker (from 1 to 5): ");
            column = inputStream.nextInt() - 1;
            inputStream.nextLine();

            column = checkCoordinatesWorker(column);

            System.out.print("Now insert the row of your first worker (from 1 to 5): ");
            row = inputStream.nextInt() - 1;
            inputStream.nextLine();

            row = checkCoordinatesWorker(row);

        } while (checkWorker(column, row));

        submitCommand(playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");

        do {
            System.out.print("Insert the column of your second worker (from 1 to 5): ");
            column = inputStream.nextInt() - 1;
            inputStream.nextLine();

            column = checkCoordinatesWorker(column);

            System.out.print("Now insert the row of your second worker (from 1 to 5): ");
            row = inputStream.nextInt() - 1;
            inputStream.nextLine();

            row = checkCoordinatesWorker(row);
        } while (checkWorker(column, row));

        submitCommand(playerName, new Pair(column, row), CommandType.ADD_WORKER, 0, "");
        remoteChangeTurn();
        return;
    }

    private int workerIndex(){
        int index = 0;
        boolean pass = true;

        //you can only move
        System.out.println("\nChoose the worker to move (indicate the INDEX 0 or 1): ");

        do {
            try{
                index = inputStream.nextInt();
                inputStream.nextLine();
                //input validation
                index = validationIndex(index);
                pass = false;
            }catch(Exception x) {
                pass = true;
            }
        }while(pass);

        return index;
    }

}