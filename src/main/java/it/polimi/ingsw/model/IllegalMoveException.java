package it.polimi.ingsw.model;

public class IllegalMoveException extends Exception{

    /**
     * Class constructor with the initialization of the error message
     */
    public IllegalMoveException(){
        super("Illegal move");
    }
}
