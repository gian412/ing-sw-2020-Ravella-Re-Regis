package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Artemis extends God {


    public Artemis(Board board) {
        super(board);
    }

    // array cell composed by 3 cells, 2 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells) throws IllegalMoveException {

        // first move
        if( cells[0].getWorker() == null ) {
            if( cells[0].getHeight() == Height.DOME ) {
                throw new IllegalMoveException();
            } else {
                board.moveWorker(worker, cells[0]);
            }
        }else{
            throw new IllegalMoveException();
        }

        //Second move
        if( cells[1].getHeight() == null && !( cells[1].equals( worker.getPreviousCell() ) ) ){
            if( cells[1].getHeight() == Height.DOME ){
                throw new IllegalMoveException();
            } else{
                board.moveWorker(worker, cells[1]);
            }
        } else{
            throw new IllegalMoveException();
        }

        // build
        if( cells[1].getHeight() == Height.DOME ){
            throw new IllegalMoveException();
        }else if( cells[2].getHeight() == Height.THIRD_FLOOR ){
            board.build( cells[2], true);
        }else{
            board.build( cells[2], false);
        }

    }
}
