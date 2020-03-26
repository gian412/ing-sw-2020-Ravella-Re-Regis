package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public abstract class God {

    protected Board board;
    public final String NAME;
    protected boolean hadWin;

    // class constructor with the initialization of board
    public God(Board board, String NAME){
        this.board = board;
        this.NAME = NAME;
        hadWin = false;
    }

    // abstract class' abstract method
    public abstract void makeMove(Worker worker, Command command) throws IllegalMoveException;
    //public abstract void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException;

    /**
     * Move the worker
     *
     * Move the worker using board.moveWorker(worker, cell) and then check if the worker had
     * win using board.checkWin(worker) and saving this result in the class' variable hadWind
     *
     * @param worker is the worker you are moving
     * @param cell is the cell in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    public void move(Worker worker, Cell cell) throws IllegalMoveException{

        if ( cell.getWorker() == null && cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) {//Posso muovere verso l'alto
            if( worker.isCanMoveUp() || (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0) ){
                board.moveWorker(worker, cell);
                hadWin = board.checkWin(worker);
            } else{
                throw new IllegalMoveException();
            }
        }else{
            throw new IllegalMoveException();
        }

    }


    public void build(Cell cell, boolean isDome) throws IllegalMoveException {
        // build
        if( cell.getWorker() != null && cell.getHeight() != Height.DOME && isDome ){
            board.build( cell, true );
        }else if( cell.getWorker() != null && cell.getHeight() != Height.DOME && !isDome){
            board.build( cell, false );
        } else{
            throw new IllegalMoveException();
        }
    }

}
