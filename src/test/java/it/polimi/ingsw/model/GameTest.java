package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;

public class GameTest {


    @Test
    @DisplayName("addPlayerTest")
    public void addPlayerTest() {

        Game game = new Game();
        Player player = new Player("player1", 10);

        game.addPlayer("player1", 10);

        assertTrue("the player is added in list player",
               game.getPlayerList().get(0).equals(player) && (game.getPlayerList().size() == 1));
    }



}
