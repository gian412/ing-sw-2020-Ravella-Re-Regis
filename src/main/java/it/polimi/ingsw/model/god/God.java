package it.polimi.ingsw.model.god;

import controller.Command;
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

    // interface abstract method
    public abstract void makeMove(Worker worker, Command command) throws IllegalMoveException;
    public abstract void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException;

    public void move(Worker worker, Cell cell) throws IllegalMoveException{

        if ( cell.getWorker() == null && cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) {
            board.moveWorker(worker, cell);
            hadWin = board.checkWin(worker);
        }else{
            throw new IllegalMoveException();
        }

    }

    public void build(Worker worker, Cell cell, boolean isDome) throws IllegalMoveException {
        // build
        if( cell.getWorker() == null ){
            if( cell.getHeight() == Height.DOME ){
                throw new IllegalMoveException();
            }else if( cell.getHeight() == Height.THIRD_FLOOR ){
                board.build( cell, true );
            }else if(isDome){
                board.build( cell, true );
            } else{
                board.build( cell, false );
            }
        } else{
            throw new IllegalMoveException();
        }
    }


}
