package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class DemeterTest {

    @Test
    @DisplayName("hadMove")
    public void hadMoveTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Demeter(board);
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

            assertTrue("hadMove must be true", god.hadMove);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMoveTest in class DemeterTest: " + e.toString());
            fail("Exception in hadMoveTest in class DemeterTest");
        }
    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Demeter(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

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
            System.err.println("Error e in method hadBuildNotDomeTest in class DemeterTest: " + e.toString());
            fail("Exception in hadBuildNotDomeTest in class DemeterTest");
        }
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Demeter(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

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
            System.err.println("Error e in method hadBuildDomeTest in class DemeterTest: " + e.toString());
            fail("Exception in hadBuildDomeTest in class DemeterTest");
        }
    }

    @Test
    @DisplayName("hadBuildSecond")
    public void hadBuildSecondNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(1, 1, CommandType.BUILD);
        Command secondCommand = new Command(0, 2, CommandType.BUILD);
        Demeter god = new Demeter(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(0, 2);
        thirdCell.setHeight(Height.FIRST_FLOOR);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, firstCommand);

            assertTrue("hadMove must be true", god.hadBuild);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.FIRST_FLOOR);

            try {
                god.makeMove(worker, secondCommand);

                assertTrue("hadMoveSecond must be true", god.hadBuildSecond);
                assertEquals("thirdCell's Height must be one bigger than before", thirdCell.getHeight(), Height.SECOND_FLOOR);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hadBuildSecondTest in class DemeterTest : " + e1.toString());
                fail("Exception in hadBuildSecondTest in class DemeterTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hadBuildSecondTest in class DemeterTest :" + e2.toString());
            fail("Exception in hadBuildSecondTest in class DemeterTest");
        }






    }

    @Test
    @DisplayName("hadBuildSecond")
    public void hadBuildSecondDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(1, 1, CommandType.BUILD_DOME);
        Command secondCommand = new Command(0, 2, CommandType.BUILD_DOME);
        Demeter god = new Demeter(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(0, 2);
        thirdCell.setHeight(Height.THIRD_FLOOR);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, firstCommand);

            assertTrue("hadMove must be true", god.hadBuild);
            assertEquals("secondCell's Height must be DOME", secondCell.getHeight(), Height.DOME);

            try {
                god.makeMove(worker, secondCommand);

                assertTrue("hadMoveSecond must be true", god.hadBuildSecond);
                assertEquals("thirdCell's Height must be DOME", thirdCell.getHeight(), Height.DOME);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hadBuildSecondTest in class DemeterTest : " + e1.toString());
                fail("Exception in hadBuildSecondTest in class DemeterTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hadBuildSecondTest in class DemeterTest :" + e2.toString());
            fail("Exception in hadBuildSecondTest in class DemeterTest");
        }






    }

    @Test
    @DisplayName("hadWin = true")
    public void HadWinTrueTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Demeter(board);
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
            System.err.println("Error e in method HadWinTrueTest in class DemeterTest: " + e.toString());
            fail("Exception in HadWinTrueTest in class DemeterTest");
        }
    }

    @Test
    @DisplayName("hadWin = false")
    public void HadWinFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Demeter(board);
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
            System.err.println("Error e in method HadWinFalseTest in class DemeterTest: " + e.toString());
            fail("Exception in HadWinFalseTest in class DemeterTest");
        }
    }

}
