package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.io.PrintStream;
import java.util.Scanner;

public class CLIView {

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        PrintStream print = new PrintStream(System.out);

        Game myGame = new Game();
        Controller c = new Controller(myGame);

        c.addPlayer("Rave", 30);
        c.addPlayer("Rave2", 22);

        print.println(myGame.getBoard().toString());

        myGame.getBoard().getCell(new Pair(2, 3)).setWorker(new Worker("1", new Player("Strarave", 30)));

        print.println(myGame.getBoard().toString());



    }
}