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
}
