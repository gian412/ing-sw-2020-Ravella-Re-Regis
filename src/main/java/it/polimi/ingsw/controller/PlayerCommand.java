package it.polimi.ingsw.controller;

import java.io.Serializable;

/**
 * encapsulates in a single data structure the command and the player issuing
 * it. this class is the only type of object sent by client (with the exceptions
 * of String type objects) during all the interaction between client and server
 *
 * @see Command
 * @author Elia Ravella
 */
public class PlayerCommand implements Serializable {
    private static final long serialVersionUID = 1;
    String playerName;
    Command cmd;
    String message;
    int workerIndex;

    /**
     * class constructor
     * 
     * @param p           the Name of the player
     * @param c           the Command object that represents the action
     * @param workerIndex the identifier for the worker that the user is willing to
     *                    move/build with
     */
    public PlayerCommand(String p, Command c, int workerIndex) {
        playerName = p;
        cmd = c;
        this.workerIndex = workerIndex;
    }

    public String getPlayer() {
        return playerName;
    }

    public Command getCommand() {
        return cmd;
    }

    public int getWorkerIndex() {
        return workerIndex;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
