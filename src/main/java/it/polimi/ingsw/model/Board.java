package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Pan;
import it.polimi.ingsw.view.RemoteView;

public class Board {

    private BoardProxy proxy;
    private Cell[][] cells;
    private Player turnPlayer;
    private Player hadWin;

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
        hadWin = null;
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
     * return a specific cell ofthe board
     *
     * @param row is the index of the matrix for the row
     * @param column is the index of the matrix for the column
     * @return the cell identified by the two parameters
     */
    // cells' getter
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    // turnPlayer's getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player player) {
        this.turnPlayer = player;
    }


    public void changeTurnPlayer(){
        turnPlayer = turnPlayer.getNextPlayer();
    }

    /**
     * build a structure on the board
     *
     * @param cell cell in which the player wants to build
     * @param isDome is true if a god, who has the ability to build dome not only after the third level, build a dome
     */
    public void build(Cell cell, boolean isDome) throws IllegalMoveException{

        if((cell.X >= 0) && (cell.X < 5) && (cell.Y >= 0) && (cell.Y < 5)){
            if(isDome){
                if(this.getCell(cell.X, cell.Y).getHeight() == Height.THIRD_FLOOR) {
                    this.getCell(cell.X, cell.Y).setHeight(Height.DOME);
                    this.getCell(cell.X, cell.Y).setIsCompleted();
                }
                else{
                    this.getCell(cell.X, cell.Y).setHeight(Height.DOME);
                }
            }
            else {
                this.getCell(cell.X, cell.Y).buildFloor();
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
     * @param cell the in in which the player moves the worker
     */
    public void moveWorker(Worker worker, Cell cell) throws IllegalMoveException{

        if((cell.X >= 0) && (cell.X < 5) && (cell.Y >= 0) && (cell.Y < 5) && (worker.getCurrentCell().cellDistance(cell))){

            this.getCell(worker.getCurrentCell().X, worker.getCurrentCell().Y).setWorker(null);
            worker.setPreviousCell(this.getCell(worker.getCurrentCell().X, worker.getCurrentCell().Y));

            this.getCell(cell.X, cell.Y).setWorker(worker);
            worker.setCurrentCell(this.getCell(cell.X, cell.Y));

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
     * @param cell the cell in which the player moves the worker
     */
    public void forceWorker(Worker worker, Cell cell) throws IllegalMoveException{
        if((cell.X >= 0) && (cell.X < 5) && (cell.Y >= 0) && (cell.Y < 5)){

            this.getCell(worker.getCurrentCell().X, worker.getCurrentCell().Y).setWorker(null);
            worker.setPreviousCell(this.getCell(worker.getCurrentCell().X, worker.getCurrentCell().Y));

            this.getCell(cell.X, cell.Y).setWorker(worker);
            worker.setCurrentCell(this.getCell(cell.X, cell.Y));

            //update the proxyBoard after a legal move
            this.updateProxyBoard();
        }
        else{
            throw new IllegalMoveException();
        }
    }

    /** put a worker on the board
     *
     * at the start of the game the player put his workers on the board
     *
     * @param row is the row of the board in which the player inserts his worker
     * @param column is the column of the board in which the player inserts his worker
     * @throws IllegalCellException,IllegalAddException
     * illegalCellException is the cell doesn't exist
     * illegalAddException if the player has already put his two workers
     */
    public void addWorker(int row, int column) throws IllegalCellException, IllegalAddException{

        // check if the two workers are alredy set with the first cell
        if( (this.turnPlayer.getWorkers()[0].getCurrentCell() == null) || (this.turnPlayer.getWorkers()[1].getCurrentCell() == null)) {
            // check if the cell where the player wants to put the workers exists and is free
            if( (this.getCell(row, column).getHeight() == Height.GROUND) && (this.getCell(row, column).getWorker() == null) && (row >= 0) && (row < 5) && (column >= 0) && (column < 5)) {
                //check if th first worker is already set
                if(this.turnPlayer.getWorkers()[0].getCurrentCell() == null){
                    //add the first worker
                    this.getCell(row, column).setWorker(this.turnPlayer.getWorkers()[0]);
                    this.turnPlayer.getWorkers()[0].setCurrentCell(this.getCell(row, column));
                }

                else{
                    //add the second worker
                    this.getCell(row, column).setWorker(this.turnPlayer.getWorkers()[1]);
                    this.turnPlayer.getWorkers()[0].setCurrentCell(this.getCell(row, column));
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
    // method that check if the worker had win after the last move
    public boolean checkWin(Worker worker){

        if (worker.getPreviousCell()!=null){
            byte heightDifference = worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight());

            //check the win with and without Pan
            if (worker.getOwner().getDivinity().NAME.equals("PAN")){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference <= -2){
                    hadWin = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else{
                    return false;
                }

            } else if (worker.getOwner().getDivinity().NAME.equals("CHRONUS")){
                if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || countCompleteTower()){
                    hadWin = worker.getOwner();
                    proxy.setWinner(worker.getOwner());
                    proxy.updateProxy();
                    return true;
                } else {
                    return false;
                }
            } else{
                if (heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR){
                    hadWin = worker.getOwner();
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
                    hadWin = worker.getOwner();
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
