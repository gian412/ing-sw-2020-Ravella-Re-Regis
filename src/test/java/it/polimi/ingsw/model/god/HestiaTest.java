package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class HestiaTest {

    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);

            assertTrue("hasMoved must be true", god.hasMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class HestiaTest: " + e.toString());
            fail("Exception in hasMovedTest in class HestiaTest");
        }
    }

    @Test
    @DisplayName("hasBuild not a dome")
    public void hasBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);

            assertTrue("hasBuild must be true", god.hasBuild);
            assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasBuildNotDomeTest in class HestiaTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class HestiaTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);

            assertTrue("hasBuild must be true", god.hasBuild);
            assertEquals("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasBuildDomeTest in class HestiaTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class HestiaTest");
        }
    }

    @Test
    @DisplayName("hasBuildSecond not a dome")
    public void hasBuildSecondNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(0, 1), CommandType.BUILD);
        Hestia god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 1));
        thirdCell.setHeight(Height.FIRST_FLOOR);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);

            assertTrue("hasMoved must be true", god.hasBuild);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.FIRST_FLOOR);

            try {
                god.executeCommand(worker, secondCommand);

                assertTrue("hasMovedSecond must be true", god.hasBuildSecond);
                assertEquals("thirdCell's Height must be one bigger than before", thirdCell.getHeight(), Height.SECOND_FLOOR);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hasBuildSecondTest in class HestiaTest : " + e1.toString());
                fail("Exception in hasBuildSecondTest in class HestiaTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hasBuildSecondTest in class HestiaTest :" + e2.toString());
            fail("Exception in hasBuildSecondTest in class HestiaTest");
        }






    }

    @Test
    @DisplayName("hasBuildSecond dome")
    public void hasBuildSecondDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        Command secondCommand = new Command(new Pair(0, 1), CommandType.BUILD_DOME);
        Hestia god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 1));
        thirdCell.setHeight(Height.THIRD_FLOOR);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);

            assertTrue("hasMoved must be true", god.hasBuild);
            assertEquals("secondCell's Height must be DOME", secondCell.getHeight(), Height.DOME);

            try {
                god.executeCommand(worker, secondCommand);

                assertTrue("hasMovedSecond must be true", god.hasBuildSecond);
                assertEquals("thirdCell's Height must be DOME", thirdCell.getHeight(), Height.DOME);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hasBuildSecondTest in class HestiaTest : " + e1.toString());
                fail("Exception in hasBuildSecondTest in class HestiaTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hasBuildSecondTest in class HestiaTest :" + e2.toString());
            fail("Exception in hasBuildSecondTest in class HestiaTest");
        }






    }

    @Test
    @DisplayName("hasWon = true")
    public void hasWonTrueTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.executeCommand(worker, command);

            assertTrue( "hasWon must be true", god.hasWon );
            assertEquals("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonTrueTest in class HestiaTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class HestiaTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hestia(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.executeCommand(worker, command);

            assertFalse( "hasWon must be false", god.hasWon );
            assertNotEquals("Worker can't be on the third floor", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonFalseTest in class HestiaTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class HestiaTest");
        }
    }

}
