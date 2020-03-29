package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.IllegalMoveException;
import it.polimi.ingsw.model.Worker;

public class Prometheus extends God {
    private boolean hadBuildSecond;

    /**
     * Class' constructor that use the super class' constructor
     *
     * @param board indicates the board of the game
     */
    // class constructor with the initialization of board using the super constructor
    public Prometheus(Board board) {
        super(board, "PROMETHEUS");
        this.hadBuildSecond = false;
    }

    /**
     * Actions made every turn
     *
     * Action made by the worker received by parameter. the possible moves are:
     *      1- Move using super.move(Worker worker, Cell cell)
     *      2- Build using super.build(Cell cell, boolean false)
     * In this method, the worker can build before and after the move if it
     * didn't move upward
     *
     * @param worker is the worker who is doing the actions
     * @param command is the command which need to be interpreted
     * @throws IllegalMoveException in case the action isn't legal
     */
    @Override
    public void makeMove(Worker worker, Command command) throws IllegalMoveException, NullPointerException {
        if (command!=null){
            Cell cell = board.getCell(command.cellX, command.cellY);

            switch (command.commandType){
                case MOVE:
                    if (!hadMove && !hadBuildSecond && !hadWin){
                        if ( hadBuild && worker.getCurrentCell().getHeight().getDifference(cell.getHeight())<1 ){
                            super.move(worker, cell);
                            hadMove = true;
                            hadWin = board.checkWin(worker);
                            break;
                        } else{
                            throw new IllegalMoveException();
                        }
                    } else{
                        throw new IllegalMoveException();
                    }

                case BUILD:
                    if (!hadBuild && !hadMove && !hadBuildSecond && !hadWin){
                        super.build(cell, false);
                        hadBuild = true;
                        break;
                    } else if(hadMove && !hadBuildSecond && !hadWin){
                        super.build(cell, false);
                        hadBuildSecond = true;
                        break;
                    } else{
                        throw new IllegalMoveException();
                    }

                case RESET:
                    this.resetLocalVariables();
                    break;

                default:
                    throw new IllegalMoveException();
            }
        } else{
            throw new NullPointerException();
        }
    }

    /**
     * Reset local variable for class Prometheus using the super method and adding local variables
     */
    @Override
    public void resetLocalVariables() {
        super.resetLocalVariables();
        this.hadBuildSecond = false;
    }
}
