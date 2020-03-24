package view;

import controller.Controller;
import it.polimi.ingsw.model.Game;

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



    }
}
