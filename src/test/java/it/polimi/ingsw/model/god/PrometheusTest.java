package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class PrometheusTest {

    // Common tests
    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
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
            System.err.println("Error e in method hasMovedTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasMovedTest in class PrometheusTest");
        }

    }

    @Test
    @DisplayName("canMoveUp=false")
    public void moveWithNotCanMoveUp(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
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
            fail("moveWithNotCanMoveUp in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            fail("moveUpMoreThan1Floor in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            fail("moveOnADome in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            fail("moveOnADome in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            fail("moveUpWhenCannotMoveUp in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            fail("hasMovedSecondTime in class PrometheusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("hasBuildMoreThanOneCell")
    public void hasBuildMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Prometheus(board);
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
            fail("moveOnADome in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

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
            fail("moveOnADome in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            System.err.println("Error e in method hasBuildNotDomeTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class PrometheusTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Prometheus(board);
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
            System.err.println("Error e in method hasBuildDomeTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class PrometheusTest");
        }

    }

    @Test
    @DisplayName("has build on a dome")
    public void hasBuildOnADome() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Prometheus(board);
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
            fail("moveOnADome in class PrometheusTest didn't throw an exception");
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
        God god = new Prometheus(board);
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
            System.err.println("Error e in method hasWonTrueTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class PrometheusTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
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
            System.err.println("Error e in method hasWonFalseTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class PrometheusTest");
        }
    }

    @Test
    @DisplayName("nullCommand")
    public void nullCommand() {
        Board board = new Board();
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, null);
            fail("nullCommand in class PrometheusTest didn't throw an exception");
        } catch (NullPointerException | IllegalMoveException e) {
            assertNull(null);
        }
    }

    @Test
    @DisplayName("outOfSwitchCommandType")
    public void outOfSwitchCommandType() {
        Command command = new Command(new Pair(1, 1), CommandType.SET_GODS);
        Board board = new Board();
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, command);
            fail("outOfSwitchCommandType in class PrometheusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertNull(null);
        }
    }


    // Exclusive tests
    @Test
    @DisplayName("hasMovedAfterFirstBuild")
    public void hasMovedAfterFirstBuild(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
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
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);
            god.executeCommand(worker, secondCommand);

            assertTrue("hasBuildBefore mustBe true", ((Prometheus)god).hasBuildBefore);
            assertTrue("hasMoved must be true", god.hasMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);
            assertEquals("secondCell's height must be first floor", Height.FIRST_FLOOR, secondCell.getHeight());

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasMovedTest in class PrometheusTest");
        }

    }

    @Test
    @DisplayName("hasMovedWithCanNotMoveAfterFirstBuild")
    public void hasMovedWithCanNotMoveAfterFirstBuild(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        worker.setCanMoveUp(false);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);
            god.executeCommand(worker, secondCommand);

            assertTrue("hasBuildBefore mustBe true", ((Prometheus)god).hasBuildBefore);
            assertTrue("hasMoved must be true", god.hasMoved);
            assertEquals("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
            assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);
            assertEquals("secondCell's height must be first floor", Height.FIRST_FLOOR, secondCell.getHeight());

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class PrometheusTest: " + e.toString());
            fail("Exception in hasMovedTest in class PrometheusTest");
        }

    }

    @Test
    @DisplayName("hasMovedUpWithCanNotMoveAfterFirstBuild")
    public void hasMovedUpWithCanNotMoveAfterFirstBuild(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        worker.setCanMoveUp(false);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 1));
        secondCell.setHeight(Height.FIRST_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {

            god.executeCommand(worker, firstCommand);
            god.executeCommand(worker, secondCommand);

            fail("Exception not throwed in hasMovedTest in class PrometheusTest");

        } catch (IllegalMoveException e) {
            assertEquals("worker's position must be firstCell", worker.getCurrentCell(), firstCell);
            assertEquals("firstCell worker must be worker", worker, firstCell.getWorker());
        }

    }

    @Test
    @DisplayName("hasBuildSecond not a dome")
    public void hasBuildSecondNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.BUILD);
        Prometheus god = new Prometheus(board);
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
        secondCell.setHeight(Height.GROUND);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);

            assertTrue("hasBuildBefore must be true", god.hasBuildBefore);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.FIRST_FLOOR);

            god.hasMoved = true;

            try {
                god.executeCommand(worker, secondCommand);

                assertTrue("hasBuild must be true", god.hasBuild);
                assertEquals("thirdCell's Height must be one bigger than before", secondCell.getHeight(), Height.SECOND_FLOOR);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hasBuildSecondTest in class PrometheusTest : " + e1.toString());
                fail("Exception in hasBuildSecondNotDomeTest in class PrometheusTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hasBuildSecondTest in class PrometheusTest :" + e2.toString());
            fail("Exception in hasBuildSecondNotDomeTest in class PrometheusTest");
        }






    }

    @Test
    @DisplayName("hasBuildSecond a dome")
    public void hasBuildSecondDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command firstCommand = new Command(new Pair(0, 0), CommandType.BUILD_DOME);
        Command secondCommand = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        Prometheus god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 0));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(1, 1));
        thirdCell.setHeight(Height.THIRD_FLOOR);
        thirdCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.executeCommand(worker, firstCommand);

            assertTrue("hasBuildBefore must be true", god.hasBuildBefore);
            assertEquals("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.DOME);

            god.hasMoved = true;

            try {
                god.executeCommand(worker, secondCommand);

                assertTrue("hasBuild must be true", god.hasBuild);
                assertEquals("thirdCell's Height must be one bigger than before", secondCell.getHeight(), Height.DOME);

            } catch (IllegalMoveException e1) {
                System.err.println("Error e1 in method hasBuildSecondTest in class PrometheusTest : " + e1.toString());
                fail("Exception in hasBuildSecondDomeTest in class PrometheusTest");
            }
        } catch (IllegalMoveException e2) {
            System.err.println("Error e2 in method hasBuildSecondTest in class PrometheusTest :" + e2.toString());
            fail("Exception in hasBuildSecondDomeTest in class PrometheusTest");
        }






    }

    @Test
    @DisplayName("hasBuildSecondMoreThanOneCell")
    public void hasBuildSecondDomeMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Prometheus(board);
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
        secondCell.setHeight(Height.THIRD_FLOOR);

        try {
            god.executeCommand(worker, command);
            fail("hasBuildFirstMoreThanOneCell in class PrometheusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertSame("secondCell's Height must be same as before", secondCell.getHeight(), Height.THIRD_FLOOR);
        }

    }

    @Test
    @DisplayName("hasBuildThirdTime")
    public void hasBuildThirdTime(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        Prometheus god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hasMoved = true;
        god.hasBuild = true;


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
            fail("hasBuildThirdTime in class PrometheusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertSame("secondCell's Height must be same as before", secondCell.getHeight(), Height.SECOND_FLOOR);
        }

    }

    @Test
    @DisplayName("hasBuildFirstMoreThanOneCell")
    public void hasBuildFirstMoreThanOneCell(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD);
        God god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

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
            fail("hasBuildFirstMoreThanOneCell in class PrometheusTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertSame("secondCell's Height must be same as before", secondCell.getHeight(), Height.SECOND_FLOOR);
        }

    }

    @Test
    @DisplayName("resetPrometheusVariable")
    public void resetPrometheusVariable() {
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.RESET);
        Prometheus god = new Prometheus(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        god.hasMoved = true;
        god.hasBuild = true;
        god.hasBuildBefore = true;

        try {
            god.executeCommand(worker, command);
            assertFalse("hasMoved isn't false", god.hasMoved);
            assertFalse("hasBuild isn't false", god.hasBuild);
            assertFalse("hasBuildSecond isn't false", god.hasBuildBefore);
        } catch (IllegalMoveException e) {
            fail("Exception in resetAllGodVariable in class DemeterTest");
        }
    }

    @Test
    @DisplayName("cannotMove")
    public void cannotMove(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 0), CommandType.CHECK_WORKERS);
        God god = new Prometheus(board);
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
        secondCell.setHeight(Height.FIRST_FLOOR);
        secondCell.setWorker(otherWorker);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(1, 1));
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.DOME);
        fourthCell.setWorker(null);

        worker.setCurrentCell(firstCell);
        otherWorker.setCurrentCell(secondCell);

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

    @Test
    @DisplayName("cannotMoveAfterFirstBuild")
    public void cannotMoveAfterFirstBuild(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 0), CommandType.BUILD);
        God god = new Prometheus(board);
        Player player1 = new Player("Name1", 18);
        board.setTurnPlayer(player1);
        player1.setDivinity(god);
        Worker worker = new Worker("Name10", player1);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(1, 1));
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.SECOND_FLOOR);
        fourthCell.setWorker(null);

        worker.setCurrentCell(firstCell);

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

    @Test
    @DisplayName("cannotMoveAfterFirstBuildDome")
    public void cannotMoveAfterFirstBuildDome(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 0), CommandType.BUILD_DOME);
        God god = new Prometheus(board);
        Player player1 = new Player("Name1", 18);
        board.setTurnPlayer(player1);
        player1.setDivinity(god);
        Worker worker = new Worker("Name10", player1);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.GROUND);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setHeight(Height.DOME);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(1, 1));
        thirdCell.setHeight(Height.DOME);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.THIRD_FLOOR);
        fourthCell.setWorker(null);

        worker.setCurrentCell(firstCell);

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
    
}
