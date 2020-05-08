package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
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
     * @param pair stands for the coordinates in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void move(Worker worker, Pair pair) throws IllegalMoveException {
        Cell cell = board.getCell(pair); // Get the reference to the cell
        if( cell.getWorker() == null ){
            try {
                super.move(worker, pair);
            } catch (IllegalMoveException e) {
                throw new IllegalMoveException(e.getMessage());
            }
        } else {
            Pair direction = worker.getCurrentCell().getDirection( cell );
            Cell nextCell =  board.getCell( new Pair( cell.X + direction.x, cell.Y + direction.y ) );
            if( nextCell.getWorker() == null && nextCell.getHeight() != Height.DOME){
                Worker otherWorker = cell.getWorker();
                try {
                    board.moveWorker(worker, pair);
                    board.moveWorker(otherWorker, new Pair(nextCell.X, nextCell.Y));
                } catch (IllegalMoveException e){
                    throw new IllegalMoveException(e.getMessage());
                }

            } else{
                throw new IllegalMoveException("Invalid MOVE parameters");
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

            switch (command.commandType){
                case MOVE:
                    if (!hasMoved && !hasBuild && !hasWon){
                        try {
                            this.move(worker, command.coordinates);
                            hasMoved = true;
                            hasWon = board.checkWin(worker);
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD:
                    if (hasMoved && !hasBuild && !hasWon){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else{
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case BUILD_DOME:

                    if (hasMoved && !hasBuild && !hasWon && board.getCell(command.coordinates).getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(worker.getCurrentCell(), command.coordinates, false);
                            hasBuild = true;
                            break;
                        } catch (IllegalMoveException e) {
                            throw new IllegalMoveException(e.getMessage());
                        }
                    } else {
                        throw new IllegalMoveException("Invalid command sequence");
                    }

                case RESET:
                    super.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException("Command type not valid for the current god");
            }
        } else{
            throw new NullPointerException("The passed command is null");
        }

    }

}
