package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

/**
 * Class Artemis, sub-class of the abstract class {@link it.polimi.ingsw.model.god.God}.
 * This class have the ability to make two moves, instead of one, before building.
 *
 * @see it.polimi.ingsw.model.god.God
 * @author Gianluca Regis
 */
public class Artemis extends God {

    protected boolean hasMovedSecond;
    protected Cell startingCell;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Artemis(Board board) {
        super(board, GodType.ARTEMIS);
        this.hasMovedSecond = false;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method is checked the possibility to move twice without returning
     * on the starting cell
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null){ // If the passed command isn't empty

            if (checkCell(command.coordinates) == null) {
                throw new IllegalMoveException("Invalid cell");
            }

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasMovedSecond && !hasBuild && !hasWon){ // If the player has not moved, moved second, build and won
                        try {
                            super.move(worker, command.coordinates); // Call super-class' move method
                            startingCell = worker.getPreviousCell(); // Save the starting position of the worker
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                            if (!hasWon && !canMove(worker) && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else if (hasMoved && !hasMovedSecond && !hasBuild && !hasWon && !(board.getCell(command.coordinates).equals(startingCell))){ // If the player has moved but has not moved second, build and won and the cell isn't equal to the starting cell
                        try {
                            super.move(worker, command.coordinates); // Call super-class' move method
                            hasMovedSecond = true; // Store the information that the worker has moved second
                            hasWon = board.checkWin(worker); // Check if the worker has win and store the result in hasWon
                            if (!hasWon && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e){
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasWon){ // If the player has moved but has not build and won
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false); // Call super-class' build method
                            hasBuild = true; // Store the information that the worker has build
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR){ // If the player has moved but has not build and won and cell's height is third floor
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false); // Call super-class' build method
                            hasBuild = true; // Store the information that the worker has build
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case RESET:
                    this.resetLocalVariables(); // Call Artemis' reset method
                    break;

                case CHECK_WORKERS:
                    if (worker.getCurrentCell()!=null && !canMove(worker)) {
                        board.removeWorker(worker);
                        worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                    }
                    break;

                default:
                    throw new IllegalMoveException("Command type not valid for the current god");
            }
        } else{
            throw new NullPointerException("The passed command is null");
        }
    }

    /**
     * Reset local variable for class Artemis using the super method and adding local variables
     *
     * @author Gianluca Regis
     */
    @Override
    protected void resetLocalVariables() {
        super.resetLocalVariables();
        this.hasMovedSecond = false;
        this.startingCell = null;
    }
}
