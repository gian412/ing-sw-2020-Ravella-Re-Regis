package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Pan extends  God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Pan(Board board) {
        super(board, "PAN");
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
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command != null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon) {
                        try {
                            super.move(worker, cell);
                            hasMoved = true;
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
                            super.build(worker.getCurrentCell(), cell, false);
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
                            super.build(worker.getCurrentCell(), cell, false);
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

}
