package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private List<ClientHandler> waitingClients;
    private List<ClientHandler> playingClients;
    private int clientsNumber;
    private final int PORT = 1337;

    /**
     * Class constructor whit the initialization of the serverSocket
     * @author Gianluca Regis
     */
    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        waitingClients = new ArrayList<>();
        playingClients = new ArrayList<>();
    }

    /**
     * Getter for clientNumber
     *
     * @author Gianluca Regis
     * @return clientNumber
     */
    public int getClientsNumber() {
        return this.clientsNumber;
    }
    /**
     * Setter for clientsNumber
     *
     * @author Gianluca Regis
     * @param clientsNumber the integer to which clientsNumber is to be set
     */
    public void setClientsNumber(int clientsNumber) {
        this.clientsNumber = clientsNumber;
    }

    /**
     * Getter for waitingClients.size
     * @return waitingClients.isEmpty
     */
    public boolean isLobbyEmpty() {
        return waitingClients.isEmpty();
    }

    /**
     * Method that simulates a lobby for users in which there is the waiting/playing lists' manager
     *
     * @authors Gianluca Regis, Elia Ravella
     * @param client the ClientHandler class of the user who enter the lobby
     */
    public synchronized void lobby(ClientHandler client) {

        waitingClients.add(client);
        if (waitingClients.size() == clientsNumber) {
            // move waiting in
            playingClients.addAll(waitingClients);
            waitingClients.clear();

            startPlaying();
        }
    }

    /**
     * The method that starts the game
     *
     * @author Elia Ravella
     */
    private synchronized void startPlaying() {
        // creating game and controller
        Game game = new Game();
        Controller controller = new Controller(game);

        // adding players, setting observers
        for(ClientHandler x : playingClients){
            controller.addPlayer(x.getName(), x.getAge());
            RemoteView remoteView = new RemoteView(x.getSocket(), controller, x.getName());
            remoteView.addObserver(controller);
            game.getBoard().addView(remoteView);
            Thread rv = new Thread(remoteView);
            rv.start();
        }

        controller.startGame();
        playingClients.clear();

    }

    /**
     * The method run for the runnable class Server.
     * This method receive the incoming connections and call the ClientHandler in a thread
     *
     * @author Gianluca Regis
     */
    @Override
    public void run() {

        System.out.println("Server ready on port " + PORT);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted, dispatching Client Handler");
                ClientHandler client = new ClientHandler(socket, this);
                executor.submit(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
