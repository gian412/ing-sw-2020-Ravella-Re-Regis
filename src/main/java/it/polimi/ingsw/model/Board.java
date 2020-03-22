package it.polimi.ingsw.model;

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
    public void setFalseAllCanMoveUp(){/*canMoveUp = false*/}
    public void setTrueAllCanMoveUp(){/*canGoUp = true*/}


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

    @Override
    public String toString() {
        String myBoard = "";

        for(int row = 0; row < 5; row++){
            for(int col = 0; col < 5; col++)
                myBoard += cells[row][col].toString().charAt(0);
            myBoard += '\n';
        }

        return myBoard;
    }
}
