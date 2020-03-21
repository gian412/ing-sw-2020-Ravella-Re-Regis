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

    // permit to move a worker from a cell to an other
    public void moveWorker(Worker worker, Cell cell){

        Cell supportCell = worker.getCurrentCell();

        supportCell.setWorker(null);
        worker.setPreviousCell(worker.getCurrentCell());
        cell.setWorker(worker);
        worker.setCurrentCell(cell);
    }

    //permit to build in the selected cell
    public void buid(Cell cell, boolean isDome){

        if(isDome)
            cell.setHeight(Height.DOME);
        else
            switch (cell.getHeight()){
                case GROUND: cell.setHeight(Height.FIRST_FLOOR);
                break;
                case FIRST_FLOOR: cell.setHeight(Height.SECOND_FLOOR);
                break;
                case SECOND_FLOOR: cell.setHeight(Height.THIRD_FLOOR);
                break;
                case THIRD_FLOOR: cell.setHeight(Height.DOME);
                break;
            }

    }

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
