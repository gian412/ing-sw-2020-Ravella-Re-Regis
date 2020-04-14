package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

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
        super(board, "PROMETHEUS");
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
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {
        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon && (!hasBuildBefore || worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight())<1)){

                        try {
                            super.move(worker, cell);
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }

                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if ( !hasBuildBefore && !hasMoved && !hasBuild && !hasWon ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hasBuildBefore = true;
                        break;
                    } else if ( hasMoved && !hasBuild && !hasWon ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hasBuild = true;
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if ( !hasBuildBefore && !hasMoved && !hasBuild && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){
                        super.build(worker.getCurrentCell(), cell, false);
                        hasBuildBefore = true;
                        break;
                    } else if ( hasMoved && !hasBuild && !hasWon && cell.getHeight() == Height.THIRD_FLOOR ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hasBuild = true;
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }
    }

    /*public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {
        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuildSecond && !hasWon){
                        if ( hasBuild && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<1 ){
                            try {
                                super.move(worker, cell);
                                hasMoved = true;
                                hasWon = board.checkWin(worker);
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException();
                            }
                        } else{
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (!hasBuild && !hasMoved && !hasBuildSecond && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if(hasMoved && !hasBuildSecond && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hasBuildSecond = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (!hasBuild && !hasMoved && !hasBuildSecond && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if(hasMoved && !hasBuildSecond && !hasWon && cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hasBuildSecond = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }
    }*/

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
