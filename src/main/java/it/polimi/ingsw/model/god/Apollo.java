package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

/**
 * Class Apollo, sub-class of the abstract class {@link it.polimi.ingsw.model.god.God}.
 * This class have the ability to make a swap whit another worker while moving.
 *
 * @see it.polimi.ingsw.model.god.God
 * @author Gianluca Regis
 */
public class Apollo extends God{

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board, GodType.APOLLO);
    }

    /**
     * Move the worker
     *
     * Override of the method of the super-class. This method is made in order to use the power
     * of Apollo, who can exchange the position with another worker.
     *
     * @author Gianluca Regis
     * @param worker is the worker you are moving
     * @param pair stands for the coordinates in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    protected void move(Worker worker, Pair pair) throws IllegalMoveException {
        Cell cell = board.getCell(pair); // Get the reference to the cell

        if( cell.getWorker() == null ){ // If worker can move without forcing anyone
            if ( cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) { // If the cell isn't a dome and it isn't more then 1 floor far
                if( worker.isCanMoveUp() || (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0) ){ // If worker can move up or worker can't move up but the destination isn't up
                    try {
                        board.moveWorker(worker, pair); // Call board's move method
                        hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                    } catch (IllegalMoveException e){
                        throw new IllegalMoveException(e.getMessage());
                    }
                } else {
                    throw new IllegalMoveException("CanMoveUp parameter error");
                }
            }else{
                throw new IllegalMoveException("Invalid MOVE parameters");
            }
        }else{ // worker has to force the otherWorker to exchange position with him
            if ( cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) { // If the cell isn't a dome and it isn't more then 1 floor far
                if( worker.isCanMoveUp() || (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0) ){ // If worker can move up or worker can't move up but the destination isn't up
                    try {
                        Worker otherWorker = cell.getWorker(); // Get the reference to the worker to force
                        board.switchWorkers(worker, otherWorker);
                        hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                    } catch (IllegalMoveException e){
                        throw new IllegalMoveException(e.getMessage());
                    }

                } else{
                    throw new IllegalMoveException("CanMoveUp parameter error");
                }
            }else{
                throw new IllegalMoveException("Invalid move parameters");
            }

        }



    }

    /**
     * Check if the given worker can move
     *
     * Override of the method of the super-class. This method don't check the presence of workers in the neighbors
     * because Apollo can force them.
     *
     * @author Gianluca Regis
     * @param worker The worker to check
     * @return true if it can move, false otherwise
     */
    @Override
    protected boolean canMove(Worker worker) {
        Cell[][] neighbors = board.getNeighbors(worker.getCurrentCell());
        for (Cell[] row : neighbors) {
            for (Cell cell : row) {
                if (cell!=null && cell!=worker.getCurrentCell() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<=1 && cell.getHeight()!=Height.DOME) {
                    if (worker.isCanMoveUp()) {
                        return true;
                    }

                    return (worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <=0 );
                }
            }
        }
        return false;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using Apollo.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
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
                    if (!hasMoved && !hasBuild && !hasWon) { // If the player has not move, build and won
                        try {
                            this.move(worker, command.coordinates); // Call Apollo's move method
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker); // Check if the worker has won and store the result in hasWon
                            if (!hasWon && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e){
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if ( hasMoved && !hasBuild && !hasWon){ // If the player has moved but has not build and won
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
                    if (hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR){ // If the player has moved but has not build and won and cell'height is third floor
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
                    super.resetLocalVariables(); // Call super-class' reset method
                    break;

                case CHECK_WORKERS:
                    if (worker.getCurrentCell()!=null && !this.canMove(worker)) {
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

}