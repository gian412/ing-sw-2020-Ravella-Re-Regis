package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.CommandType;

import java.io.Serializable;

/**
 * Represents an action that the user want to perform
 * on a worker
 *
 * @author Elia Ravella
 */
public class Command implements Serializable {
    public final Pair coordinates;
    public final CommandType commandType;

    /**
     * class constructor
     *
     * @param coordinates the target coordinates for the action
     * @param ct the type of action to be performed
     * @see CommandType
     */
    public Command(Pair coordinates, CommandType ct){
        this.coordinates = coordinates;
        this.commandType = ct;
    }
}
