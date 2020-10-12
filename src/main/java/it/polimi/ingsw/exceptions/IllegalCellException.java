package it.polimi.ingsw.exceptions;

public class IllegalCellException extends Exception {
    private static final long serialVersionUID = 1;

    public IllegalCellException() {
        super("Illegal cell");
    }
}
