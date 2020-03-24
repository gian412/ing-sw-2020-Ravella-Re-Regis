package it.polimi.ingsw.model.god;

import controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Hephaestus extends God {

    private boolean hadMove;
    private boolean[] hadBuild = {false, false};
    private  Cell previousCell = null;

    // class constructor with the initialization of board using the super constructor
    public Hephaestus(Board board) {
        super(board, "HEPHAESTUS");
    }

    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild[0] && !hadBuild[1] && !hadWin){
                        super.move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadBuild[0] && !hadBuild[1] && !hadWin){
                        super.build(worker, cell, false);
                        previousCell = cell;
                        hadBuild[0] = true;
                        break;
                    } else if (hadMove && hadBuild[0] && !hadBuild[1] && !hadWin && previousCell!=null && (previousCell.equals(cell)) && cell.getHeight()!=Height.THIRD_FLOOR && cell.getHeight()!=Height.DOME){
                        super.build(worker, cell, false);
                        hadBuild[1] = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    throw new IllegalMoveException();
            }
        }
    }

    /*// array cell composed by 3 cells, 1 for the moves and 2 for the build
    @Override
    public void makeMove(Worker worker, Cell[] cells, boolean isDome) throws IllegalMoveException, NullPointerException {

        // move
        if( worker != null && cells[0] != null ){
            super.move(worker, cells[0]);
        } else{
            throw new NullPointerException();
        }

        if( !hadWin ){
            // first build
            if(cells[1] != null){
                super.build(worker, cells[1], false);
            } else{
                throw new NullPointerException();
            }

            // second build
            if( cells[2] != null ){
                if( cells[2] == cells[1] && cells[2].getHeight() != Height.THIRD_FLOOR ){
                    super.build(worker, cells[1], false);
                }else{
                    throw new IllegalMoveException();
                }
            }
        }

    }*/
}
