package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalAddException;
import it.polimi.ingsw.exceptions.IllegalCellException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.Chronus;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.model.god.Pan;
import it.polimi.ingsw.view.Observer;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    @DisplayName("moveWorkerTestIfThePlayerMovesSuccesfully")
    public void moveWorker1Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(new Pair(0,0)).setWorker(worker);
        worker.setCurrentCell(board.getCell(new Pair (0,0)));

        try {
            board.moveWorker(worker, new Pair(1, 1));
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell oldCell = new Cell(0, 0);
        oldCell.setWorker(null);

        Cell newCell = new Cell(1, 1);
        newCell.setWorker(worker);

        assertTrue("previousCell in worker is the old current cell and currentCell is the new cell",
                worker.getCurrentCell().equals(newCell) && worker.getPreviousCell().equals(oldCell));


    }

    @Test
    @DisplayName("moveWorkerTestIfThePlayerMovesInACellWHicHDoesn'tExist")
    public void moveWorker2Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(new Pair (0,0)).setWorker(worker);
        worker.setCurrentCell(board.getCell(new Pair (0,0)));
        Pair pair = new Pair(6, 6);

        try {
            board.moveWorker(worker, pair);
            fail("test failed");
        } catch (IllegalMoveException e) {
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

        board.getCell(new Pair (3,3)).setWorker(worker);
        worker.setCurrentCell(board.getCell(new Pair (3,3)));
        Pair pair = new Pair(0, 2);

        try {
            board.moveWorker(worker, pair);
            fail("test failed");
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            assertTrue("there is an error because the cell doesn't exist",
                    true);
        }

    }

    @Test
    @DisplayName("forceWorkerTestIfaWorkerIsBoundToMoveToAnOtherCell")
    public void forceWorker1Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(new Pair (0,0)).setWorker(worker);
        worker.setCurrentCell(board.getCell(new Pair (0,0)));

        try {
            board.forceWorker(worker, new Pair(4, 3));
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell oldCell = new Cell(0, 0);
        oldCell.setWorker(null);

        Cell newCell = new Cell(4, 3);
        newCell.setWorker(worker);

        assertTrue("previousCell in worker is the old current cell and currentCell is the new cell",
                worker.getCurrentCell().equals(newCell) && worker.getPreviousCell().equals(oldCell));
    }

    @Test
    @DisplayName("forceWorkerTestIfaWorkerIsBoundToMoveInACellWhichDoesn'tExist")
    public void forceWorker2Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("worker1", player);

        board.getCell(new Pair (0,0)).setWorker(worker);
        worker.setCurrentCell(board.getCell(new Pair (0,0)));

        Pair pair = new Pair(5, 5);

        try {
            board.forceWorker(worker, pair);
            fail("test Failed");
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method moveWorkerTest in class BoardTest " + e.toString());
            assertTrue("the cell doesn't exist and there is an IllegalMoveException", true);
        }
    }

    @Test
    @DisplayName("addFirstWorkerTest")
    public void addWorker1Test() {

        Board board = new Board();
        Player player = new Player("player1", 10);
        Worker worker = new Worker("player10", player);
        board.setTurnPlayer(player);

        try {
            board.addWorker(new Pair(1, 1));
        } catch (IllegalAddException | IllegalCellException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        Cell cell = new Cell(1, 1);
        cell.setWorker(worker);

        worker.setCurrentCell(cell);


        assertTrue("now there is a worker in the cell 1,1 and below to player1",
                board.getCell(new Pair (1,1)).getWorker().WORKER_ID.equals(worker.WORKER_ID) &&
                        player.getWorkers()[0].WORKER_ID.equals(worker.WORKER_ID));


    }

    @Test
    @DisplayName("addSecondWorkerTest")
    public void addWorker2Test(){

        Board board = new Board();
        Player player1 = new Player("player1", 10);
        board.setTurnPlayer(player1);

        try{
            board.addWorker(new Pair(0, 0));
            board.addWorker(new Pair(1, 1));
        }
        catch (IllegalAddException | IllegalCellException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }


        Worker worker1 = new Worker("player10", player1);
        Worker worker2 = new Worker("player11", player1);

        Cell cell1 = new Cell(0,0);
        cell1.setWorker(worker1);

        Cell cell2 = new Cell(1,1);
        cell2.setWorker(worker2);


        assertTrue("the player sets his two workers on the board",
                board.getCell(new Pair (0,0)).getWorker().WORKER_ID.equals(worker1.WORKER_ID)  &&
                        player1.getWorkers()[0].WORKER_ID.equals(worker1.WORKER_ID) &&
                        board.getCell(new Pair (1,1)).getWorker().WORKER_ID.equals(worker2.WORKER_ID)  &&
                        player1.getWorkers()[1].WORKER_ID.equals(worker2.WORKER_ID));
    }

    @Test
    @DisplayName("addThirdWorkerTest")
    public void addWorker3Test() {

        Board board = new Board();
        Player player1 = new Player("player1", 10);
        board.setTurnPlayer(player1);

        try {
            board.addWorker(new Pair(0, 0));
            board.addWorker(new Pair(1, 1));
            board.addWorker(new Pair(2, 2));
            fail("test failed");
        } catch (IllegalAddException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            assertTrue("you can't add a third worker", true);
        } catch (IllegalCellException e) {
            fail("test failed");
        }
    }

    @Test
    @DisplayName("addWorkerTestInnInvalidCell")
    public void addWorker4Test() {

        Board board = new Board();
        Player player1 = new Player("player1", 10);
        board.setTurnPlayer(player1);

        try {
            board.addWorker(new Pair(5, 5));
            fail("test failed");
        } catch (IllegalAddException e) {
            fail("test failed");
        } catch (IllegalCellException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            assertTrue("you can't add a worker in an invalid cell", true);

        }
    }

    @Test
    @DisplayName("buildTestIfThePlayerBuildsADomeInALevel3Cell")
    public void build1Test() {

        Board board = new Board();
        Pair pair = new Pair(1, 1);
        Cell cellWorker = new Cell(0, 0);
        board.getCell(new Pair (1,1)).setHeight(Height.THIRD_FLOOR);


        try {
            board.build(cellWorker, pair, true);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");
        }

        assertTrue("the player builds in cell 1,1 a dome in a third level tower and completed it",
                board.getCell(new Pair (1,1)).isCompleted() && board.getCell(new Pair (1,1)).getHeight() == Height.DOME);
    }

    @Test
    @DisplayName("buildTestIfThePlayerBuildsADomeInANotLevel3Cell")
    public void build2Test() {

        Board board = new Board();
        Pair pair = new Pair(1, 1);
        Cell cellWorker = new Cell(0, 0);


        try {
            board.build(cellWorker, pair, true);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");

        }

        assertTrue("the player builds in cell 1,1 a dome in a not level three cell so the tower isn't completed",
                !board.getCell(new Pair (1,1)).isCompleted() && board.getCell(new Pair (1,1)).getHeight() == Height.DOME);
    }

    @Test
    @DisplayName("buildTestIfThePlayerBuilds")
    public void build3Test() {

        Board board = new Board();
        Pair pair = new Pair(1, 1);
        Cell cellWorker = new Cell(0, 0);


        try {
            board.build(cellWorker, pair, false);
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            fail("test failed");

        }

        assertTrue("the player builds in cell 1,1 a new floor and the tower isn't completed",
                !board.getCell(new Pair (1,1)).isCompleted() && board.getCell(new Pair (1,1)).getHeight() == Height.FIRST_FLOOR);
    }

    @Test
    @DisplayName("buildTestIfTheCellDoes'tExist")
    public void build4Test() {

        Board board = new Board();
        Pair pair = new Pair(8, 8);
        Cell cellWorker = new Cell(0, 0);


        try {
            board.build(cellWorker,pair, false);
            fail("test failed");
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            assertTrue("the cell doesn't exist", true);
        }
    }

    @Test
    @DisplayName("buildTestIfTheCellIsn'tAdjacent")
    public void build5Test() {

        Board board = new Board();
        Pair pair = new Pair(3, 3);
        Cell cellWorker = new Cell(0, 0);


        try {
            board.build(cellWorker, pair, false);
            fail("test failed");
        } catch (IllegalMoveException e) {
            System.err.println("Error e in method addFirstWorkerTest in class BoardTest " + e.toString());
            assertTrue("the cell isn't adjacent", true);
        }
    }


    @Test
    @DisplayName("checkWinTestNormalWin")
    public void checkWin1Test() {

        Board board = new Board();

        Cell cell1 = new Cell(1,1);
        cell1.setHeight(Height.SECOND_FLOOR);
        Cell cell2 = new Cell(2,2);
        cell2.setHeight(Height.THIRD_FLOOR);

        Player player = new Player("player", 10);
        God god = new Apollo(board);
        player.setDivinity(god);
        Worker worker = new Worker("player1", player);

        worker.setCurrentCell(cell2);
        worker.setPreviousCell(cell1);

        boolean check = board.checkWin(worker);

        assertTrue("the worker has won", check);
    }

    @Test
    @DisplayName("checkWinTestIfTheGodIsPan")
    public void checkWin2Test() {

        Board board = new Board();

        Cell cell1 = new Cell(1,1);
        cell1.setHeight(Height.SECOND_FLOOR);
        Cell cell2 = new Cell(2,2);
        cell2.setHeight(Height.GROUND);

        Player player = new Player("player", 10);
        God god = new Pan(board);
        player.setDivinity(god);
        Worker worker = new Worker("player1", player);

        worker.setCurrentCell(cell2);
        worker.setPreviousCell(cell1);

        boolean check = board.checkWin(worker);

        assertTrue("the worker has won", check);
    }

    @Test
    @DisplayName("checkWinTestIfTheGodIsChronus")
    public void checkWin3Test() {

        Board board = new Board();

        Cell cell1 = board.getCell(new Pair (0,0));
        Cell cell2 = board.getCell(new Pair (1,1));

        Player player = new Player("player", 10);
        God god = new Chronus(board);
        player.setDivinity(god);
        Worker worker = new Worker("player1", player);

        worker.setCurrentCell(cell2);
        worker.setPreviousCell(cell1);

        board.getCell(new Pair (4,0)).setIsCompleted();
        board.getCell(new Pair (4,1)).setIsCompleted();
        board.getCell(new Pair (4,2)).setIsCompleted();
        board.getCell(new Pair (4,3)).setIsCompleted();
        board.getCell(new Pair (4,4)).setIsCompleted();

        boolean check = board.checkWin(worker);

        assertTrue("the worker has won", check);
    }

    @Test
    @DisplayName("countCompleteTowerTestTrue")
    public void countCompleteTower1Test() {

        Board board = new Board();

        board.getCell(new Pair (0,0)).setIsCompleted();
        board.getCell(new Pair (1,1)).setIsCompleted();
        board.getCell(new Pair (2,2)).setIsCompleted();
        board.getCell(new Pair (3,3)).setIsCompleted();
        board.getCell(new Pair (4,4)).setIsCompleted();


        assertTrue("there are 5 or more towers completed",
                board.countCompleteTower());
    }

    @Test
    @DisplayName("countCompleteTowerTestFalse")
    public void countCompleteTower2Test() {

        Board board = new Board();

        board.getCell(new Pair(0, 0)).setIsCompleted();
        board.getCell(new Pair(2, 2)).setIsCompleted();
        board.getCell(new Pair(3, 3)).setIsCompleted();
        board.getCell(new Pair(4, 4)).setIsCompleted();


        assertFalse("there are less than 5 towers completed",
                board.countCompleteTower());
    }

    @Test
    @DisplayName("toStringTest")
    public void toStringTest() {

        Board board = new Board();

        String print = board.toString();

        assertTrue("no error", true);
    }

    @Test
    @DisplayName("getCell1Test")
    public void getCell1Test() {

        Board board = new Board();

        try {
            Cell cell = new Cell(0,0);
            if(cell.equals(board.getCell(new Pair(0,0))))
                assertTrue("the two cell are equals", true);
            else
                fail("test failed");

        }
        catch(IndexOutOfBoundsException e){
            fail("test failed");
        }
    }

    @Test
    @DisplayName("getCell2Test")
    public void getCell2Test() {

        Board board = new Board();

        try{
            Cell cell = new Cell(0,0);
            if(cell.equals(board.getCell(new Pair(5,5))))
                fail("test failed");
            else
                fail("test failed");
        }

        catch(IndexOutOfBoundsException e){
            System.err.println("Error e in method getCell in class BoardTest " + e.toString());
            assertTrue("the cell doesn't exist", true);
        }
    }

    @Test
    @DisplayName("getTurnPlayerTest")
    public void getTurnPlayerTest() {

        Board board = new Board();
        board.setTurnPlayer(new Player("marco", 9 ));

        assertTrue("getTurnPlayer is ok", board.getTurnPlayer().equals(new Player("marco", 9)));
        }

    @Test
    @DisplayName("setChoosingGodsTest")
    public void setChoosingGodsTest() {

        Board board = new Board();

        board.setChoosingGods("message");

        assertTrue("setChoosingGods set a message in boardProxy", board.getProxy().getChoosingGods().equals("message"));
    }


    }





