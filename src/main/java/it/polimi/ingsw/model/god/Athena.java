package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Athena extends God{

    private boolean hadMove = false;
    private boolean hadBuild = false;

    // class constructor with the initialization of board using the super constructor
    public Athena(Board board) {
        super(board, "ATHENA");
    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command != null) {
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType) {
                case MOVE:
                    if (!hadMove && !hadBuild && !hadWin) {
                        if (!worker.isCanMoveUp()){
                            board.setCanMoveUp(true);
                        }
                        move(worker, cell);
                        if (worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight())>0){
                            board.setCanMoveUp(false);
                        }
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadBuild && !hadWin) {
                        super.build(worker, cell, false);
                        hadBuild = true;
                        break;
                    } else {
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

        // reset of the power of Athena
        if (worker != null){
            if( !(worker.isCanMoveUp()) ){
                board.setCanMoveUp( true );
            }
        } else{
            throw new NullPointerException();
        }

        // move
        if( cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // set the power of Athena
            if( worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight()) > 0 ){
                board.setCanMoveUp( false );
            }

            // build
            if(cells[1] != null){
                super.build(worker, cells[1], false);
            } else{
                throw new NullPointerException();
            }
        }
    }*/
}
