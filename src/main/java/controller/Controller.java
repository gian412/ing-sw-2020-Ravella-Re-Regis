package controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;

import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Game myGame = new Game();
        myGame.addPlayer(new Player("p1"));
        myGame.addPlayer(new Player("p2"));

        Board gen = myGame.getBoard();
        System.out.println(gen.toString());

    }
}
