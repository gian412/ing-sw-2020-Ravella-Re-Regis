package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playerList;
    private Board board;
    private God[] gods;

    /**
     * class' constructor
     *
     * create a new board
     */
    // class constructor with the initialization of board
    public Game(){
        board = new Board();
    }

    /**
     * add a new player to the game
     *
     * @param player the player to add
     * @return true if everything is good, false if there is an error
     */
    // playerList's adder
    public boolean addPlayer(Player player){
        try {
            if(player.equals(null)) return false;
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

    public Player getTurnPlayer(){ return board.getTurnPlayer(); }

    public String getPlayers(){
        StringBuilder players = new StringBuilder("");

        for(Player p : playerList){
            players.append(p.getNAME() + " " + p.getAge() + "\n");
        }

        return players.toString();
    }
}
