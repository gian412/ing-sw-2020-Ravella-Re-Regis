package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.*;

public abstract class God {

    public Board board;

    // class constructor with the initialization of board
    public God(Board board){
        this.board = board;
    }

    // interface abstract method
    public abstract void makeMove(Worker worker, Cell[] cells);


}
