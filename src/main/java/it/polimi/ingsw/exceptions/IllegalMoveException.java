package it.polimi.ingsw.exceptions;

public class IllegalMoveException extends Exception{

    /**
     * Class constructor with the initialization of the error message
     */
    public IllegalMoveException(){
        super("Illegal move");
    }

    public IllegalMoveException(String error) {
        super(error);
    }
}
