package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.God;

import java.util.ArrayList;


public class Player {

    private Game actualGame;
    private final String NAME;
    public God divinity;
    private int age;
    private Player nextPlayer;
    private Worker[] workers = new Worker[2];

    public Player(String name){
        this.NAME = name;
    }

    // actualGame's setter
    public void setActualGame(Game actualGame) {
        this.actualGame = actualGame;
    }

    // divinity's setter
    public void setDivinity(God divinity) {
        this.divinity = divinity;
    }

    // age's getter and setter
    public void setAge(int age) {
        this.age = age;
    }
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

    // workers[]'s setter
    public void setWorkers() {
        workers[0] = new Worker(this.NAME + "0", this);
        workers[1] = new Worker(this.NAME + "1", this);
    }

    // getter of the workers
    public Worker[] getWorkers(){
        return workers;
    }
}
