package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.exceptions.IllegalMoveException;

public class Hephaestus extends God {

    protected boolean hasBuildSecond;
    private  Cell previousCell;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Hephaestus(Board board) {
        super(board, "HEPHAESTUS");
        this.hasBuildSecond = false;
        this.previousCell = null;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, the worker can build twice in the same cell but not a dome
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasBuildSecond && !hasWon){
                        try {
                            super.move(worker, new Pair(command.cellX, command.cellY));
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasBuildSecond && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), new Pair(command.cellX, command.cellY), false);
                            previousCell = cell;
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if (hasMoved && hasBuild && !hasBuildSecond && !hasWon && previousCell!=null && (previousCell.equals(cell)) && cell.getHeight()!=Height.THIRD_FLOOR && cell.getHeight()!=Height.DOME){
                        try {
                            super.build(worker.getCurrentCell(), new Pair(command.cellX, command.cellY), false);
                            hasBuildSecond = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasBuildSecond && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), new Pair(command.cellX, command.cellY), false);
                            previousCell = cell;
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * Reset local variable for class Hephaestus using the super method and adding local variables
     *
     * @author Gianluca Regis
     */
    @Override
    protected void resetLocalVariables() {
        super.resetLocalVariables();
        this.hasBuildSecond = false;
        this.previousCell = null;
    }
}
