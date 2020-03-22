package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Athena extends God{

    // class constructor with the initialization of board using the super constructor
    public Athena(Board board) {
        super(board, "ATHENA");
    }

    // array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // reset of the power of Athena
        if (worker != null){
            if( !(worker.isCanMoveUp()) ){
                board.setCanMoveUp( true );
            }
        } else{
            throw new NullPointerException();
        }

        // move
        if( cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        // set the power of Athena
        if( worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight()) > 0 ){
            board.setCanMoveUp( false );
        }

        // build
        if(cells[1] != null){
            super.build(worker, cells[1], false);
        } else{
            throw new NullPointerException();
        }

    }
}
