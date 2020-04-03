package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class AthenaTest {

    @Test
    @DisplayName("hadMove")
    public void hadMoveTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Athena(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMoveTest in class ArtemisTest: " + e.toString());
        }



        assertTrue("hadMove must be true", god.hadMove);
        assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
        assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);
        assertTrue("Worker.canMoveUp must be true", worker.isCanMoveUp());

    }

    @Test
    @DisplayName("hadMove upward")
    public void hadMoveUpwardTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Athena(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMoveTest in class ArtemisTest: " + e.toString());
        }



        assertTrue("hadMove must be true", god.hadMove);
        assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
        assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);
        assertFalse("Worker.canMoveUp must be true", worker.isCanMoveUp());

    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Artemis(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildNotDomeTest in class ArtemisTest: " + e.toString());
        }

        assertTrue("hadBuild must be true", god.hadBuild);
        assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Artemis(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildDomeTest in class ArtemisTest: " + e.toString());
        }

        assertTrue("hadBuild must be true", god.hadBuild);
        assertSame("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);
    }

    @Test
    @DisplayName("hadWin = true")
    public void hadWinYesTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Artemis(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println("Error e in method hadWinYesTest in class ArtemisTest: " + e.toString());
        }

        assertTrue( "hadWin must be true", god.hadWin );
        assertSame("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

    }

    @Test
    @DisplayName("hadWin = false")
    public void hadWinNoTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Artemis(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println("Error e in method hadWinNoTest in class ArtemisTest: " + e.toString());
        }

        assertFalse( "hadWin must be false", god.hadWin );

    }

}
