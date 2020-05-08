package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ChronusTest {

    // Common tests
    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
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
            System.err.println("Error e in method hasMovedTest in class ChronusTest: " + e.toString());
            fail("Exception in hasMovedTest in class ChronusTest");
        }

    }

    @Test
    @DisplayName("canMoveUp=false")
    public void moveWithNotCanMoveUp(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
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
        worker.setCanMoveUp(false);

        try {
            god.executeCommand(worker, command);
            fail("moveWithNotCanMoveUp in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }
    }

    @Test
    @DisplayName("moveUpMoreThan1Floor")
    public void moveUpMoreThan1Floor() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveUpMoreThan1Floor in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("moveOnADome")
    public void moveOnADome() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.THIRD_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("hasMovedMoreThanOneCell")
    public void hasMovedMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(3, 3));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("moveUpWhenCannotMoveUp")
    public void moveUpWhenCannotMoveUp() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        worker.setCanMoveUp(false);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.FIRST_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveUpWhenCannotMoveUp in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("hasMovedSecondTime")
    public void hasMovedSecondTime(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

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
            fail("hasMovedSecondTime in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("hasBuild but hasn't moved")
    public void hasBuildBeforeHasMoved(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = false;

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
            fail("hasBuildBeforeHasMoved in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertFalse("hasBuild must be false", god.hasBuild);
            assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.SECOND_FLOOR);
        }
    }

    @Test
    @DisplayName("hasBuild a dome but hasn't moved")
    public void hasBuildDomeBeforeHasMoved(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = false;

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
            fail("hasBuildDomeBeforeHasMoved in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertFalse("hasBuild must be false", god.hasBuild);
            assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);
        }

    }

    @Test
    @DisplayName("hasBuildMoreThanOneCell")
    public void hasBuildMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(3, 3));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        worker.setCurrentCell(firstCell);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.SECOND_FLOOR);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertSame("worker's position must be firstCell", secondCell.getHeight(), Height.SECOND_FLOOR);
        }

    }

    @Test
    @DisplayName("hasBuildDomeMoreThanOneCell")
    public void hasBuildDomeMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(3, 3));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        worker.setCurrentCell(firstCell);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertSame("worker's position must be firstCell", secondCell.getHeight(), Height.THIRD_FLOOR);
        }

    }

    @Test
    @DisplayName("hasBuild not a dome")
    public void hasBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Chronus(board);
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
            System.err.println("Error e in method hasBuildNotDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Chronus(board);
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
            System.err.println("Error e in method hasBuildDomeTest in class ChronusTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class ChronusTest");
        }

    }

    @Test
    @DisplayName("has build on a dome")
    public void hasBuildOnADome() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Chronus(board);
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
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);
        }

    }

    @Test
    @DisplayName("hasWon = true")
    public void hasWonTrueTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
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
            System.err.println("Error e in method hasWonTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.THIRD_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.executeCommand(worker, command);

            assertFalse( "hasWon must be false", god.hasWon );
            assertEquals("Worker must be on the third floor", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonFalseTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class ChronusTest");
        }
    }

    @Test
    @DisplayName("resetGodVariable")
    public void resetAllGodVariable() {
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.RESET);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        god.hasMoved = true;
        god.hasBuild = true;

        try {
            god.executeCommand(worker, command);
            assertFalse("hasMoved isn't false", god.hasMoved);
            assertFalse("hasBuild isn't false", god.hasBuild);
        } catch (IllegalMoveException e) {
            fail("Exception in resetAllGodVariable in class ChronusTest");
        }
    }

    @Test
    @DisplayName("nullCommand")
    public void nullCommand() {
        Board board = new Board();
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, null);
            fail("nullCommand in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertNull(null);
        }
    }

    @Test
    @DisplayName("outOfSwitchCommandType")
    public void outOfSwitchCommandType() {
        Command command = new Command(new Pair(1, 1), CommandType.SET_GODS);
        Board board = new Board();
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, command);
            fail("outOfSwitchCommandType in class ChronusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertNull(null);
        }
    }

    // Exclusive tests
    @Test
    @DisplayName("hasWon = true 'cause there're five completed tower'")
    public void hasWonWithFiveTowersTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Chronus(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the starting cell
        Cell startingCell = board.getCell(new Pair(1, 0));
        startingCell.setHeight(Height.GROUND);
        startingCell.setWorker(worker);

        // Initialization of the ending cell
        Cell endingCell = board.getCell(new Pair(1, 1));
        endingCell.setHeight(Height.GROUND);
        endingCell.setWorker(null);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setIsCompleted();
        firstCell.setHeight(Height.DOME);
        firstCell.setWorker(null);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setIsCompleted();
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 2));
        thirdCell.setIsCompleted();
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(0, 3));
        fourthCell.setIsCompleted();
        fourthCell.setHeight(Height.DOME);
        fourthCell.setWorker(null);

        // Initialization of the fifth cell
        Cell fifthCell = board.getCell(new Pair(0, 4));
        fifthCell.setIsCompleted();
        fifthCell.setHeight(Height.DOME);
        fifthCell.setWorker(null);


        worker.setCurrentCell(startingCell);

        try{
            god.executeCommand(worker, command);

            assertTrue( "hasWon must be true", god.hasWon );
            assertTrue("The number of completed towers must be at list 5", board.countCompleteTower());

        } catch (IllegalMoveException e){
            System.err.println("Error e in method hasWonTrueTest in class ChronusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class ChronusTest");
        }
    }

}
