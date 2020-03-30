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

        Player p1 = new Player("a", 10), p2 = new Player("b", 10);

        try {
            p1 = g.getTurnPlayer();
            c.changeTurnPlayer();
            p2 = g.getTurnPlayer();
        }catch (Exception x)
        {
            System.out.println(x.getMessage());
        }

        System.out.println(p1.getNAME() + " " + p2.getNAME());

    }
}
