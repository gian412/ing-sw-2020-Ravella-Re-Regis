package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Pan extends  God {

    private boolean hadMove = false;
    private boolean hadBuild = false;

    // class constructor with the initialization of board using the super constructor
    public Pan(Board board) {
        super(board, "PAN");
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
                        super.build(worker, cell, false);
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
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // build
            if(cells[1] != null){
                super.build(worker, cells[1], false);
            } else{
                throw new NullPointerException();
            }
        }
    }*/
}
