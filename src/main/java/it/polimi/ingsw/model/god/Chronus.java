package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;

public class Chronus extends God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Chronus(Board board) {
        super(board, "CHRONUS");
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
            hasWon = board.checkWin(worker);
            if(!hasWon){

                switch (command.commandType){
                    case MOVE:
                        if (!hasMoved && !hasBuild) {
                            try {
                                super.move(worker, new Pair(command.cellX, command.cellY));
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
                        if (hasMoved && !hasBuild){
                            try {
                                super.build(worker.getCurrentCell(), new Pair(command.cellX, command.cellY), false);
                                hasBuild = true;
                                hasWon = board.checkWin(worker);
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException();
                            }
                        } else{
                            throw new IllegalMoveException();
                        }

                    case BUILD_DOME:
                        Cell cell = board.getCell(command.cellX, command.cellY); // Get the reference to the cell
                        if (cell.getHeight() == Height.THIRD_FLOOR){
                            try {
                                super.build(worker.getCurrentCell(), new Pair(command.cellX, command.cellY), false);
                                hasBuild = true;
                                hasWon = board.checkWin(worker);
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
            }
        } else{
            throw new NullPointerException();
        }

    }

}
