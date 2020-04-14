package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.God;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {


    private List<Player> playerList;
    private Board board;
    private God[] gods;

    /**
     * class' constructor
     *
     * create a new board
     */
    // class constructor with the initialization of board
    public Game(){
        playerList = new ArrayList<>();
        board = new Board();
    }

    /**
     * add a new player to the game
     *
     * @param playerName the player to add
     * @param age the age of the player
     * @return true if everything is good, false if there is an error
     */
    // playerList's adder
    public boolean addPlayer(String playerName, int age){
        try {
            playerList.add(new Player(playerName, age));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void setPlayerDivinity(String playerName, God divinity) throws NoSuchPlayerException {
        for(Player p : playerList)
            if(p.getNAME().equals(playerName)){
                p.setDivinity(divinity);
                return;
            }
        throw new NoSuchPlayerException();
    }

    // this methods needs to set all the nextPlayer's attributes
    public void startGame(){
        Collections.sort(playerList);

        for(int i = 0; i < playerList.size(); i++){
            if(i != playerList.size() - 1) playerList.get(i).setNextPlayer(playerList.get(i + 1));
            else playerList.get(i).setNextPlayer(playerList.get(0));
        }

        board.setTurnPlayer(playerList.get(0));
    }

    // board's getter
    public Board getBoard() {
        return board;
    }

    public Player getTurnPlayer(){ return board.getTurnPlayer(); }

    public String getPlayers(){
        StringBuilder players = new StringBuilder();

        for(Player p : playerList){
            players.append(p.getNAME());
            players.append(" ");
            players.append(p.getAge());
            players.append("\n");
        }

        return players.toString();
    }

    public ArrayList<Player> getPlayerList(){
        return playerList;
    }

}
