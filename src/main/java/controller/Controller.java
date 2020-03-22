package controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.Atlas;
import it.polimi.ingsw.model.god.Hephaestus;

import java.util.Scanner;

public class Controller {

    private Game game;

    public Controller(Game g){
        this.game = g;
    }

    public static void main(String[] args) {
            Game g1 = new Game();

            // this code is only for testing purpose, it simulates a game

            Player p1 = new Player("Rave");
            Player p2 = new Player("Rave2");
            p1.setWorkers();
            p1.getWorkers()[0].setCurrentCell(g1.getBoard().getCell(1, 0));

            p2.setWorkers();
            p1.setDivinity(new Atlas(g1.getBoard()));
            p2.setDivinity(new Hephaestus(g1.getBoard()));

            System.out.println(g1.getBoard().toString());
            Cell[] AtlasMove = new Cell[2];
            AtlasMove[0] = g1.getBoard().getCell(0,0);
            AtlasMove[1] = g1.getBoard().getCell(1,1);
            try {
                p1.divinity.makeMove(p1.getWorkers()[0], AtlasMove, true);
            }catch(IllegalMoveException e){
                System.out.println(e.getMessage());
            }
            System.out.println(g1.getBoard().toString());

    }

}
