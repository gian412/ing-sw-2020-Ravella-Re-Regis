package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Pan;
import it.polimi.ingsw.view.RemoteView;

public class Board {

    private BoardProxy proxy;
    private Cell[][] cells;
    private Player turnPlayer;
    private Worker hadWin;

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

    // cells' getter
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    // turnPlayer's getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player next){
        turnPlayer = next;
    }

    // canMoveUp's set and reset
    public void setCanMoveUp( boolean canMoveUp ){/*Player.canMoveUp = canMoveUp*/}

    /*// this method return true if the worker is able to move up
    public boolean ableToMoveUp( Worker worker ){return true;}*/

    public void build(Cell cell, boolean isDome){
        try{
            if(isDome){
                cell.setHeight(Height.DOME);
            }
            else{
                cell.buildFloor();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void moveWorker(Worker worker, Cell cell){

        Cell supportCell = worker.getCurrentCell();

        supportCell.setWorker(null);
        worker.setPreviousCell(worker.getCurrentCell());
        cell.setWorker(worker);
        worker.setCurrentCell(cell);
    }

    public void forceWorker(Worker worker, Cell cell){}

    // method that check if the worker had win after the last move
    public boolean checkWin(Worker worker){

        byte heightDifference = worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight());

        //check the win with and without Pan
        if (worker.getOwner().getDivinity().NAME.equals("PAN")){
            if ((heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference == -2){
                hadWin = worker;
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
