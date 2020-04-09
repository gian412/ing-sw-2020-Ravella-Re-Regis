package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Observer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class RemoteView extends Observable implements Observer<BoardProxy>, Runnable {
    private Socket connSocket;
    private Controller controller;
    private Player player;

    ObjectOutputStream toClient;

    public RemoteView(Socket socket, Controller controller, String player){
        this.controller = controller;
        this.connSocket = socket;
        this.player = new Player(player, -1);
        try {
            this.toClient = new ObjectOutputStream(this.connSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while(connSocket.isConnected()){
            try {
                connSocket.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            notify(new PlayerCommand(this.player, new Command(1, 2, CommandType.MOVE), 0));
        }
    }



    @Override
    public void update(BoardProxy message) {
        try {
            toClient.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
