package it.polimi.ingsw.exceptions;

public class InvalidPlayerException extends Exception {
    private static final long serialVersionUID = 1;

    public InvalidPlayerException() {
        super("Invalid player, maybe wrong turn");
    }
}
