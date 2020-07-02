package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.IllegalAddException;
import it.polimi.ingsw.exceptions.IllegalCellException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.RemoteView;

import java.util.Map;

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
    public Cell getCell(Pair coordinates) throws IndexOutOfBoundsException{

        if(coordinates.x >= 0 && coordinates.x < 5 && coordinates.y >= 0 && coordinates.y < 5) {
            return cells[coordinates.x][coordinates.y];
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    public BoardProxy getProxy() {
        return proxy;
    }

    // turnPlayer's getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player player) {
        this.turnPlayer = player;
        updateProxyBoard();
    }

    /**
     *
     * @param message the list of gods
     */
    public void setChoosingGods(String message){
        proxy.setChoosingGods(message);
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
        this.proxy.setIllegalMoveString("");
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
     * Switch two workers
     *
     * @author Gianluca Regis
     * @param worker first worker (the one who execute the switch)
     * @param otherWorker second worker (the one who suffer the switch)
     */
    public void switchWorkers(Worker worker, Worker otherWorker) throws IllegalMoveException {

        if (worker.getCurrentCell().cellDistance(new Pair(otherWorker.getCurrentCell().X, otherWorker.getCurrentCell().Y))) {
            // Reset cells' worker
            this.getCell(new Pair(worker.getCurrentCell().X, worker.getCurrentCell().Y)).setWorker(otherWorker);
            this.getCell(new Pair(otherWorker.getCurrentCell().X, otherWorker.getCurrentCell().Y)).setWorker(worker);

            // Update previousCells' infos
            worker.setPreviousCell(otherWorker.getCurrentCell());
            otherWorker.setPreviousCell(worker.getCurrentCell());

            // Update currentCells' infos using a tmp cell variable
            Cell tmp = otherWorker.getCurrentCell();
            otherWorker.setCurrentCell(worker.getCurrentCell());
            worker.setCurrentCell(tmp);

            //update the proxyBoard
            this.updateProxyBoard();
        } else {
            throw new IllegalMoveException("Invalid distance");
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
        } else {
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
                    this.updateProxyBoard();
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
     * Remove a worker from the board
     *
     * @author Gianluca Regis
     * @param worker the worker to remove
     */
    public void removeWorker(Worker worker) {
        worker.getCurrentCell().setWorker(null);
    }

    public Cell[][] getNeighbors(Cell currentCell) {

        Cell[][] neighbors = new Cell[3][3];
        Pair[][] coordinates = {
                {
                    new Pair(currentCell.X-1, currentCell.Y-1),
                    new Pair(currentCell.X, currentCell.Y-1),
                    new Pair(currentCell.X+1, currentCell.Y-1)
                },
                {
                    new Pair(currentCell.X-1, currentCell.Y),
                    new Pair(currentCell.X, currentCell.Y),
                    new Pair(currentCell.X+1, currentCell.Y)
                },
                {
                    new Pair(currentCell.X-1, currentCell.Y+1),
                    new Pair(currentCell.X, currentCell.Y+1),
                    new Pair(currentCell.X+1, currentCell.Y+1)
                }
        };

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                try {
                    Cell cell = this.getCell(coordinates[i][j]); // Get the reference to the cell
                    neighbors[i][j] = cell;
                } catch (IndexOutOfBoundsException e) {
                    neighbors[i][j] = null;

                }
            }
        }

        return neighbors;

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
            if (worker.getOwner().getDivinity().NAME.equals(GodType.PAN)){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference <= -2){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner().getNAME());
                    proxy.setStatus(GameState.TERMINATOR);
                    proxy.updateProxy();
                    return true;
                } else{
                    return false;
                }

            } else if (worker.getOwner().getDivinity().NAME.equals(GodType.CHRONUS)){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || countCompleteTower()){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner().getNAME());
                    proxy.setStatus(GameState.TERMINATOR);
                    proxy.updateProxy();
                    return true;
                } else {
                    return false;
                }
            } else{
                if (heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner().getNAME());
                    proxy.setStatus(GameState.TERMINATOR);
                    proxy.updateProxy();
                    return true;
                } else{
                    return false;
                }
            }
        } else {
            if (worker.getOwner().getDivinity().NAME.equals(GodType.CHRONUS)){
                if (countCompleteTower()){
                    hasWon = worker.getOwner();
                    proxy.setWinner(worker.getOwner().getNAME());
                    proxy.setStatus(GameState.TERMINATOR);
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

    public void checkChronusWin() {

        Map<String, String> gods = this.proxy.getGods();
        if (gods!=null && gods.containsValue(GodType.CHRONUS.getCapitalizedName()) && countCompleteTower()) {
            String winPlayer = "";
            for(String x : gods.keySet())
                if(gods.get(x).equals("Chronus")) winPlayer = x;

            proxy.setWinner(winPlayer);
            proxy.setStatus(GameState.TERMINATOR);
            proxy.updateProxy();
        }
    }

    /**
     * update the proxy board after every step
     */
    public void updateProxyBoard(){
        proxy.resetWorkers();

        proxy.setTurnPlayer(turnPlayer.getNAME());

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
     * @author Ravella Elia
     */
    public void endGame(){
        proxy.setWinner("Unexpected Game Over");
        proxy.setStatus(GameState.TERMINATOR);
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
