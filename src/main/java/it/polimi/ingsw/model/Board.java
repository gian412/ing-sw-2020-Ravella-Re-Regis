package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Pan;

public class Board {

    private Cell[][] cells;
    private Player turnPlayer;

    // class constructor with the initialization of cells
    public Board(){
        cells = new Cell[5][5];
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                cells[i][j] = new Cell(i, j);
                cells[i][j].setHeight(Height.GROUND);
            }
        }
    }

    // cells' getter
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    // turnPlayer's getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    // canMoveUp's set and reset
    public void setCanMoveUp( boolean canMoveUp ){/*Player.canMoveUp = canMoveUp*/}

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
        if ( worker.getOwner().divinity.NAME.equals("PAN") ){
            return (heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR) || heightDifference == -2;
        } else{
            return heightDifference == 1 && worker.getCurrentCell().getHeight() == Height.THIRD_FLOOR;
        }

    }

    @Override
    public String toString() {
        StringBuilder myBoard = new StringBuilder();

        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++)
                myBoard.append(cells[row][col].toString().charAt(0));
            myBoard.append('\n');
        }

        return myBoard.toString();
    }
}
