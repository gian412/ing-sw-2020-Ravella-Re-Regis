package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ChronusTest {

    @Test
    @DisplayName("hadMoved")
    public void hadMovedTest(){

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

            assertTrue("hadMoved must be true", god.hadMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMovedTest in class ChronusrTest: " + e.toString());
            fail("Exception in hadMovedTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMoved = true;

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

            assertTrue("hadBuild must be true", god.hadBuild);
            assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildNotDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hadBuildNotDomeTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMoved = true;

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

            assertTrue("hadBuild must be true", god.hadBuild);
            assertEquals("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hadBuildDomeTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hadWin = true 'cause there're five completed tower'")
    public void hadWinWithFiveTowersTest(){

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
        firstCell.setHeight(Height.DOME);
        firstCell.setWorker(null);

        // Initialization of the second cell
        Cell secondCell = board.getCell(0,1);
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(0,2);
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(0,3);
        fourthCell.setHeight(Height.DOME);
        fourthCell.setWorker(null);

        // Initialization of the fifth cell
        Cell fifthCell = board.getCell(0,4);
        fifthCell.setHeight(Height.DOME);
        fifthCell.setWorker(null);


        worker.setCurrentCell(startingCell);

        try{
            god.makeMove(worker, command);

            assertTrue( "hadWin must be true", god.hadWin );
            assertEquals("The number of completed towers must be at list 5", board.countCompleteTower(), true);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method HadWinTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in HadWinTrueTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hadWin = true")
    public void HadWinTrueTest(){

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

            assertTrue( "hadWin must be true", god.hadWin );
            assertEquals("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method HadWinTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in HadWinTrueTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hadWin = false")
    public void HadWinFalseTest(){

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

            assertFalse( "hadWin must be false", god.hadWin );
            assertNotEquals("Worker can't be on the third floor", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method HadWinFalseTest in class ChronusTest: " + e.toString());
            fail("Exception in HadWinFalseTest in class ChronusTest");
        }
    }

}
