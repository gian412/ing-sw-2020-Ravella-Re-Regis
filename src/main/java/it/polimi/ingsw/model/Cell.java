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
        this.height = Height.GROUND;
    }

    // worker's getter
    public Worker getWorker() {
        return worker;
    }

    // worker's setter
    public void setWorker(Worker worker){ this.worker = worker;}

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
        switch(this.height){
            case GROUND:
                this.height = Height.FIRST_FLOOR;
                break;
            case FIRST_FLOOR:
                this.height = Height.SECOND_FLOOR;
                break;
            case SECOND_FLOOR:
                this.height = Height.THIRD_FLOOR;
                break;
            case THIRD_FLOOR:
                this.height = Height.DOME;
                break;
        }
    }

    // equals implementetion for cell
    public boolean equals(Object o){

        if( !( o instanceof Cell))
            return false;

        Cell other = (Cell) o;

        return this.X == other.X && this.Y == other.Y && this.worker == other.worker;
    }


    @Override
    public String toString() {
        return this.height.toString();
    }
}
