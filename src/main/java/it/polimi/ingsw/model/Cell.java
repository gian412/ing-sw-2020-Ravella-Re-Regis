package it.polimi.ingsw.model;

public class Cell {

    private Height height;
    private Worker worker;

    public final int X;
    public final int Y;

    // class constructor with the initialization of X and Y
    public Cell(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    // worker's getter
    public Worker getWorker() {
        return worker;
    }

    // height's getter
    public Height getHeight() {
        return height;
    }
}
