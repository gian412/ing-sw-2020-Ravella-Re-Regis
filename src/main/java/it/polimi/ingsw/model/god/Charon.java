package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.utils.GodType;


public class Charon extends God {

    protected boolean hasForced;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Charon(Board board){
        super(board, GodType.CHARON);
        hasForced = false;
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
     * This method give the possibility to force an opponent's worker to move in a cell
     * back to you
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
                case FORCE:
                    if(!hasForced && !hasMoved && !hasBuild && worker.getCurrentCell().cellDistance(new Pair(cell.X, cell.Y)) && cell.getWorker()!=null){

                        Pair directionOfWorker = worker.getCurrentCell().getDirection(cell);

                        try {
                            Cell forcedCell = board.getCell(new Pair(worker.getCurrentCell().X - directionOfWorker.x, worker.getCurrentCell().Y - directionOfWorker.y));
                            if ( forcedCell.getWorker()==null && forcedCell.getHeight()!=Height.DOME ){
                                board.forceWorker(cell.getWorker(),new Pair(forcedCell.X, forcedCell.Y));
                                hasForced = true;
                                if (!super.canMove(worker)) {
                                    board.removeWorker(worker);
                                    worker.setPreviousCell(null);
                                    worker.setCurrentCell(null);
                                }
                                break;
                            } else {
                                throw new IllegalMoveException("Invalid FORCE parameters");
                            }
                        } catch (IllegalMoveException | IndexOutOfBoundsException e){
                            throw new IllegalMoveException(e.getMessage());
                        }


                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon) {
                        try {
                            super.move(worker, command.coordinates);
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker);
                            if (!hasWon && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setPreviousCell(null);
                                worker.setCurrentCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if ( hasMoved && !hasBuild && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
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
                    if (!this.canMove(worker)) {
                        board.removeWorker(worker);
                        worker.setPreviousCell(null);
                        worker.setCurrentCell(null);
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
     * Reset local variable for class Charon using the super method and adding local variables
     *
     * @author Gianluca Regis
     */
    @Override
    protected void resetLocalVariables() {
        super.resetLocalVariables();
        this.hasForced = false;
    }
}
