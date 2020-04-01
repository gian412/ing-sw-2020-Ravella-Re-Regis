package it.polimi.ingsw.model;

public class Cell {

    private Height height;
    private Worker worker;

    public final int X;
    public final int Y;

    /**
     * the constructor of class Cell
     *
     * it instantiate a new cell with the two coordinate and the height to GROUND
     *
     * @author Marco Re
     * @param x coordinate X of the cell
     * @param y coordinate Y of the cell
     */
    // class constructor with the initialization of X and Y
    public Cell(int x, int y) {
        this.X = x;
        this.Y = y;
        this.height = Height.GROUND;
    }

    /**
     * worker's getter
     *
     * @author Marco Re
     * @return the worker which is present in the cell, null if there isn't any worker
     */
    // worker's getter
    public Worker getWorker() {
        return worker;
    }

    /**
     *worker's setter
     *
     * @author Marco Re
     * @param worker the worker which the player wants to set in the cell
     */
    // worker's setter
    public void setWorker(Worker worker){
        this.worker = worker;
    }

    /**
     * height's getter
     *
     * @author Marco Re
     * @return the height of the cell
     */
    // height's getter
    public Height getHeight() {
        return height;
    }

    /**
     * height's setter
     *
     * it is used when the player wants to build a dome when the actual height isn't a THIRD FLOOR
     *
     * @author Marco Re
     * @param height is the new height of the cell
     */
    // height's setter
    public void setHeight(Height height){
        this.height = height;
    }

    /**
     * add a level
     *
     * it is used to add a level in the cell
     *
     * @author Marco Re
     */
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
     * @author Gianluca Regis
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
     * @author Marco Re
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

    /**
     * compare two cells
     *
     * override the method equals of the class Object
     *
     * @author Marco Re
     * @param o is the cell to compare
     * @return true if two cells are equal
     */
    // equals implementetion for cell
    @Override
    public boolean equals(Object o){

        if( !( o instanceof Cell))
            return false;

        Cell other = (Cell) o;

        return this.X == other.X && this.Y == other.Y && this.worker == other.worker && this.height == other.height;
    }

    /**
     * create a string which represents the attributes of the cell
     *
     * override the method toString of the class Object
     *
     * @author Marco Re
     * @return true if two cells are equal
     */
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
