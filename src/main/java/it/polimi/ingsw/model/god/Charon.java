package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Height;


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
        super(board, "CHARON");
        hasForced = false;
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
            Cell cell = board.getCell(command.coordinates); // Get the reference to the cell

            switch (command.commandType){
                case FORCE:
                    if(!hasForced && !hasMoved && !hasBuild && worker.getCurrentCell().cellDistance(new Pair(cell.X, cell.Y)) && cell.getWorker()!=null){

                        Pair directionOfWorker = worker.getCurrentCell().getDirection(cell);
                        Cell forcedCell = board.getCell(new Pair(worker.getCurrentCell().X - directionOfWorker.x, worker.getCurrentCell().Y - directionOfWorker.y));
                        if ( forcedCell.getWorker()==null && forcedCell.getHeight()!=Height.DOME ){
                            try {
                                board.forceWorker(cell.getWorker(),new Pair(forcedCell.X, forcedCell.Y));
                                hasForced = true;
                                break;
                            } catch (IllegalMoveException e){
                                throw new IllegalMoveException();
                            }
                        } else {
                            throw new IllegalMoveException();
                        }

                    } else {
                        throw new IllegalMoveException();
                    }

                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon) {
                        try {
                            super.move(worker, command.coordinates);
                            hasMoved = true; // Store the information that the worker has moved
                            hasWon = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if ( hasMoved && !hasBuild && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case RESET:
                    super.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
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
