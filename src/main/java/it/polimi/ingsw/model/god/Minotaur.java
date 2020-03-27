package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.Height;

public class Minotaur extends God {

    private boolean hadMove = false;
    private boolean hadBuild = false;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Minotaur(Board board) {
        super(board, "MINOTAUR");
    }

    /**
     * Get the direction between two cell
     *
     * This method return the distance between two cells passed like parameters
     *
     * @param firstCell is the cell from which the distance start
     * @param secondCell is the cell in which the distance finish
     * @return an array of two integer with the two coordinates x and y
     */
    // method that return the direction of the movement of the worker
    private int[] getDirection(Cell firstCell, Cell secondCell){

        int[] direction = new int[2];

        direction[0] = secondCell.X - firstCell.X;
        direction[1] = secondCell.Y - firstCell.Y;

        return direction;
    }

    /**
     * Move the worker
     *
     * Override of the method of the super-class. This method is made in order to use the power
     * of Minotaur, who can move in a cell occupied from another worker, forcing this worker to
     * move a cell forward, if it's free.
     *
     * @param worker is the worker you are moving
     * @param cell is the cell in which you're moving the worker
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void move(Worker worker, Cell cell) throws IllegalMoveException {
        if( cell.getWorker() == null ){
            super.move(worker, cell);
        } else{
            int[] direction = getDirection( worker.getCurrentCell(), cell );
            Cell nextCell =  board.getCell( cell.X + direction[0], cell.Y + direction[1] );
            if( nextCell.getWorker() != null && nextCell.getHeight() != Height.DOME){
                Worker otherWorker = cell.getWorker();
                super.move(worker, cell);
                board.forceWorker(otherWorker, nextCell);
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
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException {

        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuild && !hadWin){
                        move(worker, cell);
                        hadMove = true;
                        hadWin = board.checkWin(worker);
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (hadMove && !hadBuild && !hadWin){
                        build(cell, false);
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
