package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalAddException;
import it.polimi.ingsw.exceptions.IllegalCellException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.view.RemoteView;

public class Board {

    private BoardProxy proxy;
    private Cell[][] cells;
    private Player turnPlayer;
    private Player hasWon;

    /**
     * class' constructor
     *
     * create a new boardProxy, set the win to false an initialize al the cells of the board and then
     * update proxyBoard
     *
     * @author Marco Re
     */
    // class constructor with the initialization of cells
    public Board(){
        hasWon = null;
        cells = new Cell[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                cells[i][j] = new Cell(i, j);
            }
        }

        proxy = new BoardProxy();
        proxy.updateProxy();
    }

    /**
     * return a specific cell of the board
     *
     * @param coordinates the x and y coord. of the cell
     * @return the selected cell
     */
    // cells' getter
    public Cell getCell(Pair coordinates) {
        return cells[coordinates.x][coordinates.y];
    }

    // turnPlayer's getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player player) {
        this.turnPlayer = player;
        proxy.setChoosingGods(player.getNAME());
        updateProxyBoard();
    }

    /**
     * updates the remoteviews (by the proxy) informing that an illegal
     * move has been made
     *
     * @param message the description of the illegal move
     */
    public void notifyIllegalMove(String message){
        this.proxy.setIllegalMoveString(message);
        this.updateProxyBoard();
    }

    public void changeTurnPlayer(){
        turnPlayer = turnPlayer.getNextPlayer();
    }

    /**
     * build a structure on the board
     *
     * @param originCell cell in which the worker is
     * @param coordinates coordinates in which the player wants to build
     * @param isDome is true if a god, who has the ability to build dome not only after the third level, build a dome
     */
    public void build(Cell originCell, Pair coordinates, boolean isDome) throws IllegalMoveException {

        if((coordinates.x >= 0) && (coordinates.x < 5) && (coordinates.y >= 0) && (coordinates.y < 5) && originCell.cellDistance(coordinates)){
            if(isDome){
                if(this.getCell(coordinates).getHeight() == Height.THIRD_FLOOR) {
                    this.getCell(coordinates).buildFloor();
                }
                else{
                    this.getCell(coordinates).setHeight(Height.DOME);
                }
            }
            else {
                this.getCell(coordinates).buildFloor();
            }
            this.updateProxyBoard();
        }
        else{
            throw new IllegalMoveException();
        }
    }

    /**
     * move a worker in an other cell
     *
     * @author Marco Re
     * @param worker the worker that the player moves
     * @param coordinates coordinates in which the player moves the worker
     */
    public void moveWorker(Worker worker, Pair coordinates) throws IllegalMoveException{

        if((coordinates.x >= 0) && (coordinates.x < 5) && (coordinates.y >= 0) && (coordinates.y < 5) && (worker.getCurrentCell().cellDistance(coordinates))){

            this.getCell(new Pair(
                    worker.getCurrentCell().X,
                    worker.getCurrentCell().Y)
            ).setWorker(null);

            worker.setPreviousCell(this.getCell(new Pair(
                    worker.getCurrentCell().X,
                    worker.getCurrentCell().Y))
            );

            this.getCell(coordinates).setWorker(worker);
            worker.setCurrentCell(this.getCell(coordinates));

            //update the proxyBoard after a legal move
            this.updateProxyBoard();
        }
        else{
            throw new IllegalMoveException();
        }
    }

    /**
     * Force a worker in another cell
     *
     * @author Gianluca Regis
     * @param worker the worker that the player moves
     * @param coordinates the cell in which the player moves the worker
     */
    public void forceWorker(Worker worker, Pair coordinates) throws IllegalMoveException{

        if((coordinates.x >= 0) && (coordinates.x < 5) && (coordinates.y >= 0) && (coordinates.y < 5)){

            this.getCell(new Pair(
                    worker.getCurrentCell().X,
                    worker.getCurrentCell().Y)
            ).setWorker(null);

            worker.setPreviousCell(this.getCell(new Pair(
                    worker.getCurrentCell().X,
                    worker.getCurrentCell().Y))
            );

            this.getCell(coordinates).setWorker(worker);
            worker.setCurrentCell(this.getCell(coordinates));

            //update the proxyBoard after a legal move
            this.updateProxyBoard();
        }
        else{
            throw new IllegalMoveException("Invalid FORCE parameters");
        }
    }

    /** put a worker on the board
     *
     * at the start of the game the player put his workers on the board
     *
     * @param coordinates in which the player wants to put the worker
     * @throws IllegalCellException,IllegalAddException
     * illegalCellException is the cell doesn't exist
     * illegalAddException if the player has already put his two workers
     */
    public void addWorker(Pair coordinates) throws IllegalCellException, IllegalAddException {

        // check if the two workers are already set with the first cell
        if( (this.turnPlayer.getWorkers()[0].getCurrentCell() == null) || (this.turnPlayer.getWorkers()[1].getCurrentCell() == null)) {
            // check if the cell where the player wants to put the workers exists and is free
            if( (coordinates.x >= 0) && (coordinates.x < 5) && (coordinates.y >= 0) && (coordinates.y < 5) && (this.getCell(coordinates).getHeight() == Height.GROUND) && (this.getCell(coordinates).getWorker() == null)) {
                //check if th first worker is already set
                if(this.turnPlayer.getWorkers()[0].getCurrentCell() == null){
                    //add the first worker
                    this.getCell(coordinates).setWorker(this.turnPlayer.getWorkers()[0]);
                    this.turnPlayer.getWorkers()[0].setCurrentCell(this.getCell(coordinates));
                }

                else{
                    //add the second worker
                    this.getCell(coordinates).setWorker(this.turnPlayer.getWorkers()[1]);
                    this.turnPlayer.getWorkers()[1].setCurrentCell(this.getCell(coordinates));
                }

            }
            else{
                throw new IllegalCellException();
            }
        }

        else{
            throw new IllegalAddException();
        }
    }

    /**
     * check the winning condition
     *
     * @author Gianluca regis
     * @param worker the worker that the player have just moved
     * @return true if the player wins, false if the player doesn't win
     */
    // method that check if the worker has win after the last move
    public boolean checkWin(Worker worker){

        if (worker.getPreviousCell()!=null){
            byte heightDifference = worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight());

            //check the win with and without Pan
            if (worker.getOwner().getDivinity().NAME.equals("PAN")){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference <= -2){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else{
                    return false;
                }

            } else if (worker.getOwner().getDivinity().NAME.equals("CHRONUS")){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || countCompleteTower()){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else {
                    return false;
                }
            } else{
                if (heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else{
                    return false;
                }
            }
        } else {
            if (worker.getOwner().getDivinity().NAME.equals("CHRONUS")){
                if (countCompleteTower()){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else {
                    return false;
                }
            } else{
                return false;
            }
        }
    }

    /**
     * update the proxy board after every step
     */
    public void updateProxyBoard(){
        proxy.resetWorkers();

        for(int rows = 0; rows < 5; rows++)
            for (int cols = 0; cols < 5; cols++){
                proxy.addHeight(rows, cols, cells[rows][cols].getHeight());

                if(cells[rows][cols].getWorker() != null){
                    proxy.addWorker(
                            cells[rows][cols].getWorker().getWORKER_ID(),
                            new Pair(rows, cols)
                    );
                }
            }

        proxy.updateProxy();
    }

    public void addView(RemoteView remoteView){
        proxy.addObserver(remoteView);
    }

    /**ends the game
     *
     * @authors Ravella Elia
     */
    public void endGame(){
        proxy.setWinner(new Player("Unexpected Game Over", -1));
        this.updateProxyBoard();
    }

    /**
     * Count the number of complete towers in the board
     *
     * @author Gianluca Regis
     * @return true if there are at least five completed towers in the board, otherwise return false
     */
    public boolean countCompleteTower(){
        int completedTowers = 0;
        for(int row = 0; row < cells.length; row++)
            for(int column = 0; column < cells[row].length; column++)
                if(cells[row][column].isCompleted())completedTowers++;

        return completedTowers >= 5;
    }

    /**
     *create a string which represents the attributes and the structure of the board
     *
     * override the method toString of the class Object
     *
     * @author Marco Re
     * @return the string which represents the board
     */
    @Override
    public String toString() {
        StringBuilder myBoard = new StringBuilder();

        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++) {
                myBoard.append(cells[row][col].toString());
                myBoard.append('\t');
            }
            myBoard.append("\n\n");
        }

        return myBoard.toString();
    }
}
