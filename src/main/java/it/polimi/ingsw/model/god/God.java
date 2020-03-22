package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public abstract class God {

    public Board board;

    // class constructor with the initialization of board
    public God(Board board){
        this.board = board;
    }

    // interface abstract method
    public abstract void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException;

    public void move(Worker worker, Cell cell) throws IllegalMoveException{

        if( cell.getWorker() == null && cell.getHeight() != Height.DOME ) {
            board.moveWorker(worker, cell);
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
