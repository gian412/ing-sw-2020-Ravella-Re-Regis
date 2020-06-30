package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.GodType;

public abstract class God {

    protected Board board;
    public final GodType NAME;
    protected boolean hasMoved;
    protected boolean hasBuild;
    protected boolean hasWon;

    /**
     * Class' constructor with the initialization of boar and NAME
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     * @param name indicates the name of the god
     */
    // class constructor with the initialization of board
    public God(Board board, GodType name){
        this.board = board;
        this.NAME = name;
        this.hasMoved = false;
        this.hasBuild = false;
        this.hasWon = false;
    }

    /**
     * Actions made every turn
     *
     * This abstract method was made in order to simulate actions made by any divinity in each turn.
     * The method will be implemented in every child class
     *
     * @author Gianluca Regis
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    // abstract class' abstract method
    public abstract void executeCommand(Worker worker, Command command) throws IllegalMoveException, NullPointerException;

    /**
     * Move the worker
     *
     * Move the worker using board.moveWorker(Worker worker, Cell cell) and then check if the worker has
     * win using board.checkWin(Worker worker) and saving this result in the class' variable hasWond.
     * The method throw an IllegalMoveException if the worker can't move in the given cell
     *
     * @author Gianluca Regis
     * @param worker is the worker you are moving
     * @param pair stands for the coordinates in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    protected void move(Worker worker, Pair pair) throws IllegalMoveException{

        Cell cell = board.getCell(pair); // Get the reference to the cell

        if ( cell.getWorker() == null && cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) { // If the cell isn't occupied and it isn't a dome and it isn't more then 1 floor far
            if( worker.isCanMoveUp() || (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0) ){ // If worker can move up or worker can't move up but the destination isn't up
                try {
                    board.moveWorker(worker, pair); // Call board's move method
                    hasWon = board.checkWin(worker); // Check if the worker has win and store the result in hasWon
                } catch (IllegalMoveException e){
                    throw new IllegalMoveException(e.getMessage());
                }
            } else{
                throw new IllegalMoveException("CanMoveUp parameter error");
            }
        }else{
            throw new IllegalMoveException("Invalid MOVE parameters");
        }

    }

    /**
     * Build a new piece in the cell passed as parameter using board-build(Cell cell, boolean isDome).
     * The method throw an IllegalMoveException if the piece can't be built in the given cell
     *
     * @author Gianluca Regis
     * @param pair stands for the coordinates in which you're building the new piece
     * @param isDome is true if Atlas build a dome in any position
     * @throws IllegalMoveException in case the move isn't legal
     */
    protected void build(Cell originCell, Pair pair, boolean isDome) throws IllegalMoveException {

        Cell buildCell = board.getCell(pair); // Get the reference to the cell

        // build
        if( buildCell.getWorker() == null && buildCell.getHeight() != Height.DOME && isDome ){ // If the cell isn't occupied and it isn't a dome and the piece to build is a dome
            try {
                board.build(originCell, pair, true ); // Call board's build method
            } catch (IllegalMoveException e){
                throw new IllegalMoveException(e.getMessage());
            }
        }else if( buildCell.getWorker() == null && buildCell.getHeight() != Height.DOME && !isDome){ // If the cell isn't occupied and it isn't a dome and the piece to build isn't a dome
            try{
                board.build(originCell, pair, false ); // Call board's build method
            } catch (IllegalMoveException e) {
                throw new IllegalMoveException(e.getMessage());
            }
        } else{
            throw new IllegalMoveException("Invalid BUILD parameters");
        }
    }

    /**
     * Reset local variable for class God
     *
     * @author Gianluca Regis
     */
    protected void resetLocalVariables(){
        this.hasMoved = false;
        this.hasBuild = false;
    }

    /**
     * Check if the given cell is a valid cell or if it is out of bound
     *
     * @author Gianluca Regis
     * @param coordinates The coordinates of the cell
     * @return The cell if the coordinates are valid, null otherwise
     */
    protected Cell checkCell(Pair coordinates) {
        try {
            Cell cell = board.getCell(coordinates); // Get the reference to the cell
            return cell;
        } catch (IndexOutOfBoundsException e) {
            return null;

        }
    }

    /**
     * Check if the given worker can move
     *
     * @author Gianluca Regis
     * @param worker The worker to check
     * @return true if it can move, false otherwise
     */
    protected boolean canMove(Worker worker) {
        return true;
    }

    /**
     * CHeck if the given worker can build
     *
     * @author Gianluca Regis
     * @param worker The worker to check
     * @return true if it can move, false otherwise
     */
    protected boolean canBuild(Worker worker) {
        return true;
    }

}
