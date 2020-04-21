package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Pair;

public class Command {
    public final Pair coordinates;
    public final CommandType commandType;

    public Command(Pair coordinates, CommandType ct){
        this.coordinates = coordinates;
        this.commandType = ct;
    }
}
