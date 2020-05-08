package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;

import java.io.Serializable;

public class PlayerCommand implements Serializable {
    String playerName;
    Command cmd;
    String message;
    int workerIndex;

    public PlayerCommand(String p, Command c, int workerIndex){
        playerName = p;
        cmd = c;
        this.workerIndex = workerIndex;
    }

    public String getPlayer(){
        return playerName;
    }

    public  Command getCommand(){
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
