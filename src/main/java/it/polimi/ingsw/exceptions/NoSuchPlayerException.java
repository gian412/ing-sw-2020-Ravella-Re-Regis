package it.polimi.ingsw.exceptions;

public class NoSuchPlayerException extends Exception {
    private static final long serialVersionUID = 1;

    public NoSuchPlayerException() {
        super("No player");
    }
}
