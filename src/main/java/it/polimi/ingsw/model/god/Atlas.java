package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Atlas extends God {

    public Atlas(Board board) {
        super(board);
    }

    // array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException {

        // move
        super.move(worker, cells[0]);

        // build
        super.build(worker, cells[1], isDome);

    }
}
