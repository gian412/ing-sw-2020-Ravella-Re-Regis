package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Prometheus extends God {

    public final String NAME = "PROMETHEUS";

    // class constructor with the initialization of board using the super constructor
    public Prometheus(Board board) {
        super(board);
    }

    // array cell composed by 2 cells, 1 for the moves and 2 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // first build
        if( worker != null && cells[1] != null  ){
            if( cells[0] != null ){
                if( worker.getCurrentCell().getHeight() == cells[1].getHeight() ){
                    super.build(worker, cells[0], false );
                } else{
                    throw new IllegalMoveException();
                }
            }
        } else{
            throw new NullPointerException();
        }

        // move
        if( cells[1] != null ){
            super.move(worker, cells[1]);
        } else{
            throw new NullPointerException();
        }

        // second build
        if( cells[2] != null ){
            super.build(worker, cells[2], false );
        } else{
            throw new NullPointerException();
        }

    }
}
