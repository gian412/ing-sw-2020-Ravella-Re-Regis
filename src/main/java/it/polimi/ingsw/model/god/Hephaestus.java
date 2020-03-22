package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Hephaestus extends God {

    // class constructor with the initialization of board using the super constructor
    public Hephaestus(Board board) {
        super(board);
    }

    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException {

        // move
        super.move(worker, cells[0]);

        // first build
        super.build(worker, cells[1], false);

        // second build
        if( cells[2] != null && cells[2] == cells[1] && cells[2].getHeight() != Height.THIRD_FLOOR ){
            super.build(worker, cells[1], false);
        }else{
            // TODO: Eccezione o niente?
            throw new IllegalMoveException();
        }

    }
}
