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

        if (command != null) {
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType) {
                case MOVE:
                    if (!hadMoved && !hadBuild && !hadWin) {
                        if (!worker.isCanMoveUp()){
                            worker.setCanMoveUp(true);
                        }
                        try {
                            super.move(worker, cell);
                            if (worker.getPreviousCell().getHeight().getDifference(worker.getCurrentCell().getHeight())>0){
                                worker.setCanMoveUp(false);
                            }
                            hadMoved = true;
                            hadWin = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMoved && !hadBuild && !hadWin) {
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException();
                        }
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), cell, false);
                            hadBuild = true;
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

}
