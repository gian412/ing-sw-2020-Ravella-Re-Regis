package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Athena extends God{

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Athena(Board board) {
        super(board, "ATHENA");
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, if the worker move upwards a static variable in worker is setted
     * to false in order to prevent other player to move upward in the next turn
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null) { // If the passed command isn't empty
            Cell cell = board.getCell(command.cellX, command.cellY); // Get the reference to the cell

            switch (command.commandType) {
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon) { // If the player has not move, build and won
                        if (!worker.isCanMoveUp()){ // If the worker can't move up...
                            worker.setCanMoveUp(true); // ... reset the action of Athena's power
                        }
                        try {
                            super.move(worker, cell); // Call super-class' move method
                            if (worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight())>0){ // If the worker has moved up
                                worker.setCanMoveUp(false); // Set the canMoveUp parameter to false
                            }
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasWon) { // If the player has moved but has not build and won
                        try {
                            super.build(worker.getCurrentCell(), cell, false); // Call super-class' build method
                            hasBuild = true; // Store the information that the worker has build
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){ // If the player has moved but has not build and won and cell'height isn't third floor
                        try {
                            super.build(worker.getCurrentCell(), cell, false); // Call super-class' build method
                            hasBuild = true; // Store the information that the worker has build
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case RESET:
                    super.resetLocalVariables(); // Call super-class' reset method
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }
    }

}
