package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public abstract class God {

    protected Board board;
    public final String NAME;
    protected boolean hadMove;
    protected boolean hadBuild;
    protected boolean hadWin;

    /**
     * Class' constructor with the initialization of boar and NAME
     *
     * @param board indicates the board of the game
     * @param NAME indicates the name of the god
     */
    // class constructor with the initialization of board
    public God(Board board, String NAME){
        this.board = board;
        this.NAME = NAME;
        this.hadMove = false;
        this.hadBuild = false;
        this.hadWin = false;
    }

    /**
     * Actions made every turn
     *
     * This abstract method was made in order to simulate actions made by any divinity in each turn.
     * The method will be implemented in every child class
     *
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    // abstract class' abstract method
    public abstract void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException;

    /**
     * Move the worker
     *
     * Move the worker using board.moveWorker(Worker worker, Cell cell) and then check if the worker had
     * win using board.checkWin(Worker worker) and saving this result in the class' variable hadWind.
     * The method throw an IllegalMoveException if the worker can't move in the given cell
     *
     * @param worker is the worker you are moving
     * @param cell is the cell in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    public void move(Worker worker, Cell cell) throws IllegalMoveException{

        if ( cell.getWorker() == null && cell.getHeight() != Height.DOME && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 1 ) {
            if( worker.isCanMoveUp() || (!worker.isCanMoveUp() && worker.getCurrentCell().getHeight().getDifference(cell.getHeight()) <= 0) ){
                //try {
                    board.moveWorker(worker, cell);
                    hadWin = board.checkWin(worker);
                //} catch (IllegalMoveException e){
                //    throw new IllegalMoveException();
                //}

            } else{
                throw new IllegalMoveException();
            }
        }else{
            throw new IllegalMoveException();
        }

    }

    /**
     * Build a new piece in the cell passed as parameter using board-build(Cell cell, boolean isDome).
     * The method throw an IllegalMoveException if the piece can't be built in the given cell
     *
     * @param cell is the cell in which you're building the new piece
     * @param isDome is true if Atlas build a dome in any position
     * @throws IllegalMoveException in case the move isn't legal
     */
    public void build(Cell cell, boolean isDome) throws IllegalMoveException {
        // build
        if( cell.getWorker() != null && cell.getHeight() != Height.DOME && isDome ){
            //try {
                board.build( cell, true );
            //} catch (IllegalMoveException e){
                throw new IllegalMoveException();
            //}
        }else if( cell.getWorker() != null && cell.getHeight() != Height.DOME && !isDome){
            //try{
                board.build( cell, false );
            //} catch (IllegalMoveException e) {
            //    throw new IllegalMoveException();
            //}
        } else{
            throw new IllegalMoveException();
        }
    }

    /**
     * Reset local variable for class God
     */
    protected void resetLocalVariables(){
        this.hadMove = false;
        this.hadBuild = false;
    }

}
