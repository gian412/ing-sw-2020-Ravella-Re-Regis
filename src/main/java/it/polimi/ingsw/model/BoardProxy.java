package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.Observable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BoardProxy extends Observable<BoardProxy> implements Serializable {

    private Height[][] boardScheme;
    private Map<String, Pair> workers;
    private Player winner, turnPlayer;
    private String choosingGods, illegalMoveString;

    public String getIllegalMoveString() {
        return illegalMoveString;
    }

    public void setIllegalMoveString(String illegalMoveString) {
        this.illegalMoveString = illegalMoveString;
    }

    public BoardProxy(){
        boardScheme = new Height[5][5];
        workers = new HashMap<>();
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public void setWinner(Player player){
        this.winner = player;
    }

    public Player getWinner() {
        return winner;
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
     * returns a string that represents the board. not very useful but can be used in console repr
     *
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder myBoard = new StringBuilder();

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
