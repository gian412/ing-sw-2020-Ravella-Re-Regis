package it.polimi.ingsw.model.god;

import controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Prometheus extends God {
    private boolean hadMove = false;
    private boolean[] hadBuild = {false, false};

    // class constructor with the initialization of board using the super constructor
    public Prometheus(Board board) {
        super(board, "PROMETHEUS");
    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {
        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild[1] && !hadWin){
                        if ( hadBuild[0] && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<1 ){
                            super.move(worker, cell);
                            hadMove = true;
                            hadWin = board.checkWin(worker);
                            break;
                        } else{
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (!hadBuild[0] && !hadMove && !hadBuild[1] && !hadWin){
                        super.build(worker, cell, false);
                        hadBuild[0] = true;
                        break;
                    } else if(hadMove && !hadBuild[1] && !hadWin){
                        super.build(worker, cell, false);
                        hadBuild[1] = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }
            }
        }
    }

    /*// array cell composed by 2 cells, 1 for the moves and 2 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {



        // first build
        if( worker != null ){
            if( cells[0] != null ){
                if( worker.getCurrentCell().getHeight() == cells[1].getHeight() ){
                    super.build(worker, cells[0], false );
                } else{
                    throw new IllegalMoveException();
                }
            }
        } else{
            throw new NullPointerException();
        }

        // move
        if( cells[1] != null ){
            super.move(worker, cells[1]);
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // second build
            if( cells[2] != null ){
                super.build(worker, cells[2], false );
            } else{
                throw new NullPointerException();
            }
        }
    }*/
}
