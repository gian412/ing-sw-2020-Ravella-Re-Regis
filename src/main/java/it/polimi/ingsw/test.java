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
        c.addPlayer("io", 1);
        c.addPlayer("yu", 2);
        c.startGame();

        try {
            Player p1 = g.getTurnPlayer();
            c.changeTurnPlayer();
            Player p2 = g.getTurnPlayer();
        }catch (Exception x)
        {
            System.out.println(x.getMessage());
        }

    }
}
