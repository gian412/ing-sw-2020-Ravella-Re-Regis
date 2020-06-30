package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.utils.GodType;

public class Hephaestus extends God {

    protected boolean hasBuildSecond;
    protected   Cell previousCell;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Hephaestus(Board board) {
        super(board, GodType.HEPHAESTUS);
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

        if (command!=null){ // If the passed command isn't empty

            Cell cell = checkCell(command.coordinates); // Get the reference to the cell

            if (cell == null) {
                throw new IllegalMoveException("Invalid cell");
            }

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasBuildSecond && !hasWon){ // If the player has not move, build, build second and won
                        try {
                            super.move(worker, command.coordinates); // Call super-class' move method
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                            if (!hasWon && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setPreviousCell(null);
                                worker.setCurrentCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasBuildSecond && !hasWon){ // If the player has moved but has not build, build second and won
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false); // Call super-class' build method
                            previousCell = cell; // Save the position in which the player has build the first time
                            hasBuild = true; // Store the information that the worker has build
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else if (hasMoved && hasBuild && !hasBuildSecond && !hasWon && previousCell!=null && (previousCell.equals(cell)) && cell.getHeight()!=Height.THIRD_FLOOR && cell.getHeight()!=Height.DOME){
                        // If the player has moved and build but has not build second and won and previousCell is equal to cell and cell in not THIRD_FLOOR or DOME
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false); // Call super-class' build method
                            hasBuildSecond = true; // Store the information that the worker has build second
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasBuildSecond && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){ // If the player has moved but has not build and won and cell's height is third floor
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false); // Call super-class' build method
                            previousCell = cell; // Save the position in which the player has build the first time
                            hasBuild = true; // Store the information that the worker has build
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                case CHECK_WORKERS:
                    if (!canMove(worker)) {
                        board.removeWorker(worker);
                        worker.setPreviousCell(null);
                        worker.setCurrentCell(null);
                    }

                default:
                    throw new IllegalMoveException("Command type not valid for the current god");
            }
        } else {
            throw new NullPointerException("The passed command is null");
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
