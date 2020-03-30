package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

import static it.polimi.ingsw.controller.CommandType.RESET;

public class Apollo extends God{

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Apollo(Board board) {
        super(board, "APOLLO");
    }

    /**
     * Move the worker
     *
     * Override of the method of the super-class. This method is made in order to use the power
     * of Apollo, who can exchange the position with another worker.
     *
     * @param worker is the worker you are moving
     * @param cell is the cell in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            if( cell.getHeight() == Height.DOME || worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) > 1 ){
                throw new IllegalMoveException();
            }else{
                if (worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0){
                    board.moveWorker(worker, cell);
                } else if (worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) >1 && worker.isCanMoveUp() ){
                    board.moveWorker(worker, cell);
                } else {
                    throw new IllegalMoveException();
                }
            }
        }else{
            if (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0){
                Worker otherWorker = cell.getWorker();
                Cell actualCell = worker.getCurrentCell();
                board.moveWorker(worker, cell);
                board.moveWorker(otherWorker, actualCell);
            } else{
                throw new IllegalMoveException();
            }

        }



    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using Apollo.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     *
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
                    if (!hadMove && !hadBuild && !hadWin) {
                        move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else {
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if ( hadMove && !hadBuild && !hadWin){
                        super.build(cell, false);
                        hadBuild = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD_DOME:
                    if (cell.getHeight() == Height.THIRD_FLOOR){
                        super.build(cell, false);
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