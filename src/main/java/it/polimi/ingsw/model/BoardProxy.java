package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.Observable;

import java.util.HashMap;
import java.util.Map;

public class BoardProxy extends Observable<BoardProxy> {

    private Height[][] boardScheme;
    private Map<String, Pair> workers;

    public BoardProxy(){
        boardScheme = new Height[5][5];
        workers = new HashMap<String, Pair>();
    }

    public void addHeight(int x, int y, Height h){
        boardScheme[x][y] = h;
    }

    public void addWorker(String worker, Pair coordinates){
        workers.put(worker, coordinates);
    }

    public void resetWorkers(){
        workers = new HashMap<String, Pair>();
    }

    public void updateProxy(){
        notify(this);
    }

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
