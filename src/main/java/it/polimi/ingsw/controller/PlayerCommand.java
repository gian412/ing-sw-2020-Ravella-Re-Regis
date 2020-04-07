package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;

public class PlayerCommand {
    Player player;
    Command cmd;
    int workerIndex;

    public PlayerCommand(Player p, Command c, int workerIndex){
        player = p;
        cmd = c;
        this.workerIndex = workerIndex;
    }

    public Player getPlayer(){
        return player;
    }

    public  Command getCommand(){
        return cmd;
    }
    public int getWorkerIndex() {
        return workerIndex;
    }

}
