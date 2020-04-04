package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class HephaestusTest {

    @Test
    @DisplayName("hadMove")
    public void hadMoveTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Hephaestus(board);
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
            System.err.println("Error e in method hadMoveTest in class HephaestusTest: " + e.toString());
            fail("Exception in hadMoveTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Hephaestus(board);
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
            System.err.println("Error e in method hadBuildNotDomeTest in class HephaestusTest: " + e.toString());
            fail("Exception in hadBuildNotDomeTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Hephaestus(board);
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
            System.err.println("Error e in method hadBuildDomeTest in class HephaestusTest: " + e.toString());
            fail("Exception in hadBuildDomeTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hadBuildSecond")
    public void hadBuildSecondNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(1, 1, CommandType.BUILD);
        Command secondCommand = new Command(1, 1, CommandType.BUILD);
        Hephaestus god = new Hephaestus(board);
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

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, firstCommand);

            assertTrue("hadMove must be true", god.hadBuild);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.FIRST_FLOOR);

            try {
                god.makeMove(worker, secondCommand);

                assertTrue("hadMoveSecond must be true", god.hadBuildSecond);
                assertEquals("thirdCell's Height must be one bigger than before", secondCell.getHeight(), Height.SECOND_FLOOR);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hadBuildSecondTest in class HephaestusTest : " + e1.toString());
                fail("Exception in hadBuildSecondTest in class HephaestusTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hadBuildSecondTest in class HephaestusTest :" + e2.toString());
            fail("Exception in hadBuildSecondTest in class HephaestusTest");
        }






    }

    /*@Test
    @DisplayName("hadBuildSecond")
    public void hadBuildSecondDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(1, 1, CommandType.BUILD_DOME);
        Command secondCommand = new Command(1, 1, CommandType.BUILD_DOME);
        Hephaestus god = new Hephaestus(board);
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
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, firstCommand);

            assertTrue("hadMove must be true", god.hadBuild);
            assertEquals("secondCell's Height must be DOME", secondCell.getHeight(), Height.THIRD_FLOOR);

            assertThrows("Build a dome as second move must throw an exception",  IllegalMoveException.class, () -> god.makeMove(worker, secondCommand));

        } catch (IllegalMoveException e1) {
            System.err.println("Error e1 in method hadBuildSecondDomeTest in class HephaestusTest :" + e1.toString());
            fail("Exception in hadBuildSecondDomeTest in class HephaestusTest");
        }
    }*/

    @Test
    @DisplayName("hadWin = true")
    public void hadWinYesTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Hephaestus(board);
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
            System.err.println("Error e in method hadWinYesTest in class HephaestusTest: " + e.toString());
            fail("Exception in hadWinYesTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hadWin = false")
    public void hadWinNoTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Hephaestus(board);
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
            System.err.println("Error e in method hadWinNoTest in class HephaestusTest: " + e.toString());
            fail("Exception in hadWinNoTest in class HephaestusTest");
        }
    }

}
