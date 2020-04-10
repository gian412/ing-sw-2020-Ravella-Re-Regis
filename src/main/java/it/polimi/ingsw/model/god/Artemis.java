package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

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
        super(board, "ARTEMIS");
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
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null){ // If the passed command isn't empty
            Cell cell = board.getCell(command.cellX, command.cellY); // Get the reference to the cell

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasMovedSecond && !hasBuild && !hasWon){ // If the player has not moved, moved second, build and win
                        try {
                            super.move(worker, cell); // Call super-class' move method
                            startingCell = cell; // Save the starting position of the worker
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker); // Check if the worker has win and store the result in hasWon
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if (hasMoved && !hasMovedSecond && !hasBuild && !hasWon && !(cell.equals(startingCell))){ // If the player has moved but has not moved second, build and win and the cell isn't equal to the starting cell
                        try {
                            super.move(worker, cell); // Call super-class' move method
                            hasMovedSecond = true; // Store the information that the worker has moved second
                            hasWon = board.checkWin(worker); // Check if the worker has win and store the result in hasWon
                            break;
                        } catch (IllegalMoveException e){
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasWon){ // If the player has moved but has not build and won
                        try {
                            super.build(worker.getCurrentCell(), cell, false); // Call super-class' build method
                            hasBuild = true; // Store the information that the worker has build
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){ // If the player has moved but has not build and won and cell's height is third floor
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
                    this.resetLocalVariables(); // Call Artemis' reset method
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
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
