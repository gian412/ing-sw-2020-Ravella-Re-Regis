package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;

import it.polimi.ingsw.view.Observable;
import it.polimi.ingsw.view.Observer;

public class Controller implements Observer {

    private Game game;
    private ArrayList<RemoteView> remoteViews;

    public Controller(Game g){
        this.game = g;
    }

    /**actually modifies the mode
     *
     * this function passes the "command" from the user to the divinity, that then modifie the Board accordingly
     * TODO better exceptions management
     *
     * @author Elia Ravella
     * @param player the player in control of the (Remote)View
     * @param command the operation the player is wishing to do
     * @param workerID the number representing the worker (0, 1)
     * @return true if the operation goes fine, false if not
     */
    public boolean commitMove(String player, Command command, int workerID){
        if(game.getTurnPlayer().getNAME().equals(player)){
            try{
                game.getTurnPlayer().getDivinity().makeMove(
                        game.getTurnPlayer().getWorkers()[workerID],
                        command
                );
            }catch(IllegalMoveException | NullPointerException exc){
                System.err.println(exc.getMessage() + " controller generated");
                return  false;
            }
        }
        return true;
    }

    /**adds a worker to the board
     *
     * @author Elia Ravella
     * @param row the x axis coordinate to be added
     * @param column the y axis coordinate to be added
     * @return true if can be added there, false if not
     */
    public boolean addWorker(int row, int column){
        try {
            game.getBoard().addWorker(row, column);
        } catch (IllegalCellException | IllegalAddException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**adds the player to the game
     *
     * this function is called from the RemoteView at the beginning of the game, before the
     * startGame() method is invoked
     *
     * @author Elia Ravella
     * @param playerName the player's name
     * @param age the player's age
     * @return true if the operation goes fine, false if not
     */
    public boolean addPlayer(String playerName, int age){
        return game.addPlayer(playerName, age);
    }

    /**Changes the player controlling the board
     *
     * @author Elia Ravella
     */
    public void changeTurnPlayer(){
        try{
            game.getTurnPlayer().getDivinity().makeMove(
                    null,
                    new Command(0,0, CommandType.RESET)
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


}
