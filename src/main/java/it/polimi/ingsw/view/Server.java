package it.polimi.ingsw.view;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private List<ClientHandler> waitingClients;
    private List<ClientHandler> playingClients;
    private int clientsNumber;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setClientsNumber(int clientsNumber) {
        this.clientsNumber = clientsNumber;
    }
    public boolean isLobbyEmpty() {
        return waitingClients.isEmpty();
    }
    public synchronized void lobby(ClientHandler client) {

        waitingClients.add(client);
        if (waitingClients.size() == clientsNumber) {
            // move waiting in
            playingClients.addAll(waitingClients);
            waitingClients.clear();
            // Controller controller = new Controller(new Game)
            // executor.submit(controller)
        }


    }

    @Override
    public void run() {

        try {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket, this);
            executor.submit(client);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
