package it.polimi.ingsw.exceptions;

public class InvalidPlayerException extends Exception {
    public InvalidPlayerException(){ super("Invalid player, maybe wrong turn"); }
}
