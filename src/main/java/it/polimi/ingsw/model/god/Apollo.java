package it.polimi.ingsw.model.god;

import com.sun.tools.javac.util.Log;
import it.polimi.ingsw.model.*;

public class Apollo extends God{

    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board);
    }

    // array cell composto da 2 celle, una dove muoversi ed una dove costruire.
    @Override
    public void makeMove(Worker worker, Cell[] cells) throws IllegalMoveException {

        // move
        if( cells[0].getWorker() == null ){
            if( cells[0].getHeight() == Height.DOME ){
                throw new IllegalMoveException();
            }else{
                board.moveWorker(worker, cells[0]);
            }
        }else{
            Worker otherWorker = cells[0].getWorker();
            Cell actualCell = worker.getCurrentCell();
            board.moveWorker(worker, cells[0]);
            board.forceWorker(otherWorker, actualCell);
        }

        // build
        if( cells[1].getHeight() == Height.DOME ){
            throw new IllegalMoveException();
        }else if( cells[1].getHeight() == Height.THIRD_FLOOR ){
            board.build( cells[1], true);
        }else{
            board.build( cells[1], false);
        }

    }

}