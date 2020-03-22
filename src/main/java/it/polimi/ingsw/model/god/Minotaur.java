package it.polimi.ingsw.model.god;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Minotaur extends God {

    // class constructor with the initialization of board using the super constructor
    public Minotaur(Board board) {
        super(board);
    }

    // method that return the direction of the movement of the worker
    private int[] getDirection(Cell firstCell, Cell secondCell){

        int[] direction = new int[2];

        direction[0] = secondCell.X - firstCell.X;
        direction[1] = secondCell.Y - firstCell.Y;

        return direction;
    }

    // array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            if( cells[0].getWorker() == null ){
                super.move(worker, cells[0]);
            } else{
                int[] direction = getDirection( worker.getCurrentCell(), cells[0] );
                Cell nextCell =  board.getCell( cells[0].X + direction[0], cells[0].Y + direction[1] );
                if( nextCell.getWorker() != null && nextCell.getHeight() != Height.DOME){
                    Worker otherWorker = cells[0].getWorker();
                    super.move(worker, cells[0]);
                    board.forceWorker(otherWorker, nextCell);
                } else{
                    throw new IllegalMoveException();
                }
            }
        } else{
            throw new NullPointerException();
        }

        // build
        if( cells[1] != null ){
            super.build(worker, cells[1], false );
        } else{
            throw new NullPointerException();
        }

    }
}
