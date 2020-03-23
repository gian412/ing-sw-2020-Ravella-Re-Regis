package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Artemis extends God {

    // class constructor with the initialization of board using the super constructor
    public Artemis(Board board) {
        super(board, "ARTEMIS");
    }

    // array cell composed by 3 cells, 2 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // first move
        if( worker != null && cells[0] != null ){
            super.move( worker, cells[0] );
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            //Second move
            if( cells[1] != null ){
                if( cells[1].equals( worker.getPreviousCell() ) ){
                    throw new IllegalMoveException();
                } else{
                    super.move( worker, cells[1] );
                }
            } else{
                throw new NullPointerException();
            }

            if( !hadWin ){
                // build
                if( cells[2] != null ){
                    super.build(worker, cells[2], false);
                } else{
                    throw new NullPointerException();
                }
            }
        }
    }
}
