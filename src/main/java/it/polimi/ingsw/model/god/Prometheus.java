package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

public class Prometheus extends God {
    protected boolean hasBuildBefore;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Prometheus(Board board) {
        super(board, GodType.PROMETHEUS);
        this.hasBuildBefore = false;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, the worker can build before and after the move if it
     * didn't move upward
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {
        if (command!=null){

            if (checkCell(command.coordinates) == null) {
                throw new IllegalMoveException("Invalid cell");
            }

            switch (command.commandType){
                case MOVE:
                    if (!hasBuildBefore && !hasMoved && !hasBuild && !hasWon){

                        try {
                            super.move(worker, command.coordinates);
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            if (!hasWon && !canBuild(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }

                    } else if (hasBuildBefore && !hasMoved && !hasBuild && !hasWon) {
                        if (!worker.isCanMoveUp()) {
                            try {
                                super.move(worker, command.coordinates);
                                hasMoved = true;
                                hasWon = board.checkWin(worker);
                                if (!hasWon && !canBuild(worker)) {
                                    board.removeWorker(worker);
                                    worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                                }
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException(e.getMessage());
                            }
                        } else {
                                worker.setCanMoveUp(false);
                            try {
                                super.move(worker, command.coordinates);
                                hasMoved = true;
                                worker.setCanMoveUp(true); // reset canMoveUp parameter
                                hasWon = board.checkWin(worker);
                                if (!hasWon && !canBuild(worker)) {
                                    board.removeWorker(worker);
                                    worker.setCurrentCell(null);
                                    worker.setPreviousCell(null);
                                }
                                break;
                            } catch (IllegalMoveException e) {
                                worker.setCanMoveUp(true); // reset canMoveUp parameter
                                throw new IllegalMoveException(e.getMessage());
                            }
                        }

                    }else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if ( !hasBuildBefore && !hasMoved && !hasBuild && !hasWon ){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuildBefore = true;
                            if (!canMove(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else if ( hasMoved && !hasBuild && !hasWon ){
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

                case BUILD_DOME:
                    if ( !hasBuildBefore && !hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuildBefore = true;
                            if (!canMove(worker)) {
                                board.removeWorker(worker);
                                worker.setCurrentCell(null);
                        worker.setPreviousCell(null);
                            }
                            board.checkChronusWin();
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else if ( hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR ){
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
                    this.resetLocalVariables();
                    break;

                case CHECK_WORKERS:
                    if (worker.getCurrentCell()!=null && !canMove(worker) && !canBuild(worker)) {
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
     * Reset local variable for class Prometheus using the super method and adding local variables
     *
     * @author Gianluca Regis
     */
    @Override
    protected void resetLocalVariables() {
        super.resetLocalVariables();
        this.hasBuildBefore = false;
    }

}
