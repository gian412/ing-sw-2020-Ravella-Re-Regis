package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;

public class PlayerCommand {
    Player player;
    Command cmd;

    public PlayerCommand(Player p, Command c){
        player = p;
        cmd = c;
    }

    public Player getPlayer(){
        return player;
    }

    public  Command getCommand(){
        return cmd;
    }

}
