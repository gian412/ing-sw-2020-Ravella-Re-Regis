package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class HephaestusTest {

    // Common tests
    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            System.err.println("Error e in method hasMovedTest in class HephaestusTest: " + e.toString());
            fail("Exception in hasMovedTest in class HephaestusTest");
        }

    }

    @Test
    @DisplayName("canMoveUp=false")
    public void moveWithNotCanMoveUp(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveWithNotCanMoveUp in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveUpMoreThan1Floor in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveOnADome in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(3, 3));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, command);
            fail("moveOnADome in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveUpWhenCannotMoveUp in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("hasMovedSecondTime in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("hasBuildBeforeHasMoved in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("hasBuildDomeBeforeHasMoved in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveOnADome in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveOnADome in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            System.err.println("Error e in method hasBuildNotDomeTest in class HephaestusTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            System.err.println("Error e in method hasBuildDomeTest in class HephaestusTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class HephaestusTest");
        }

    }

    @Test
    @DisplayName("has build on a dome")
    public void hasBuildOnADome() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            fail("moveOnADome in class HephaestusTest didn't throw an exception");
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
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            System.err.println("Error e in method hasWonTrueTest in class HephaestusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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
            System.err.println("Error e in method hasWonFalseTest in class HephaestusTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("nullCommand")
    public void nullCommand() {
        Board board = new Board();
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, null);
            fail("nullCommand in class HephaestusTest didn't throw an exception");
        } catch (NullPointerException | IllegalMoveException e) {
            assertNull(null);
        }
    }

    @Test
    @DisplayName("outOfSwitchCommandType")
    public void outOfSwitchCommandType() {
        Command command = new Command(new Pair(1, 1), CommandType.SET_GODS);
        Board board = new Board();
        God god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, command);
            fail("outOfSwitchCommandType in class HephaestusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertNull(null);
        }
    }

    @Test
    @DisplayName("cannotMove")
    public void cannotMove(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 0), CommandType.CHECK_WORKERS);
        God god = new Hephaestus(board);
        Player player1 = new Player("Name1", 18);
        Player player2 = new Player("Name2", 18);
        board.setTurnPlayer(player1);
        player1.setDivinity(god);
        Worker worker = new Worker("Name10", player1);
        Worker otherWorker = new Worker("Name20", player2);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(1, 1));
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.FIRST_FLOOR);
        fourthCell.setWorker(otherWorker);

        worker.setCurrentCell(firstCell);
        otherWorker.setCurrentCell(fourthCell);

        try {
            god.executeCommand(worker, command);

            assertNull("worker.previousCell must be null", worker.getPreviousCell());
            assertNull("worker.currentCell must be null", worker.getCurrentCell());
            assertNull("firstCell.worker must be null", firstCell.getWorker());

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class ApolloTest: " + e.toString());
            fail("Exception in hasMovedTest in class ApolloTest");
        }

    }
    

    // Exclusive tests
    @Test
    @DisplayName("hasBuildSecond not a dome")
    public void hasBuildSecondNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Hephaestus god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
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

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);

            assertTrue("hasBuild must be true", god.hasBuild);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.FIRST_FLOOR);

            try {
                god.executeCommand(worker, secondCommand);

                assertTrue("hasBuildSecond must be true", god.hasBuildSecond);
                assertEquals("thirdCell's Height must be one bigger than before", secondCell.getHeight(), Height.SECOND_FLOOR);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hasBuildSecondTest in class HephaestusTest : " + e1.toString());
                fail("Exception in hasBuildSecondNotDomeTest in class HephaestusTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hasBuildSecondTest in class HephaestusTest :" + e2.toString());
            fail("Exception in hasBuildSecondNotDomeTest in class HephaestusTest");
        }
    }

    @Test
    @DisplayName("hasBuildSecond dome in a cell more far than 1")
    public void hasBuildSecondDomeMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        //Command firstCommand = new Command(new Pair(1, 0), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(0, 2), CommandType.BUILD);
        Hephaestus god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;
        god.hasBuild = true;

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);



        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 0));
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 2));
        thirdCell.setHeight(Height.FIRST_FLOOR);
        thirdCell.setWorker(null);

        god.previousCell = thirdCell;
        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, secondCommand);
            fail("hasBuildSecondMoreThanOneCell in class DemeterTest didn't throw an exception");
        } catch (IllegalMoveException e1) {
            assertEquals("thirdCell's Height must be same as before", thirdCell.getHeight(), Height.FIRST_FLOOR);
            assertFalse("hasBuildSecond must be false", god.hasBuildSecond);
        }

    }

    @Test
    @DisplayName("resetHephaestusVariable")
    public void resetHephaestusVariable() {
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.RESET);
        Hephaestus god = new Hephaestus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        god.hasMoved = true;
        god.hasBuild = true;
        god.hasBuildSecond = true;
        god.previousCell = new Cell(1, 1);

        try {
            god.executeCommand(worker, command);
            assertFalse("hasMoved isn't false", god.hasMoved);
            assertFalse("hasBuild isn't false", god.hasBuild);
            assertFalse("hasBuildSecond isn't false", god.hasBuildSecond);
            assertNull("hasBuildSecond isn't null", god.previousCell);
        } catch (IllegalMoveException e) {
            fail("Exception in resetAllGodVariable in class DemeterTest");
        }
    }

}
