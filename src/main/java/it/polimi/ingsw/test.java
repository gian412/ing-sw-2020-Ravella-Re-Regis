package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.RemoteView;

import java.net.Socket;

public class test {
    public static void main(String[] args) {
        Game g = new Game();
        Controller c = new Controller(g);
        RemoteView rv = new RemoteView(new Socket(), c, new Player("ciao", 30));
        g.getBoard().getProxy().addObserver(rv);
        c.addPlayer("Marco", 30);
        System.out.println(g.getPlayers());


    }
}
