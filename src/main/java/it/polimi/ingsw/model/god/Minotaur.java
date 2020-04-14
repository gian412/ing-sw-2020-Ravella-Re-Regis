package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Minotaur extends God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Minotaur(Board board) {
        super(board, "MINOTAUR");
    }

    /**
     * Move the worker
     *
     * Override of the method of the super-class. This method is made in order to use the power
     * of Minotaur, who can move in a cell occupied from another worker, forcing this worker to
     * move a cell forward, if it's free.
     *
     * @author Gianluca Regis
     * @param worker is the worker you are moving
     * @param cell is the cell in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            try {
                super.move(worker, cell);
            } catch (IllegalMoveException e) {
                throw new IllegalMoveException();
            }
        } else{
            Pair direction = worker.getCurrentCell().getDirection( cell );
            Cell nextCell =  board.getCell( cell.X + direction.x, cell.Y + direction.y );
            if( nextCell.getWorker() == null && nextCell.getHeight() != Height.DOME){
                Worker otherWorker = cell.getWorker();
                try {
                    board.moveWorker(worker, cell);
                    board.moveWorker(otherWorker, nextCell);
                } catch (IllegalMoveException e){
                    throw new IllegalMoveException();
                }

            } else{
                throw new IllegalMoveException();
            }
        }
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, the worker can move in a cell occupied by another worker forcing
     * this other worker to move a cell forward in the same direction, if it's fre.
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon){
                        try {
                            this.move(worker, cell);
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
                    if (hasMoved && !hasBuild && !hasWon){
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
