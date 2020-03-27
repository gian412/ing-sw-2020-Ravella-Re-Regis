package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Demeter extends God {

    private boolean hadMove = false;
    private boolean[] hadBuild = {false, false};
    private Cell previousCell = null;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Demeter(Board board) {
        super(board, "DEMETER");
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, the worker can build twice but not in the same cell
     *
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
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
                        super.build(cell, false);
                        previousCell = cell;
                        hadBuild[0] = true;
                        break;
                    } else if (hadMove && hadBuild[0] && !hadBuild[1] && !hadWin && previousCell!=null && !(previousCell.equals(cell))){
                        super.build(cell, false);
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

}
