package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Apollo extends God{

    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board);
    }

    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            if( cell.getHeight() == Height.DOME ){
                throw new IllegalMoveException();
            }else{
                board.moveWorker(worker, cell);
            }
        }else{
            Worker otherWorker = cell.getWorker();
            Cell actualCell = worker.getCurrentCell();
            board.moveWorker(worker, cell);
            board.forceWorker(otherWorker, actualCell);
        }

    }

    // array cell composed by 2 cells, 1 for the move and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException {

        // move
        move(worker, cells[0]);

        // build
        super.build(worker, cells[1], false);

    }

}