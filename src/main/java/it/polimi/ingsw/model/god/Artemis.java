package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Artemis extends God {


    public Artemis(Board board) {
        super(board);
    }

    // array cell composed by 3 cells, 2 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException {

        // first move
        super.move( worker, cells[0] );

        //Second move
        if( cells[1].equals( worker.getPreviousCell() ) ){
            throw new IllegalMoveException();
        } else{
            super.move( worker, cells[1] );
        }

        // build
        super.build(worker, cells[2], false);

    }
}
