package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.Observable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class acts as a proxy of the model's Board: it contains all the Board's information to display it properly and
 * some other infos about the match in general. As the PlayerCommand, the BoardProxy is the only type of object that
 * the server sends to the client
 *
 * @see it.polimi.ingsw.controller.PlayerCommand
 * @see Board
 * @author Marco Re, Elia Ravella
 */
public class BoardProxy extends Observable<BoardProxy> implements Serializable {

    private Height[][] boardScheme;
    private Map<String, String> gods;
    private Map<String, Pair> workers;
    private String winPlayer;
    private String turnPlayer;
    private GameState status;
    private String choosingGods, illegalMoveString;

    /**
     * class constructor, instantiates all the variables and data structures
     */
    public BoardProxy(){
        boardScheme = new Height[5][5];
        workers = new HashMap<>();
        choosingGods = "";
        illegalMoveString = "";
        winPlayer = "";
    }

    public Map<String, String> getGods() {
        return gods;
    }

    public void setGods(Map<String, String> mapGods){
        gods = mapGods;
    }

    public GameState getStatus() {
        return status;
    }

    public void setStatus(GameState status) {
        this.status = status;
    }

    public String getTurnPlayer() {
        return turnPlayer;
    }

    public String getIllegalMoveString() {
        return illegalMoveString;
    }

    public void setIllegalMoveString(String illegalMoveString) {
        this.illegalMoveString = illegalMoveString;
    }

    public Height[][] getBoardScheme() {
        return boardScheme;
    }

    public Map<String, Pair> getWorkers() {
        return workers;
    }

    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public void setWinner(String player){
        this.winPlayer = player;
        this.setStatus(GameState.TERMINATOR);
    }

    public String getWinner() {
        return winPlayer;
    }

    public void addHeight(int x, int y, Height h){
        boardScheme[x][y] = h;
    }

    public void addWorker(String worker, Pair coordinates){
        workers.put(worker, coordinates);
    }

    public void resetWorkers(){
        workers = new HashMap<>();
    }

    public void updateProxy(){
        notify(this);
    }

    public String getChoosingGods() {
        return choosingGods;
    }

    public void setChoosingGods(String choosingGods) {
        this.choosingGods = choosingGods;
    }

    /**
     * returns a string that represents the board. it's used in console representation
     *
     * @return the string representing the Board
     */

    @Override
    public String toString() {
        StringBuilder myBoard = new StringBuilder();

        myBoard.append(this.choosingGods + "\n\n");

        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++) {
                myBoard.append(boardScheme[row][col].toString());
                myBoard.append('\t');
            }
            myBoard.append("\n\n");
        }

        return myBoard.toString();
    }
}
