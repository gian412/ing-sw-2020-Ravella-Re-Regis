package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;


public class Pan extends  God {

    // class constructor with the initialization of board using the super constructor
    public Pan(Board board) {
        super(board);
    }

    // array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        // else
        if(cells[1] != null){
            super.build(worker, cells[1], false);
        } else{
            throw new NullPointerException();
        }

    }
}
