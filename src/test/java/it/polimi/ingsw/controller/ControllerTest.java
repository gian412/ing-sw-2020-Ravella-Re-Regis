package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    @DisplayName("checking the base 'add player' function")
    public void checkAddPlayer(){
        Game g = new Game();
        Controller controller = new Controller(g);

        assertTrue("I should be able to simply add a Player by name and age" , controller.addPlayer("marco", 30));
    }

    @Test
    @DisplayName("the turnplayer actually change when issued the 'changeTurnPlayer' command")
    public void checkTurnPlayer(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Elia", 20);

        controller.startGame();

        Player p1 = g.getTurnPlayer();
        controller.changeTurnPlayer();
        Player p2 = g.getTurnPlayer();

        assertNotEquals(p1, p2);

    }


}
