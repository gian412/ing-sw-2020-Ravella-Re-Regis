package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    @DisplayName("moveWorkerTestIfThePlayerMovesSuccesfully")
    public void moveWorker1Test(){

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(0,0).setWorker(worker);
        worker.setCurrentCell(board.getCell(0,0));

        try {
            board.moveWorker(worker, board.getCell(1, 1));
        }
        catch( IllegalMoveException e){
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell oldCell = new Cell(0,0 );
        oldCell.setWorker(null);

        Cell newCell = new Cell( 1, 1 );
        newCell.setWorker(worker);

        assertTrue("previousCell in worker is the old current cell and currentCell is the new cell",
                worker.getCurrentCell().equals(newCell) && worker.getPreviousCell().equals(oldCell));


    }

    @Test
    @DisplayName("moveWorkerTestIfThePlayerMovesInACellWHicHDoesn'tExist")
    public void moveWorker2Test(){

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(0,0).setWorker(worker);
        worker.setCurrentCell(board.getCell(0,0));
        Cell cell = new Cell(6,6);

        try {
            board.moveWorker(worker, cell);
            fail("test failed");
        }
        catch( IllegalMoveException e){
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            assertTrue("there is an error because the cell doesn't exist",
                    true);
        }
    }

    @Test
    @DisplayName("moveWorkerTestIfThePlayerMovesInACellWhichIsn'tAdjacent")
    public void moveWorker3Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(3, 3).setWorker(worker);
        worker.setCurrentCell(board.getCell(3, 3));
        Cell cell = new Cell(0, 2);

        try {
            board.moveWorker(worker, cell);
            fail("test failed");
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            assertTrue("there is an error because the cell doesn't exist",
                    true);
        }

    }

    @Test
    @DisplayName("forceWorkerTestIfaWorkerIsBoundToMoveToAnOtherCell")
    public void forceWorker1Test(){

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(0,0).setWorker(worker);
        worker.setCurrentCell(board.getCell(0,0));

        try {
            board.forceWorker(worker, board.getCell(4, 3));
        }
        catch( IllegalMoveException e){
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell oldCell = new Cell(0,0 );
        oldCell.setWorker(null);

        Cell newCell = new Cell( 4, 3 );
        newCell.setWorker(worker);

        assertTrue("previousCell in worker is the old current cell and currentCell is the new cell",
                worker.getCurrentCell().equals(newCell) && worker.getPreviousCell().equals(oldCell));
    }

    @Test
    @DisplayName("forceWorkerTestIfaWorkerIsBoundToMoveInACellWhichDoesn'tExist")
    public void forceWorker2Test(){

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(0,0).setWorker(worker);
        worker.setCurrentCell(board.getCell(0,0));

        Cell cell = new Cell(5,5);

        try {
            board.forceWorker(worker, cell);
            fail("test Failed");
        }
        catch( IllegalMoveException e){
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            assertTrue("the cell doesn't exist and there is an IllegalMoveException", true);
        }
    }

    @Test
    @DisplayName("addFirstWorkerTest")
    public void addFirstWorkerTest(){

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("player10", player);
        board.setTurnPlayer(player);

        try{
            board.addWorker(1 ,1 );
        }
        catch (IllegalAddException | IllegalCellException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell cell = new Cell(1,1);
        cell.setWorker(worker);

        worker.setCurrentCell(cell);


        assertTrue("now there is a worker in the cell 1,1 and below to player1",
                board.getCell(1,1).getWorker().WORKER_ID.equals(worker.WORKER_ID)  &&
                            player.getWorkers()[0].WORKER_ID.equals(worker.WORKER_ID) );


    }



    @Test
    @DisplayName("buildTestIfThePlayerBuildsADomeInALevel3Cell")
    public void build1Test(){

        Board board = new Board();
        Cell cell = new Cell(1,1);
        board.getCell(1,1).setHeight(Height.THIRD_FLOOR);


        try {
            board.build(cell, true);
        }
        catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        assertTrue("the player builds in cell 1,1 a dome in a third level tower and completed it",
                board.getCell(1, 1).isCompleted() && board.getCell(1,1).getHeight() == Height.DOME);
    }

    @Test
    @DisplayName("buildTestIfThePlayerBuildsADomeInANotLevel3Cell")
    public void build2Test(){

        Board board = new Board();
        Cell cell = new Cell(1,1);
        board.getCell(1,1).setHeight(Height.GROUND);


        try {
            board.build(cell, true);
        }
        catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");

        }

        assertTrue("the player builds in cell 1,1 a dome in a not level three cell so the tower isn't completed",
                !board.getCell(1, 1).isCompleted() && board.getCell(1,1).getHeight() == Height.DOME);
    }

    @Test
    @DisplayName("buildTestIfThePlayerBuilds")
    public void build3Test(){

        Board board = new Board();
        Cell cell = new Cell(1,1);
        board.getCell(1,1).setHeight(Height.GROUND);


        try {
            board.build(cell, false);
        }
        catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");

        }

        assertTrue("the player builds in cell 1,1 a new floor and the tower isn't completed",
                !board.getCell(1, 1).isCompleted() && board.getCell(1,1).getHeight() == Height.FIRST_FLOOR);
    }

    @Test
    @DisplayName("buildTestIfTheCellDoes'tExist")
    public void build4Test(){

        Board board = new Board();
        Cell cell = new Cell(8,8);
        board.getCell(1,1).setHeight(Height.GROUND);


        try {
            board.build(cell, false);
            fail("test failed");
        }
        catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            assertTrue("the cell doesn't exist", true);
        }
    }
}
