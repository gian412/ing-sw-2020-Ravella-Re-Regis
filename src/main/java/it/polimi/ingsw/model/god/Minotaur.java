package it.polimi.ingsw.model.god;


import controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Minotaur extends God {

    private boolean hadMove = false;
    private boolean hadBuild = false;

    // class constructor with the initialization of board using the super constructor
    public Minotaur(Board board) {
        super(board, "MINOTAUR");
    }

    // method that return the direction of the movement of the worker
    private int[] getDirection(Cell firstCell, Cell secondCell){

        int[] direction = new int[2];

        direction[0] = secondCell.X - firstCell.X;
        direction[1] = secondCell.Y - firstCell.Y;

        return direction;
    }

    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            super.move(worker, cell);
        } else{
            int[] direction = getDirection( worker.getCurrentCell(), cell );
            Cell nextCell =  board.getCell( cell.X + direction[0], cell.Y + direction[1] );
            if( nextCell.getWorker() != null && nextCell.getHeight() != Height.DOME){
                Worker otherWorker = cell.getWorker();
                super.move(worker, cell);
                board.forceWorker(otherWorker, nextCell);
            } else{
                throw new IllegalMoveException();
            }
        }
    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild && !hadWin){
                        move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadBuild && !hadWin){
                        build(worker, cell, false);
                        hadBuild = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    throw new IllegalMoveException();
            }
        }

    }

    /*// array cell composed by 2 cells, 1 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            if( cells[0].getWorker() == null ){
                super.move(worker, cells[0]);
            } else{
                int[] direction = getDirection( worker.getCurrentCell(), cells[0] );
                Cell nextCell =  board.getCell( cells[0].X + direction[0], cells[0].Y + direction[1] );
                if( nextCell.getWorker() != null && nextCell.getHeight() != Height.DOME){
                    Worker otherWorker = cells[0].getWorker();
                    super.move(worker, cells[0]);
                    board.forceWorker(otherWorker, nextCell);
                } else{
                    throw new IllegalMoveException();
                }
            }
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // build
            if( cells[1] != null ){
                super.build(worker, cells[1], false );
            } else{
                throw new NullPointerException();
            }
        }
    }*/
}
