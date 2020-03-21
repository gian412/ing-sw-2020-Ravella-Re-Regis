package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Artemis extends God {


    public Artemis(Board board) {
        super(board);
    }

    @Override
    public void makeMove(Worker worker, Cell[] cells) throws IllegalMoveException {

        // move
        if( cells[0].getWorker() == null ) {
            if (cells[0].getHeight() == Height.DOME) {
                throw new IllegalMoveException();
            } else {
                board.moveWorker(worker, cells[0]);
            }
        }else{
            throw new IllegalMoveException();
        }

    }
}
