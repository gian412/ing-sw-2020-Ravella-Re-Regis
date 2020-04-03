package it.polimi.ingsw.model;

public class Worker {

    public final String WORKER_ID;
    private Player owner;
    private Cell currentCell;
    private Cell previousCell;
    private static boolean canMoveUp;

    /**
     * Class' constructor
     *
     * inizialize worker's id, the worker's owner and the other parameters to null
     *
     * @author Marco Re
     * @param workerId indicate worker's id
     * @param owner indicate worker's owner
     */
    // class constructor with the initialization of WORKER_ID and owner
    public Worker(String workerId, Player owner) {
        this.WORKER_ID = workerId;
        this.owner = owner;
        currentCell = null;
        previousCell = null;
        canMoveUp = true;
    }

    /**
     * workerID's getter
     *
     * @author Marco Re
     * @return worker's ID
     */
    // WORKER_ID's getter
    public String getWORKER_ID() {
        return WORKER_ID;
    }

    /**
     * owner's getter
     *
     * @author Marco Re
     * @return worker's owner
     */
    // owner's getter
    public Player getOwner(){
        return owner;
    }

    /**
     * getCurrentCell's getter
     *
     * @author Marco Re
     * @return the cell in which the worker is
     */
    // currentCell's getter and setter
    public Cell getCurrentCell() {
        return currentCell;
    }

    /** set a new currentCell
     *
     * set a new currentCell and put the old currentCell in previousCell
     *
     * @author Marco Re
     * @param currentCell the cell in which the player moved the worker
     */
    public void setCurrentCell(Cell currentCell) {
        setPreviousCell(this.getCurrentCell());
        this.currentCell = currentCell;
    }

    /**
     * getPreviousCell's getter
     *
     * @author Marco Re
     * @return the cell in which the worker was before his currentCell
     */
    // previousCell's getter and setter
    public Cell getPreviousCell() {
        return previousCell;
    }

    /**
     * set a new previousCell
     *
     * @author Marco Re
     * @param previousCell the cell in which the worker was before his move
     */
    public void setPreviousCell(Cell previousCell) {
        this.previousCell = previousCell;
    }

    /**
     * set if a worker can move up
     *
     * @author Gianluca Regis
     * @param canMoveUp is true if a worker can move up, is false if a worker can't move up for effect of a god
     */
    // canMoveUp's getter and setter
    public void setCanMoveUp(boolean canMoveUp) {
        Worker.canMoveUp = canMoveUp;
    }

    /**
     * return if a worker can move up
     *
     * @author Gianluca Regis
     * @return true if a worker can move up, false if a worker can't move up
     */
    public boolean isCanMoveUp() {
        return canMoveUp;
    }
}
