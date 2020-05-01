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

        assertTrue(game.getPlayers().contains(player.getNAME()));
        assertTrue(game.getPlayers().contains("10"));
    }



}
