package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board;

import java.util.HashMap;
import java.util.Map;

public class BoardProxy {

    public final Height[][] boardScheme;
    public final Map<String, Pair> workers;

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

}
