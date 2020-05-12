package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;

public class WorkerTest {

    @Test
    @DisplayName("getWORKER_IDTest")
    public void getWORKER_IDTest() {

        Worker worker = new Worker("name1", new Player("name", 5));

        assertTrue("getWORKER_ID is ok", worker.getWORKER_ID().equals("name1"));
    }

    @Test
    @DisplayName("getOwnerTest")
    public void getOwnerTest() {

        Worker worker = new Worker("name1", new Player("name", 5));

        assertTrue("getOwner is ok", worker.getOwner().equals(new Player ("name", 5)));
    }

    @Test
    @DisplayName("getandsetCurrentCellTest")
    public void getandsetCurrentCellTest() {

        Worker worker = new Worker("name1", new Player("name", 5));
        worker.setCurrentCell(new Cell(0,0));

        assertTrue("getandsetCurrentCell is ok", worker.getCurrentCell().equals(new Cell(0,0)));
    }

    @Test
    @DisplayName("getandsetPreviousCellTest")
    public void getandsetPreviousCellTest() {

        Worker worker = new Worker("name1", new Player("name", 5));
        worker.setPreviousCell(new Cell(0,0));

        assertTrue("getandsetPreviousCell is ok", worker.getPreviousCell().equals(new Cell(0,0)));
    }

    @Test
    @DisplayName("getandsetCanMoveUpTest")
    public void getandsetCanMoveUpTest() {

        Worker worker = new Worker("name1", new Player("name", 5));
        worker.setCanMoveUp(true);

        assertTrue("getandsetCanMoveUp is ok", worker.isCanMoveUp());
    }

}
