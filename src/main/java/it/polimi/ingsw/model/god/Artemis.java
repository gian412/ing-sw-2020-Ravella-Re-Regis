package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Artemis extends God {

    private boolean hadMoveSecond;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Artemis(Board board) {
        super(board, "ARTEMIS");
        this.hadMoveSecond = false;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method is checked the possibility to move twice without returning
     * on the starting cell
     *
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command != null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadWin && !hadBuild){
                        super.move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else if (!hadMoveSecond && !hadWin && !hadBuild){
                        super.move(worker, cell);
                        hadMoveSecond = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadWin && !hadBuild){
                        super.build(cell, false);
                        hadBuild = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    throw new IllegalMoveException();
            }
        }
    }

}
