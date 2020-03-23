package it.polimi.ingsw.model;

public class Worker {

    public final String WORKER_ID;
    private Player owner;
    private Cell currentCell;
    private Cell previousCell;
    private static boolean canMoveUp;

    // class constructor with the initialization of WORKER_ID and owner
    public Worker(String workerId, Player owner) {
        this.WORKER_ID = workerId;
        this.owner = owner;
        currentCell = null;
        previousCell = null;
        canMoveUp = true;
    }

    // WORKER_ID's getter
    public String getWORKER_ID() {
        return WORKER_ID;
    }

    // owner's getter
    public Player getOwner(){
        return owner;
    }

    // currentCell's getter and setter
    public Cell getCurrentCell() {
        return currentCell;
    }
    public void setCurrentCell(Cell currentCell) {
        setPreviousCell(currentCell);
        this.currentCell = currentCell;
    }

    // previousCell's getter and setter
    public Cell getPreviousCell() {
        return previousCell;
    }
    public void setPreviousCell(Cell previousCell) {
        this.previousCell = previousCell;
    }

    // canMoveUp's getter and setter
    public void setCanMoveUp(boolean canMoveUp) {
        Worker.canMoveUp = canMoveUp;
    }
    public boolean isCanMoveUp() {
        return canMoveUp;
    }
}
