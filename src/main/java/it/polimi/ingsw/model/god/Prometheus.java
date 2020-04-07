package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Prometheus extends God {
    protected boolean hadBuildBefore;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Prometheus(Board board) {
        super(board, "PROMETHEUS");
        this.hadBuildBefore = false;
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
                    if (!hadMoved && !hadBuild && !hadWin && (!hadBuildBefore || (hadBuildBefore &&
                            worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight())<1))){

                        try {
                            super.move(worker, cell);
                            hadMoved = true;
                            hadWin = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }

                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if ( !hadBuildBefore && !hadMoved && !hadBuild && !hadWin ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hadBuildBefore = true;
                        break;
                    } else if ( hadMoved && !hadBuild && !hadWin ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hadBuild = true;
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if ( !hadBuildBefore && !hadMoved && !hadBuild && !hadWin && cell.getHeight() == Height.THIRD_FLOOR){
                        super.build(worker.getCurrentCell(), cell, false);
                        hadBuildBefore = true;
                        break;
                    } else if ( hadMoved && !hadBuild && !hadWin && cell.getHeight() == Height.THIRD_FLOOR ){
                        super.build(worker.getCurrentCell(), cell, false);
                        hadBuild = true;
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
                    if (!hadMoved && !hadBuildSecond && !hadWin){
                        if ( hadBuild && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<1 ){
                            try {
                                super.move(worker, cell);
                                hadMoved = true;
                                hadWin = board.checkWin(worker);
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
                    if (!hadBuild && !hadMoved && !hadBuildSecond && !hadWin){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if(hadMoved && !hadBuildSecond && !hadWin){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuildSecond = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (!hadBuild && !hadMoved && !hadBuildSecond && !hadWin && cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else if(hadMoved && !hadBuildSecond && !hadWin && cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuildSecond = true;
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
        this.hadBuildBefore = false;
    }
}
