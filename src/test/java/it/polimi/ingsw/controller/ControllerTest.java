package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Game;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ControllerTest {
    @Test
    @DisplayName("check constructors")
    public void checkConstructors(){
        Game g = new Game();

        assertNotNull("g.getBoard() must be != null", g.getBoard());
    }

}
