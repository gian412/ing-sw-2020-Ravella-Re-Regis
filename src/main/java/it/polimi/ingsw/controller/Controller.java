package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalAddException;
import it.polimi.ingsw.exceptions.IllegalCellException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.view.Observer;

import java.io.IOException;


public class Controller implements Observer<PlayerCommand>, Runnable {

    private Game game;

    public Controller(Game g){
        this.game = g;
    }

    /**actually modifies the mode
     *
     * this function passes the "command" from the user to the divinity, that then modifie the Board accordingly
     *
     *
     * @author Elia Ravella
     * @param player the player in control of the (Remote)View
     * @param command the operation the player is wishing to do
     * @param workerID the number representing the worker (0, 1)
     */
    public void commitCommand(String player, Command command, int workerID){
        if(game.getTurnPlayer().getNAME().equals(player)) {
            try {
                game.getTurnPlayer().getDivinity().executeCommand(
                        game.getTurnPlayer().getWorkers()[workerID],
                        command
                );
            } catch (IllegalMoveException exc) {
                game.getBoard().notifyIllegalMove(exc.getMessage());
            } catch (NullPointerException exc) {
                exc.printStackTrace();
            }
        }
    }

    /**adds a worker to the board
     *
     * @author Elia Ravella
     * @param row the x axis coordinate to be added
     * @param column the y axis coordinate to be added
     */
    public void addWorker(int row, int column){
        try {
            game.getBoard().addWorker(new Pair(row, column));
        } catch (IllegalCellException | IllegalAddException e) {
            game.getBoard().notifyIllegalMove(e.getMessage());
        }
    }

    /**adds the player to the game
     *
     * this function is called from the RemoteView at the beginning of the game, before the
     * startGame() method is invoked
     *
     * @author Elia Ravella
     * @param playerName the player's name
     * @param age the player's age
     */
    public void addPlayer(String playerName, int age){
        game.addPlayer(playerName, age);
    }

    /**Changes the player controlling the board
     *
     * @author Elia Ravella
     */
    public void changeTurnPlayer(){
        try{
            game.getTurnPlayer().getDivinity().executeCommand(
                    null,
                    new Command(new Pair(0, 0), CommandType.RESET)
            );
        }catch(IllegalMoveException | NullPointerException x){
            System.err.println(x.getMessage() + " controller generated");
        } finally {
            game.getTurnPlayer().setTurnPlayer(false);
            game.getBoard().changeTurnPlayer();
            game.getTurnPlayer().setTurnPlayer(true);
        }

    }

    /**starts the game
     *
     * @author Elia Ravella
     */
    public void startGame(){
        game.startGame();
    }

    public Player getTurnPlayer(){
        return this.game.getTurnPlayer();
    }

    @Override
    public void run() {

    }

    /**
     * the method update is triggered by a remoteview change. it decodes the message from the client
     * and executes accordingly
     * @param message the update content
     */
    @Override
    public void update(PlayerCommand message) {
        if(message == null ) throw new IllegalArgumentException();

        // procedure to handle disconnected player
        if(message.cmd.commandType == CommandType.DISCONNECTED){
            game.endGame();
            return;
        }

        if(message.cmd.commandType == CommandType.SET_GODS){
            game.getBoard().setChoosingGods(message.getMessage());
        }
        else{
            // in-game operations
            if(message.cmd.commandType == CommandType.CHANGE_TURN)
                changeTurnPlayer();
            else if(message.cmd.commandType == CommandType.ADD_WORKER){
                addWorker(
                        message.cmd.coordinates.x,
                        message.cmd.coordinates.y
                );
            }
            else {
                commitCommand(
                        message.player.getNAME(),
                        message.getCommand(),
                        message.getWorkerIndex()
                );
            }
        }
    }
}
