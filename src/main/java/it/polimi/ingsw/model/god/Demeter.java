package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Demeter extends God {

    private boolean hadBuildSecond;
    private Cell previousCell = null;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Demeter(Board board) {
        super(board, "DEMETER");
        this.hadBuildSecond = false;
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
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild && !hadBuildSecond && !hadWin){
                        super.move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadBuild && !hadBuildSecond && !hadWin){
                        super.build(cell, false);
                        previousCell = cell;
                        hadBuild = true;
                        break;
                    } else if (hadMove && hadBuild && !hadBuildSecond && !hadWin && previousCell!=null && !(previousCell.equals(cell))){
                        super.build(cell, false);
                        hadBuildSecond = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }

    }

    /**
     * Reset local variable for class Demeter using the super method and adding local variables
     */
    @Override
    public void resetLocalVariables() {
        super.resetLocalVariables();
        this.hadBuildSecond = false;
        this.previousCell = null;
    }
}
