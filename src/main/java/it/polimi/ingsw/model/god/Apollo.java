package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Apollo extends God{

    private boolean hadMove = false;
    private boolean hadBuild = false;

    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board, "APOLLO");
    }

    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            if( cell.getHeight() == Height.DOME || worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) > 1 ){
                throw new IllegalMoveException();
            }else{
                if (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0){
                    board.moveWorker(worker, cell);
                } else{
                    throw new IllegalMoveException();
                }
            }
        }else{
            if (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0){
                Worker otherWorker = cell.getWorker();
                Cell actualCell = worker.getCurrentCell();
                board.moveWorker(worker, cell);
                board.forceWorker(otherWorker, actualCell);
            } else{
                throw new IllegalMoveException();
            }

        }



    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command != null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild && !hadWin) {
                        move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if ( hadMove && !hadBuild && !hadWin){
                        super.build(cell, false);
                        hadBuild = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }
    }

    /*// array cell composed by 2 cells, 1 for the move and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            move(worker, cells[0]);
            hadWin = board.checkWin(worker);
        } else{
            throw new NullPointerException();
        }

        // build
        if ( !hadWin ){
            if( cells[1] != null ){
                super.build(worker, cells[1], false);
            } else{
                throw new NullPointerException();
            }
        }

    }*/

}