package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.utils.GodType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    @DisplayName("getNameTest")
    public void getNameTest() {

        Player player = new Player("name", 5);

        assertTrue("getNAME is ok", player.getNAME().equals("name"));
    }

    @Test
    @DisplayName("getandsetTurnPlayerTest")
    public void getandsetTurnPlayerTest() {

        Player player = new Player("name", 5);
        player.setTurnPlayer(true);

        assertTrue("get and setTurnPlayer is ok", player.getTurnPlayer());
    }

    @Test
    @DisplayName("getandsetDivinityTest")
    public void getandsetDivinityTest() {

        Player player = new Player("name", 5);
        player.setDivinity(new Apollo(new Board()));

        assertTrue("getandsetDivinity is ok", player.getDivinity().NAME == GodType.APOLLO);
    }

    @Test
    @DisplayName("getAgeTest")
    public void getAgeTest() {

        Player player = new Player("name", 5);

        assertTrue("getAge is ok", player.getAge() == 5);
    }

    @Test
    @DisplayName("getandsetNextPlayerTest")
    public void getAndSetNextPlayerTest() {

        Player player = new Player("name", 5);
        player.setNextPlayer(new Player("name2", 5));

        assertTrue("getandsetNextPlayer is ok", player.getNextPlayer().equals(new Player("name2", 5)));
    }

    @Test
    @DisplayName("getWorkersTest")
    public void getWorkersTest() {

        Player player = new Player("name", 5);
        Worker[] workers = player.getWorkers();

        assertTrue("getWorkers is ok", workers[0].getWORKER_ID().equals("name0") && workers[1].getWORKER_ID().equals("name1"));
    }


}
