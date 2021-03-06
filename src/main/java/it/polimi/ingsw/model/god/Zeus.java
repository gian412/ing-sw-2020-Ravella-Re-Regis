package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

/**
 * Class Zeus, sub-class of the abstract class {@link it.polimi.ingsw.model.god.God}.
 * This class have the ability to build under itself.
 *
 * @see it.polimi.ingsw.model.god.God
 * @author Gianluca Regis
 */
public class Zeus extends God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Zeus(Board board) {
        super(board, GodType.ZEUS);
    }

    /**
     * Build a new piece in the cell passed as parameter using board.build(Cell cell, Pair coordinates, boolean isDome).
     * The method throw an IllegalMoveException if the piece can't be built in the given cell.
     * This Override is able to build a piece under the worker
     *
     * @author Gianluca Regis
     * @param pair stands for the coordinates in which you're building the new piece
     * @param isDome is true if Atlas build a dome in any position
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void build(Cell originCell, Pair pair, boolean isDome) throws IllegalMoveException {
        Cell buildCell = board.getCell(pair); // Get the reference to the cell
        // build
        if( buildCell.getHeight() != Height.THIRD_FLOOR && buildCell.getHeight() != Height.DOME && !isDome ){
            board.build(originCell, pair, false );
        } else{
            throw new IllegalMoveException("Invalid BUILD parameters");
        }
    }

    /**
     * Check if the given worker can build
     *
     * @author Gianluca Regis
     * @param worker The worker to check
     * @return true if it can move, false otherwise
     */
    @Override
    protected boolean canBuild(Worker worker) {
        Cell[][] neighbors = board.getNeighbors(worker.getCurrentCell());
        for (Cell[] row : neighbors) {
            for (Cell cell : row) {
                if (cell!=null && cell.getWorker()==null && cell.getHeight()!=Height.DOME ||
                        (cell!=null && cell==worker.getCurrentCell() && cell.getHeight()!=Height.THIRD_FLOOR && cell.getHeight()!=Height.DOME)) {
                    return true;
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
     * This method implement the ability to build a block under the worker
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null){

            Cell cell = checkCell(command.coordinates); // Get the reference to the cell

            if (cell == null) {
                throw new IllegalMoveException("Invalid cell");
            }

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon) {
                        try {
                            super.move(worker, command.coordinates);
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            if (!hasWon && !this.canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if (worker.getCurrentCell()!=cell){
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
                    } else {
                        if ( hasMoved && !hasBuild && !hasWon){
                            try {
                                this.build(cell, command.coordinates, false);
                                hasBuild = true;
                                board.checkChronusWin();
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException(e.getMessage());
                            }
                        } else{
                            throw new IllegalMoveException("Invalid command sequence");
                        }
                    }

                case BUILD_DOME:
                    if (hasMoved && !hasBuild && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){
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

}
