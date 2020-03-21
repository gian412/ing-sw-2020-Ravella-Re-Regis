package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playerList;
    private Board board;
    private God[] gods;

    // class constructor with the initialization of board
    public Game(){
        board = new Board();
    }

    // playerList's adder
    public boolean addPlayer(Player player){
        try {
            playerList.add(player);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    // board's getter
    public Board getBoard() {
        return board;
    }
}
