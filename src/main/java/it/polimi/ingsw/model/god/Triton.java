package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

/**
 * Class Triton, sub-class of the abstract class {@link it.polimi.ingsw.model.god.God}.
 * This class have the ability to move as many times as he wants if it is on the perimeter.
 *
 * @see it.polimi.ingsw.model.god.God
 * @author Gianluca Regis
 */
public class Triton extends God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Triton(Board board) {
        super(board, GodType.TRITON);
    }

    /**
     * Check if the given worker can move
     *
     * Override of the method of the super-class. This method check if there is a free neighbor perimeter cell
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
                if (cell!=null && cell.getWorker()==null && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<=1 && cell.getHeight()!=Height.DOME && cell.isPerimeter()) {
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
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null){

            if (checkCell(command.coordinates) == null) {
                throw new IllegalMoveException("Invalid cell");
            }

            switch (command.commandType){
                case MOVE:
                    //if (board.getCell(command.coordinates).isPerimeter() && !hasBuild && !hasWon){
                    if (worker.getCurrentCell().isPerimeter() && !hasBuild && !hasWon){
                        try {
                            super.move(worker, command.coordinates);
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            if (!hasWon && !this.canMove(worker) && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        if (!hasMoved && !hasBuild && !hasWon) {
                            try {
                                super.move(worker, command.coordinates);
                                hasMoved = true;
                                hasWon = board.checkWin(worker);
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException(e.getMessage());
                            }
                        } else {
                            throw new IllegalMoveException("Invalid command sequence");
                        }
                    }

                case BUILD:
                    if ( hasMoved && !hasBuild && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case RESET:
                    super.resetLocalVariables();
                    break;

                case CHECK_WORKERS:
                    if (worker.getCurrentCell()!=null && !super.canMove(worker)) {
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
