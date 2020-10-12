package it.polimi.ingsw.exceptions;

public class IllegalAddException extends Exception {
    private static final long serialVersionUID = 1;

    public IllegalAddException() {
        super("Illegal add");
    }
}
