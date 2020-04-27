package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Observer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class RemoteView extends Observable<PlayerCommand> implements Observer<BoardProxy>, Runnable {
    private Socket connSocket;
    private Controller controller;
    private Player player;
    private boolean disconnected;

    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;

    /**
     * constructor of the RemoteView class
     *
     * @param socket the client to communicate with
     * @param controller the controller of the match (one for all views)
     * @param player a string that represents the player associated to this view.
     */
    public RemoteView(Socket socket, Controller controller, String player){
        disconnected = false;
        this.controller = controller;
        this.connSocket = socket;
        this.player = new Player(player, -1);
        try {
            this.toClient = new ObjectOutputStream(this.connSocket.getOutputStream());
            this.fromClient = new ObjectInputStream(this.connSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * runs the remoteview
     *
     * the main method of the remoteview, it's in charge of scanning the
     * input stream from the client
     */
    @Override
    public void run(){
        if(connSocket.isConnected() && !connSocket.isClosed()){
            while (true){
                try {
                    PlayerCommand playerCommand = (PlayerCommand) fromClient.readObject();
                    if(playerCommand != null){
                        notify(playerCommand);
                    }
                    else break;
                } catch (IOException e) {
                    System.err.println("Client disconnected");
                    disconnected = true;
                    notify(new PlayerCommand(player, new Command(new Pair(-1, -1), CommandType.DISCONNECTED), -1));
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.err.println("Socket not connected or closed. Shutting down");
        }
    }



    @Override
    public void update(BoardProxy message) {
        if(disconnected) return;
        try {
            toClient.writeObject(message);

            if(message.getWinner().getNAME().equals("Unexpected Game Over")) {
                System.out.println("Unexpected game over, shutting down");
                connSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException x){}
    }


}
