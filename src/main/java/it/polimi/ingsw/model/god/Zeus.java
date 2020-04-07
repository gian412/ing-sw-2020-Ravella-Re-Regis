package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.*;

public class Zeus extends God {

    /**
     * Class' constructor that use the super class' constructor
     *
     * @author Gianluca Regis
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Zeus(Board board) {
        super(board, "ZEUS");
    }

    /**
     * Build a new piece in the cell passed as parameter using board-build(Cell cell, boolean isDome).
     * The method throw an IllegalMoveException if the piece can't be built in the given cell.
     * This Override is able to build a piece under the worker
     *
     * @author Gianluca Regis
     * @param buildCell is the cell in which you're building the new piece
     * @param isDome is true if Atlas build a dome in any position
     * @throws IllegalMoveException in case the move isn't legal
     */
    @Override
    public void build(Cell originCell, Cell buildCell, boolean isDome) throws IllegalMoveException {
        // build
        if( buildCell.getHeight() != Height.THIRD_FLOOR && buildCell.getHeight() != Height.DOME && !isDome ){
            try {
                board.build(originCell, buildCell, false );
            } catch (IllegalMoveException e){
                throw new IllegalMoveException();
            }
        } else{
            throw new IllegalMoveException();
        }
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * This method implement the ability to build a block under the worker
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
                    if (!hadMoved && !hadBuild && !hadWin) {
                        try {
                            super.move(worker, cell);
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
                    if (worker.getCurrentCell()!=cell){
                        if ( hadMoved && !hadBuild && !hadWin){
                            try {
                                super.build(worker.getCurrentCell(), cell, false);
                                hadBuild = true;
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException();
                            }
                        } else{
                            throw new IllegalMoveException();
                        }
                    } else {
                        if ( hadMoved && !hadBuild && !hadWin){
                            try {
                                this.build(cell, cell, false);
                                hadBuild = true;
                                break;
                            } catch (IllegalMoveException e) {
                                throw new IllegalMoveException();
                            }
                        } else{
                            throw new IllegalMoveException();
                        }
                    }

                case BUILD_DOME:
                    if (cell.getHeight() == Height.THIRD_FLOOR){
                        try {
                            super.build(cell, cell, false);
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
