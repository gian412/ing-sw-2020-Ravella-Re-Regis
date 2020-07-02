package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.God;

/**
 * this class represents the Player in the game.
 * it manages the workers on the Board and stores all the fundamental information about a player of Santorini
 *
 * @author Marco Re
 */
public class Player implements Comparable<Player> {

    private Game actualGame;
    private final String NAME;
    private God divinity;
    private final int age;
    private Player nextPlayer;
    private Worker[] workers = new Worker[2];
    private boolean isTurnPlayer;

    public Player(String name, int age){
        this.NAME = name;
        this.age = age;
        this.isTurnPlayer = false;

        workers[0] = new Worker(this.NAME + "0", this);
        workers[1] = new Worker(this.NAME + "1", this);
    }

    //NAME's get
    public String getNAME(){ return NAME;}

    //turnPlayer's getter and setter
    public boolean getTurnPlayer(){
        return isTurnPlayer;
    }

    public void setTurnPlayer(boolean flag){
        isTurnPlayer = flag;
    }

    // actualGame's setter
    public void setActualGame(Game actualGame) {
        this.actualGame = actualGame;
    }

    // divinity's getter and setter
    public void setDivinity(God divinity) {
        this.divinity = divinity;
    }

    public God getDivinity(){ return this.divinity;}

    // age's getter and setter
    public int getAge() {
        return age;
    }

    // nextPlayer's getter and setter
    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public Worker[] getWorkers(){
        return workers;
    }

    //compare two player and return if is the same
    public boolean equals(Player p) {
        return (this.NAME.equals(p.NAME));
    }

    //permit to the player to decide his divinity
    public void selectGod(God[] listDivinity){}

    @Override
    public int compareTo(Player other) {
        return this.getAge() - other.getAge();
    }
}
