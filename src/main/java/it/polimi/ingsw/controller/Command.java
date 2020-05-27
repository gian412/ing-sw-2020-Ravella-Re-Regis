package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.CommandType;

import java.io.Serializable;

public class Command implements Serializable {
    public final Pair coordinates;
    public final CommandType commandType;

    public Command(Pair coordinates, CommandType ct){
        this.coordinates = coordinates;
        this.commandType = ct;
    }
}
