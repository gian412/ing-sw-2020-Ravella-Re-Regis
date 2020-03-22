package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Hephaestus extends God {

    // class constructor with the initialization of board using the super constructor
    public Hephaestus(Board board) {
        super(board);
    }

    // array cell composed by 3 cells, 1 for the moves and 2 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        // first build
        if(cells[1] != null){
            super.build(worker, cells[1], false);
        } else{
            throw new NullPointerException();
        }

        // second build
        if( cells[2] != null ){
            if( cells[2] == cells[1] && cells[2].getHeight() != Height.THIRD_FLOOR ){
                super.build(worker, cells[1], false);
            }else{
                throw new IllegalMoveException();
            }
        }
    }
}
