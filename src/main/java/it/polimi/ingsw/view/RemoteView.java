package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Observer;
import java.net.Socket;


public class RemoteView implements Observer<BoardProxy> {
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
        System.out.println(message.toString());
    }
}
