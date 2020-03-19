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

    // height's setter
    public void setHeight(Height height){
        this.height = height;
    }

    // height's adder
    public void buildFloor(){
        switch(height){
            case GROUND:
                height = Height.FIRST_FLOOR;
                break;
            case FIRST_FLOOR:
                height = Height.SECOND_FLOOR;
                break;
            case SECOND_FLOOR:
                height = Height.THIRD_FLOOR;
                break;
            case THIRD_FLOOR:
                height = Height.DOME;
                break;
        }
    }
}
