package it.polimi.ingsw.controller;

public class InvalidPlayerException extends Exception {
    public InvalidPlayerException(){ super("Invalid player, maybe wrong turn"); }
}