package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ApolloTest {

    @Test
    @DisplayName("hadMove")
    public void hadMoveTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMoveTest in class ApolloTest: " + e.toString());
        }

        assertTrue("hadMove must be true", god.hadMove);
        assertSame("worker's previous position must be firstCell", worker.getPreviousCell(), firstCell);
        assertEquals("worker's position must be secondCell", worker.getCurrentCell(), secondCell);
    }

    @Test
    @DisplayName("hadMove and hadForced")
    public void hadMoveAndForcedTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player1 = new Player("Name1", 18);
        Player player2 = new Player("Name2", 18);
        player1.setDivinity(god);
        Worker worker1 = new Worker("Id1", player1);
        Worker worker2 = new Worker("Id2", player2);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker1);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker1.setCurrentCell(firstCell);
        worker2.setCurrentCell(secondCell);

        try {
            god.makeMove(worker1, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadMoveAndForcedTest in class ApolloTest: " + e.toString());
        }

        assertTrue("hadMove must be true", god.hadMove);
        assertEquals("worker1's previous position must be firstCell", worker1.getCurrentCell(), firstCell);
        assertEquals("worker1's position must be secondCell", worker1.getCurrentCell(), secondCell);
        assertEquals("worker2's previous position must be secondCell", worker2.getPreviousCell(), secondCell);
        assertEquals("worker2's position must be forced to be firstCell", worker2.getCurrentCell(), firstCell);
    }

    @Test
    @DisplayName("hadBuild not a dome")
    public void hadBuildNotDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildNotDomeTest in class ApolloTest: " + e.toString());
        }

        assertTrue("hadBuild must be true", god.hadBuild);
        assertSame("secondCell's Height must be one bigger than before", secondCell.getHeight(), Height.THIRD_FLOOR);
    }

    @Test
    @DisplayName("hadBuild a dome")
    public void hadBuildDomeTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.BUILD_DOME);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);
        god.hadMove = true;

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try {
            god.makeMove(worker, command);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method hadBuildDomeTest in class ApolloTest: " + e.toString());
        }

        assertTrue("hadBuild must be true", god.hadBuild);
        assertSame("secondCell's Height must be equals to DOME", secondCell.getHeight(), Height.DOME);
    }

    @Test
    @DisplayName("hadWin = true")
    public void hadWinYesTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println("Error e in method hadWinYesTest in class ApolloTest: " + e.toString());
        }

        assertTrue( "hadWin must be true", god.hadWin );
        assertSame("worker's position's Height must be THIRD_FLOOR", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

    }

    @Test
    @DisplayName("hadWin = false")
    public void hadWinNoTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        worker.setCurrentCell(firstCell);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println("Error e in method hadWinNoTest in class ApolloTest: " + e.toString());
        }

        assertFalse( "hadWin must be false", god.hadWin );
        assertNotSame("Worker can't be on the third floor", worker.getCurrentCell().getHeight(), Height.THIRD_FLOOR);

    }
}