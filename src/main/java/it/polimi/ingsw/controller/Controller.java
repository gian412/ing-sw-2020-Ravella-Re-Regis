package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalAddException;
import it.polimi.ingsw.exceptions.IllegalCellException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.*;

import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.view.Observer;

import java.lang.reflect.InvocationTargetException;

/**
 * Implements the controller of the application
 * Manages the model, links the remote views with the model, manages exceptions/errors
 * generated in the model
 *
 * @author Elia Ravella
 */
public class Controller implements Observer<PlayerCommand> {

    private Game game;

    /**
     * class constructor
     *
     * @param g the instance of the class Game that encapsulates the whole match
     * @@author Elia Ravella
     */
    public Controller(Game g){
        this.game = g;
    }

    /**actually modifies the mode
     *
     * this function passes the "command" from the user to the divinity, that then modifies the Board accordingly
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
                if (game.getTurnPlayer().getDivinity()!=null) {
                    game.getTurnPlayer().getDivinity().executeCommand(
                            game.getTurnPlayer().getWorkers()[workerID],
                            command
                    );
                }
            } catch (IllegalMoveException exc) {
                game.getBoard().notifyIllegalMove(exc.getMessage());
            } catch (Exception exc) {
                game.getBoard().notifyIllegalMove("Something went wrong: " + exc.getMessage());
            }
        }

    }

    /**adds a worker to the board. the worker added is owned by the turn player
     * as the "adding worker" procedure is turn-based. if the operation is "illegal"
     * a notify is sent to the clients
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
     * this function removes the actual turn player and replaces it with the next on the list
     * it also sends a command to the gods of the former turn player to resets some variables
     * this method triggers an update of the client
     *
     * @author Elia Ravella
     */
    public void changeTurnPlayer(){
        try{
            if (game.getTurnPlayer().getDivinity()!=null) {
                game.getTurnPlayer().getDivinity().executeCommand(
                        null,
                        new Command(new Pair(0, 0), CommandType.RESET)
                );
            }
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

    /**
     * @return the turn player memorised in Board
     * @author Elia Ravella
     */
    public Player getTurnPlayer(){
        return this.game.getTurnPlayer();
    }

    /**
     * the method update is triggered by a remoteview change: it's the standard notify-update interaction of the Observer pattern
     * it decodes the message from the client and manages the action to do on the model
     * @param message the update content
     * @author Elia Ravella
     */
    @Override
    public void update(PlayerCommand message){
        if(message == null ) throw new IllegalArgumentException();

        switch(message.cmd.commandType){
            case DISCONNECTED:
                game.endGame();
                break;

            case SET_GODS:
                game.getBoard().setChoosingGods(message.getMessage());
                break;

            case CHOOSE_GOD:
                try {
                    game.setPlayerDivinity(
                            message.playerName,
                            (God)Class.forName("it.polimi.ingsw.model.god." + message.message).getDeclaredConstructor(Board.class).newInstance(game.getBoard())
                    );
                    game.getBoard().getProxy().setChoosingGods(
                            game.getBoard().getProxy().getChoosingGods().replace(message.message, "")
                    );
                    if(game.getBoard().getProxy().getChoosingGods().split(" ").length == 0) {
                        game.getBoard().getProxy().setGods(game.getGodsMap());
                        game.getBoard().getProxy().setStatus(GameState.ADDING_WORKER);
                    }

                } catch (NoSuchPlayerException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;

            case CHANGE_TURN:
                changeTurnPlayer();

                for(Worker worker : getTurnPlayer().getWorkers())
                    commitCommand(
                            getTurnPlayer().getNAME(),
                            new Command(
                                    new Pair(0, 0),
                                    CommandType.CHECK_WORKERS
                            ),
                            worker.getWORKER_ID().charAt(worker.getWORKER_ID().length() - 1) == '0' ? 0 : 1
                    );

                game.getBoard().updateProxyBoard();
                break;

            case ADD_WORKER:
                addWorker(
                        message.cmd.coordinates.x,
                        message.cmd.coordinates.y
                );
                if(game.getBoard().getProxy().getWorkers().size() == (2*game.getNumberOfPlayers())) {
                    game.getBoard().getProxy().setStatus(GameState.PLAYING);
                }

                break;

            default:
                commitCommand(
                        message.getPlayer(),
                        message.getCommand(),
                        message.getWorkerIndex()
                );
                break;
        }
    }
}
