package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Demeter extends God {

    // class constructor with the initialization of board using the super constructor
    public Demeter(Board board) {
        super(board, "DEMETER");
    }

    // array cell composed by 3 cells, 1 for the moves and 2 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            super.move( worker, cells[0] );
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // first build
            if( cells[1] != null ){
                super.build(worker, cells[1], false);
            } else{
                throw new NullPointerException();
            }

            // second build
            if( cells[2] != null ){
                if( cells[2].equals(cells[1]) ){
                    throw new IllegalMoveException();
                } else{
                    super.build(worker, cells[2], false);
                }
            }
        }
    }
}
