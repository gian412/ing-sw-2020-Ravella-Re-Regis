package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Observer;
import java.net.Socket;
import java.util.Observable;


public class RemoteView implements Observer<BoardProxy>, Runnable {
    Socket connSocket;
    Controller controller;
    Player player;

    public RemoteView(Socket socket, Controller controller, Player player){
        this.connSocket = socket;
        this.controller = controller;
        this.player = player;
    }



    @Override
    public void update(BoardProxy message) {
        //send the message through the socket
    }

    @Override
    public void run() {
        if (controller.getTurnPlayer().equals(player)){

        } else {
            // Send an error message via socket
        }
    }
}
