package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.utils.GameState;

import java.util.*;

/**
 * this class encapsulates the whole match. it reunites the players with the Board and takes care
 * of the correct management of the game's startup and closing
 *
 * @see Board
 * @see Player
 * @author Marco Re
 */
public class Game {

    private List<Player> playerList;
    private Board board;

    /**
     * class' constructor
     *
     * create a new board
     */
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

    /**
     * triggers the "end-game" procedure to inform the clients that the match is ended
     * @author Ravella Elia
     */
    public void endGame(){
        board.endGame();
    }

    /**
     * this methods starts the game
     *
     * in detail: sorts the player's list by the age (the youngest is the "Challenger", the first player)
     * and concatenates all the other players in a circular dataset. This method also sets the status of the board to
     * SELECTING_GOD that is the first part of the game. as last thing, it sets the first player of the list as
     * the turn player
     *
     * @see GameState
     * @see BoardProxy
     * @author Ravella Elia, Marco Re
     */
    public void startGame(){
        Collections.sort(playerList);

        for(int i = 0; i < playerList.size(); i++){
            if(i != playerList.size() - 1) playerList.get(i).setNextPlayer(playerList.get(i + 1));
            else playerList.get(i).setNextPlayer(playerList.get(0));
        }

        board.getProxy().setStatus(GameState.SELECTING_GOD);
        board.setTurnPlayer(playerList.get(0));
    }

    public Board getBoard() {
        return board;
    }

    public Player getTurnPlayer(){ return board.getTurnPlayer(); }

    public int getNumberOfPlayers(){
        return playerList.size();
    }

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

    public Map<String, String> getGodsMap() {
        Map<String, String> gods = new HashMap<>();

        for (Player p : playerList) {
            gods.put(p.getNAME(), p.getDivinity().NAME.getCapitalizedName());
        }

        return  gods;
    }
}
