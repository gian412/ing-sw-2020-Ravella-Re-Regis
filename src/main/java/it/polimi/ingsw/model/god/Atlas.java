package it.polimi.ingsw.model.god;

import controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Atlas extends God {

    // class constructor with the initialization of board using the super constructor
    public Atlas(Board board) {
        super(board, "ATLAS");
    }

    /*// array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw  new NullPointerException();
        }

        if( !hadWin ){
            // build
            if( cells[1] != null ){
                super.build(worker, cells[1], isDome);
            } else{
                throw new NullPointerException();
            }
        }
    }*/

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

    }
}
