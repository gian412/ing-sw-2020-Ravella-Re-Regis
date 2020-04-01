package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Pan;
import it.polimi.ingsw.view.RemoteView;

public class Board {

    private BoardProxy proxy;
    private Cell[][] cells;
    private Player turnPlayer;
    private Worker hadWin;

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
        proxy = new BoardProxy();
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

    public BoardProxy getProxy() {
        return proxy;
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

    // canMoveUp's set and reset
    public void setCanMoveUp( boolean canMoveUp ){/*Player.canMoveUp = canMoveUp*/}


    // this method return true if the worker is able to move up
    public boolean ableToMoveUp( Worker worker ){return true;}

    /**
     * build a structure on the board
     *
     * @param cell cell in which the player wants to build
     * @param isDome is true if a god, who has the ability to build dome not only after the third level, build a dome
     */
    public void build(Cell cell, boolean isDome) throws IllegalMoveException{

        if((cell.X >= 0) && (cell.X < 5) && (cell.Y >= 0) && (cell.Y < 5)){
            if(isDome){
                cell.setHeight(Height.DOME);

                //update proxyBoard after a legal construction
                this.updateProxyBoard();
            }
            else {
                cell.buildFloor();

                //update proxyBoard after a legal construction
                this.updateProxyBoard();
            }
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
     * check the winning condition
     *
     * @author Gial+nluca regis
     * @param worker the worker that the player have just moved
     * @return true if the player wins, false if the player doesn't win
     */
    // method that check if the worker had win after the last move
    public boolean checkWin(Worker worker){

        byte heightDifference = worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight());

        //check the win with and without Pan
        if (worker.getOwner().getDivinity().NAME.equals("PAN")){
            if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference == -2){
                hadWin = worker;
                proxy.setWinner(worker.getOwner());
                proxy.updateProxy();
                return true;
            } else{
                return false;
            }

        } else{
            if (heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR){
                hadWin = worker;
                return true;
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
