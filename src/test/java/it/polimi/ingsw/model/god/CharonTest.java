package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class CharonTest {

    // Common tests
    @Test
    @DisplayName("hasMoved")
    public void hasMovedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Charon(board);
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
            System.err.println("Error e in method hasMovedTest in class CharonTest: " + e.toString());
            fail("Exception in hasMovedTest in class CharonTest");
        }

    }

    @Test
    @DisplayName("canMoveUp=false")
    public void moveWithNotCanMoveUp(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Charon(board);
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
            fail("moveWithNotCanMoveUp in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveUpMoreThan1Floor in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveOnADome in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveOnADome in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveUpWhenCannotMoveUp in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("hasMovedSecondTime in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("hasBuildBeforeHasMoved in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("hasBuildDomeBeforeHasMoved in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveOnADome in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            fail("moveOnADome in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            System.err.println("Error e in method hasBuildNotDomeTest in class CharonTest: " + e.toString());
            fail("Exception in hasBuildNotDomeTest in class CharonTest");
        }
    }

    @Test
    @DisplayName("hasBuild a dome")
    public void hasBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Charon(board);
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
            System.err.println("Error e in method hasBuildDomeTest in class CharonTest: " + e.toString());
            fail("Exception in hasBuildDomeTest in class CharonTest");
        }

    }

    @Test
    @DisplayName("has build on a dome")
    public void hasBuildOnADome() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.BUILD_DOME);
        God god = new Charon(board);
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
            fail("moveOnADome in class CharonTest didn't throw an exception");
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
        God god = new Charon(board);
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
            System.err.println("Error e in method hasWonTrueTest in class CharonTest: " + e.toString());
            fail("Exception in hasWonTrueTest in class CharonTest");
        }
    }

    @Test
    @DisplayName("hasWon = false")
    public void hasWonFalseTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.MOVE);
        God god = new Charon(board);
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
            System.err.println("Error e in method hasWonFalseTest in class CharonTest: " + e.toString());
            fail("Exception in hasWonFalseTest in class CharonTest");
        }
    }

    @Test
    @DisplayName("resetGodVariable")
    public void resetAllGodVariable() {
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.RESET);
        God god = new Charon(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        god.hasMoved = true;
        god.hasBuild = true;

        try {
            god.executeCommand(worker, command);
            assertFalse("hasMoved isn't false", god.hasMoved);
            assertFalse("hasBuild isn't false", god.hasBuild);
        } catch (IllegalMoveException e) {
            fail("Exception in resetAllGodVariable in class CharonTest");
        }
    }

    @Test
    @DisplayName("nullCommand")
    public void nullCommand() {
        Board board = new Board();
        God god = new Charon(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, null);
            fail("nullCommand in class CharonTest didn't throw an exception");
        } catch (NullPointerException | IllegalMoveException e) {
            assertNull(null);
        }
    }

    @Test
    @DisplayName("outOfSwitchCommandType")
    public void outOfSwitchCommandType() {
        Command command = new Command(new Pair(1, 1), CommandType.SET_GODS);
        Board board = new Board();
        God god = new Charon(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        try {
            god.executeCommand(worker, command);
            fail("outOfSwitchCommandType in class CharonTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertNull(null);
        }
    }

    // Exclusive tests
    @Test
    @DisplayName("Force a worker to move")
    public void forceWorkerToMove() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.FORCE);
        Charon thisGod = new Charon(board);
        God otherGod = new Zeus(board);
        Player thisPlayer = new Player("thisName", 18);
        Player otherPlayer = new Player("otherName", 18);
        board.setTurnPlayer(thisPlayer);
        thisPlayer.setDivinity(thisGod);
        otherPlayer.setDivinity(otherGod);

        Worker thisWorker = new Worker("Id", thisPlayer);
        Worker otherWorker = new Worker("otherId", otherPlayer);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(1, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(otherWorker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 2));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(thisWorker);

        // Initialization of the third cell
        Cell thirdCell = board.getCell((new Pair(1, 3)));
        thirdCell.setHeight(Height.SECOND_FLOOR);
        thirdCell.setWorker(null);

        thisWorker.setCurrentCell(secondCell);
        otherWorker.setCurrentCell(firstCell);

        try {
            thisGod.executeCommand(thisWorker, command);

            assertTrue("hasForced must be true", ( thisGod).hasForced);
            assertEquals("otherWorker's position must be thirdCell", otherWorker.getCurrentCell(), thirdCell);

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method forceWorkerToMove in class CharonTest: " + e.toString());
            fail("Exception in forceWorkerToMove in class CharonTest");
        }

    }

    @Test
    @DisplayName("forceWorkerToMoveInANotEmptyCell")
    public void forceWorkerToMoveInANotEmptyCell() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.FORCE);
        Charon thisGod = new Charon(board);
        God otherGod = new Zeus(board);
        God thirdGod = new Hestia(board);
        Player thisPlayer = new Player("thisName", 18);
        Player otherPlayer = new Player("otherName", 18);
        Player thirdPlayer = new Player("thirdPlayer", 18);
        board.setTurnPlayer(thisPlayer);
        thisPlayer.setDivinity(thisGod);
        otherPlayer.setDivinity(otherGod);
        thirdPlayer.setDivinity(thirdGod);

        Worker thisWorker = new Worker("Id", thisPlayer);
        Worker otherWorker = new Worker("otherId", otherPlayer);
        Worker thirdWorker = new Worker("thirdId", thirdPlayer);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(1, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(otherWorker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 2));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(thisWorker);

        // Initialization of the third cell
        Cell thirdCell = board.getCell((new Pair(1, 3)));
        thirdCell.setHeight(Height.SECOND_FLOOR);
        thirdCell.setWorker(thirdWorker);

        thisWorker.setCurrentCell(secondCell);
        otherWorker.setCurrentCell(firstCell);
        thirdWorker.setCurrentCell(thirdCell);

        try {
            thisGod.executeCommand(thisWorker, command);
            fail("forceWorkerToMoveInANotEmptyCell in class CharonTest didn't throw an exception");
        } catch (IllegalMoveException e) {
            assertEquals("otherWorker's position must be firstCell", otherWorker.getCurrentCell(), firstCell);
            assertEquals("thirdWorker's position must be thirdCell", thirdWorker.getCurrentCell(), thirdCell);
        }

    }

    @Test
    @DisplayName("Force a worker to move out of board")
    public void forceWorkerToMoveOutOfBoard() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 1), CommandType.FORCE);
        Charon thisGod = new Charon(board);
        God otherGod = new Zeus(board);
        Player thisPlayer = new Player("thisName", 18);
        Player otherPlayer = new Player("otherName", 18);
        board.setTurnPlayer(thisPlayer);
        thisPlayer.setDivinity(thisGod);
        otherPlayer.setDivinity(otherGod);

        Worker thisWorker = new Worker("Id", thisPlayer);
        Worker otherWorker = new Worker("otherId", otherPlayer);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);
        firstCell.setWorker(otherWorker);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 0));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(thisWorker);

        thisWorker.setCurrentCell(secondCell);
        otherWorker.setCurrentCell(firstCell);

        try {
            thisGod.executeCommand(thisWorker, command);
            fail("Exception in forceWorkerToMove in class CharonTest");
        } catch (IllegalMoveException e) {
            assertEquals("otherWorker's position must be firstCell", otherWorker.getCurrentCell(), firstCell);
        }

    }

    @Test
    @DisplayName("Force a worker to move twice")
    public void forceWorkerToMoveTwice() {

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.FORCE);
        Charon thisGod = new Charon(board);
        Player thisPlayer = new Player("thisName", 18);
        board.setTurnPlayer(thisPlayer);
        thisPlayer.setDivinity(thisGod);
        thisGod.hasForced = true;

        Worker thisWorker = new Worker("Id", thisPlayer);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(1, 1));
        firstCell.setHeight(Height.FIRST_FLOOR);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(1, 2));
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(thisWorker);

        // Initialization of the third cell
        Cell thirdCell = board.getCell((new Pair(1, 3)));
        thirdCell.setHeight(Height.SECOND_FLOOR);
        thirdCell.setWorker(null);

        thisWorker.setCurrentCell(secondCell);

        try {
            thisGod.executeCommand(thisWorker, command);
            fail("Exception in forceWorkerToMoveTwice in class CharonTest");
        } catch (IllegalMoveException e) {
            assertTrue("otherWorker's position must be firstCell", thisGod.hasForced);
        }

    }

    @Test
    @DisplayName("resetCharonVariable")
    public void resetCharonVariable() {
        Board board = new Board();
        Command command = new Command(new Pair(1, 1), CommandType.RESET);
        Charon god = new Charon(board);
        Player player = new Player("Name", 18);
        board.setTurnPlayer(player);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        god.hasMoved = true;
        god.hasForced = true;
        god.hasBuild = true;

        try {
            god.executeCommand(worker, command);
            assertFalse("hasMoved isn't false", god.hasMoved);
            assertFalse("hasBuild isn't false", god.hasBuild);
            assertFalse("hasForced isn't false", god.hasForced);
        } catch (IllegalMoveException e) {
            fail("Exception in resetAllGodVariable in class ArtemisTest");
        }
    }

    @Test
    @DisplayName("cannotMove")
    public void cannotMove(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 0), CommandType.CHECK_WORKERS);
        God god = new Charon(board);
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

    @Test
    @DisplayName("cannotMoveEvenForcing")
    public void cannotMoveEvenForcing(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 0), CommandType.CHECK_WORKERS);
        God god = new Charon(board);
        Player player1 = new Player("Name1", 18);
        Player player2 = new Player("Name2", 18);
        board.setTurnPlayer(player1);
        player1.setDivinity(god);
        Worker worker = new Worker("Name10", player1);
        Worker otherWorker = new Worker("Name20", player2);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.DOME);
        firstCell.setWorker(null);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 2));
        thirdCell.setHeight(Height.SECOND_FLOOR);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.DOME);
        fourthCell.setWorker(null);

        // Initialization of the fifth cell
        Cell fifthCell = board.getCell(new Pair(1, 1));
        fifthCell.setHeight(Height.GROUND);
        fifthCell.setWorker(worker);

        // Initialization of the sixth cell
        Cell sixthCell = board.getCell(new Pair(1, 2));
        sixthCell.setHeight(Height.FIRST_FLOOR);
        sixthCell.setWorker(otherWorker);

        // Initialization of the seventh cell
        Cell seventhCell = board.getCell(new Pair(2, 0));
        seventhCell.setHeight(Height.SECOND_FLOOR);
        seventhCell.setWorker(null);

        // Initialization of the eighth cell
        Cell eighthCell = board.getCell(new Pair(2, 1));
        eighthCell.setHeight(Height.THIRD_FLOOR);
        eighthCell.setWorker(null);

        // Initialization of the ninth cell
        Cell ninthCell = board.getCell(new Pair(2, 2));
        ninthCell.setHeight(Height.DOME);
        ninthCell.setWorker(null);

        worker.setCurrentCell(fifthCell);
        otherWorker.setCurrentCell(sixthCell);

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
    @DisplayName("canMoveForcing")
    public void canMoveForcing(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(new Pair(0, 0), CommandType.CHECK_WORKERS);
        God god = new Charon(board);
        Player player1 = new Player("Name1", 18);
        Player player2 = new Player("Name2", 18);
        board.setTurnPlayer(player1);
        player1.setDivinity(god);
        Worker worker = new Worker("Name10", player1);
        Worker otherWorker = new Worker("Name20", player2);

        // Initialization of the first cell
        Cell firstCell = board.getCell(new Pair(0, 0));
        firstCell.setHeight(Height.DOME);
        firstCell.setWorker(null);

        // Initialization of the second cell
        Cell secondCell = board.getCell(new Pair(0, 1));
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        // Initialization of the third cell
        Cell thirdCell = board.getCell(new Pair(0, 2));
        thirdCell.setHeight(Height.SECOND_FLOOR);
        thirdCell.setWorker(null);

        // Initialization of the fourth cell
        Cell fourthCell = board.getCell(new Pair(1, 0));
        fourthCell.setHeight(Height.GROUND);
        fourthCell.setWorker(null);

        // Initialization of the fifth cell
        Cell fifthCell = board.getCell(new Pair(1, 1));
        fifthCell.setHeight(Height.GROUND);
        fifthCell.setWorker(worker);

        // Initialization of the sixth cell
        Cell sixthCell = board.getCell(new Pair(1, 2));
        sixthCell.setHeight(Height.FIRST_FLOOR);
        sixthCell.setWorker(otherWorker);

        // Initialization of the seventh cell
        Cell seventhCell = board.getCell(new Pair(2, 0));
        seventhCell.setHeight(Height.SECOND_FLOOR);
        seventhCell.setWorker(null);

        // Initialization of the eighth cell
        Cell eighthCell = board.getCell(new Pair(2, 1));
        eighthCell.setHeight(Height.THIRD_FLOOR);
        eighthCell.setWorker(null);

        // Initialization of the ninth cell
        Cell ninthCell = board.getCell(new Pair(2, 2));
        ninthCell.setHeight(Height.DOME);
        ninthCell.setWorker(null);

        worker.setCurrentCell(fifthCell);
        otherWorker.setCurrentCell(sixthCell);

        try {
            god.executeCommand(worker, command);

            assertEquals("worker.currentCell must be null", fifthCell, worker.getCurrentCell());
            assertEquals("firstCell.worker must be null", worker, fifthCell.getWorker());

        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hasMovedTest in class ApolloTest: " + e.toString());
            fail("Exception in hasMovedTest in class ApolloTest");
        }

    }

}
