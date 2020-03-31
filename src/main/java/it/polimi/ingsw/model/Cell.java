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
    public void setWorker(Worker worker){
        this.worker = worker;
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

    /**
     * Get the direction between two cell
     *
     * This method return the distance between two cells passed like parameters
     *
     * @param secondCell is the cell in which the distance finish
     * @return an array of two integer with the two coordinates x and y
     */
    // method that return the direction of the movement of the worker
    public int[] getDirection(Cell secondCell){

        int[] direction = new int[2];

        direction[0] = secondCell.X - this.X;
        direction[1] = secondCell.Y - this.Y;

        return direction;
    }

    /**
     *check if the cell is adjacent to an other
     *
     * @author Gianluca Regis
     * @param cell the other cell
     * @return true if the cell is adjacent
     */
    public boolean cellDistance(Cell cell){
        int distanceX, distanceY;

        distanceX = this.X - cell.X;
        distanceY = this.Y - cell.Y;

        if ((distanceX >= -1) && (distanceX <= 1)) {
            return (distanceY >= -1) && (distanceY <= 1);
        }
        else
            return false;
    }

    // equals implementetion for cell
    @Override
    public boolean equals(Object o){

        if( !( o instanceof Cell))
            return false;

        Cell other = (Cell) o;

        return this.X == other.X && this.Y == other.Y && this.worker == other.worker;
    }

    @Override
    public String toString() {
        StringBuilder myCell = new StringBuilder();

        if(this.worker == null)
            myCell.append(this.height.toString().toUpperCase().charAt(0));

        else {
            myCell.append(this.height.toString().toUpperCase().charAt(0));
            myCell.append('/');
            myCell.append(this.worker.getOwner().getNAME());
        }

        return myCell.toString();
    }
}
