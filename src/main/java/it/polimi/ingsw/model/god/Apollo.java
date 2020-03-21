package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public class Apollo extends God{

    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board);
    }

    // array cell composto da 2 celle, una dove muoversi ed una dove costruire.
    @Override
    public void makeMove(Worker worker, Cell[] cells){

        if( cells[0].getWorker() == null ){
            board.moveWorker(worker, cells[0]);
        }else{
            Worker otherWorker = cells[1].getWorker();
            Cell actualCell = worker.getCurrentCell();
            board.moveWorker(worker, cells[1]);
            board.forceWorker(otherWorker, actualCell);
        }

    }

}
