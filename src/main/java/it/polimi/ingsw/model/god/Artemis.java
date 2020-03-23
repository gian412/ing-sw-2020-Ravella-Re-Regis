package it.polimi.ingsw.model.god;

import controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Artemis extends God {

    private boolean[] hadMove = {false, false};


    // class constructor with the initialization of board using the super constructor
    public Artemis(Board board) {
        super(board, "ARTEMIS");
    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command != null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove[0] && !hadWin){
                        super.move(worker, cell);
                        hadMove[0] = true;
                        hadWin = board.checkWin(worker);
                    } else if (!hadMove[1] && !hadWin){
                        super.move(worker, cell);
                        hadMove[1] = true;
                        hadWin = board.checkWin(worker);
                    } else{
                        throw new IllegalMoveException();
                    }
                    break;

                case BUILD:
                    if (hadMove[0] && !hadWin){
                        super.build(worker, cell, false);
                    } else{
                        throw new IllegalMoveException();
                    }
                    break;

                case BUILD_DOME:
                    throw new IllegalMoveException();
            }
        }
    }

    /*// array cell composed by 3 cells, 2 for the moves and 1 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // first move
        if( worker != null && cells[0] != null ){
            super.move( worker, cells[0] );
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            //Second move
            if( cells[1] != null ){
                if( cells[1].equals( worker.getPreviousCell() ) ){
                    throw new IllegalMoveException();
                } else{
                    super.move( worker, cells[1] );
                }
            }

            if( !hadWin ){
                // build
                if( cells[2] != null ){
                    super.build(worker, cells[2], false);
                } else{
                    throw new NullPointerException();
                }
            }
        }
    }*/

}
