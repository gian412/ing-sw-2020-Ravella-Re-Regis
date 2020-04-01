package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    @DisplayName("moveWorkerTest")
    public void moveWorkerTest(){

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
        }

        Cell oldCell = new Cell(0,0 );
        oldCell.setWorker(null);

        Cell newCell = new Cell( 1, 1 );
        newCell.setWorker(worker);

        assertTrue("previousCell in worker is the old current cell and currentCell is the new cell",
                worker.getCurrentCell().equals(newCell) && worker.getPreviousCell().equals(oldCell));


    }



}
