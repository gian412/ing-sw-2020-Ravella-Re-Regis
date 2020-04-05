package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class TritonTest {

    @Test
    @DisplayName("hadMoved")
    public void hadMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Triton(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(0,1);
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(1,1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);

            assertTrue("hadMoved must be true", god.hadMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMovedTest in class TritonTest: " + e.toString());
            fail("Exception in hadMovedTest in class TritonTest");
        }
    }

    @Test
    @DisplayName("hadMovedSecond")
    public void hadMovedThreeTimeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(0, 1, CommandType.MOVE);
        Command secondCommand = new Command(0, 2, CommandType.MOVE);
        Command thirdCommand = new Command(0, 3, CommandType.MOVE);
        Triton god = new Triton(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(1,1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(0,1);
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(0, 2);
        thirdCell.setHeight(Height.FIRST_FLOOR);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(0, 3);
        thirdCell.setHeight(Height.GROUND);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, firstCommand);

            assertTrue("hadMoved must be true", god.hadMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);

            try {
                god.makeMove(worker, secondCommand);

                assertTrue("hadMoved must be true for the second time", god.hadMoved);
                assertEquals("worker's previous position must be secondCell", worker.getPreviousCell(), secondCell);
                assertEquals("worker's position must be thirdCell", worker.getCurrentCell(), thirdCell);

                try {
                    god.makeMove(worker, thirdCommand);

                    assertTrue("hadMoved must be true for the third time", god.hadMoved);
                    assertEquals("worker's previous position must be thirdCell", worker.getPreviousCell(), thirdCell);
                    assertEquals("worker's position must be fourthCell", worker.getCurrentCell(), fourthCell);
                } catch (IllegalMoveException e1) {
                    System.err.println("Error e1 in method hadMovedThreeTimeTest in class TritonTest : " + e1.toString());
                    fail("Exception in hadMovedThreeTimeTest in class TritonTest");
                }
            } catch (IllegalMoveException e2) {
                System.err.println("Error e1 in method hadMovedThreeTimeTest in class TritonTest : " + e2.toString());
                fail("Exception in hadMovedThreeTimeTest in class TritonTest");
            }
        } catch (IllegalMoveException e3) {
            System.err.println("Error e2 in method hadMovedThreeTimeTest in class TritonTest :" + e3.toString());
            fail("Exception in hadMovedThreeTimeTest in class TritonTest");
        }






    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Triton(board);
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
            System.err.println("Error e in method hadBuildNotDomeTest in class TritonTest: " + e.toString());
            fail("Exception in hadBuildNotDomeTest in class TritonTest");
        }
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Triton(board);
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
            assertSame("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildDomeTest in class TritonTest: " + e.toString());
            fail("Exception in hadBuildDomeTest in class TritonTest");
        }
    }

    @Test
    @DisplayName("hadWin = true")
    public void HadWinTrueTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Triton(board);
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
            assertSame("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method HadWinTrueTest in class TritonTest: " + e.toString());
            fail("Exception in HadWinTrueTest in class TritonTest");
        }
    }

    @Test
    @DisplayName("hadWin = false")
    public void HadWinFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Triton(board);
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

        } catch (IllegalMoveException e){
            System.err.println("Error e in method HadWinFalseTest in class TritonTest: " + e.toString());
            fail("Exception in HadWinFalseTest in class TritonTest");
        }
    }

}
