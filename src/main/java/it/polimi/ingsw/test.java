package it.polimi.ingsw;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.RemoteView;

import java.net.Socket;

public class test {
    public static void main(String[] args) {
        RemoteView rv = new RemoteView(new Socket());
        Board b = new Board();
        b.addView(rv);
        b.updateProxyBoard();

    }
}
