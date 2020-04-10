package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ChronusTest {

    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);

            assertTrue("hasMoved must be true", god.hasMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class ChronusrTest: " + e.toString());
            fail("Exception in hasMovedTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasBuild not a dome")
    public void hasBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);

            assertTrue("hasBuild must be true", god.hasBuild);
            assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasBuildNotDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);

            assertTrue("hasBuild must be true", god.hasBuild);
            assertEquals("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasBuildDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasWon = true 'cause there're five completed tower'")
    public void hasWonWithFiveTowersTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the starting cell
        Cell startingCell = board.getCell(1, 0);
        startingCell.setHeight(Height.GROUND);
        startingCell.setWorker(worker);

        // Initialization of the ending cell
        Cell endingCell = board.getCell(1, 1);
        endingCell.setHeight(Height.GROUND);
        endingCell.setWorker(null);

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,0);
        firstCell.setIsCompleted();
        firstCell.setHeight(Height.DOME);
        firstCell.setWorker(null);

        // Initialization of the second cell
        Cell secondCell = board.getCell(0,1);
        secondCell.setIsCompleted();
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(0,2);
        thirdCell.setIsCompleted();
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(0,3);
        fourthCell.setIsCompleted();
        fourthCell.setHeight(Height.DOME);
        fourthCell.setWorker(null);

        // Initialization of the fifth cell
        Cell fifthCell = board.getCell(0,4);
        fifthCell.setIsCompleted();
        fifthCell.setHeight(Height.DOME);
        fifthCell.setWorker(null);


        worker.setCurrentCell(startingCell);

        try{
            god.makeMove(worker, command);

            assertTrue( "hasWon must be true", god.hasWon );
            assertTrue("The number of completed towers must be at list 5", board.countCompleteTower());

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasWon = true")
    public void hasWonTrueTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);

            assertTrue( "hasWon must be true", god.hasWon );
            assertEquals("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);

            assertFalse( "hasWon must be false", god.hasWon );
            assertNotEquals("Worker can't be on the third floor", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonFalseTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class ChronusTest");
        }
    }

}
