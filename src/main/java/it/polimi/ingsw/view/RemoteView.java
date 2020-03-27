package it.polimi.ingsw.view;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.view.Observer;
import java.net.Socket;


public class RemoteView implements Observer<BoardProxy> {
    Socket connSocket;

    public RemoteView(Socket s){
        this.connSocket = s;
    }

    @Override
    public void update(BoardProxy message) {
        System.out.println(message.toString());
    }
}
