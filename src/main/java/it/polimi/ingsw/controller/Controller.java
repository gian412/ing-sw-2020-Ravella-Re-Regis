package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import it.polimi.ingsw.view.Observer;


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
     * @return true if the operation goes fine, false if not
     */
    public boolean commitCommand(String player, Command command, int workerID){
        if(game.getTurnPlayer().getNAME().equals(player)){
            try{
                game.getTurnPlayer().getDivinity().executeCommand(
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
            game.getTurnPlayer().getDivinity().executeCommand(
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
    public void startGame(){ game.startGame(); }

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

        // this if is triggered during game setup
        if(this.game.getTurnPlayer().getDivinity() == null && message.player.getDivinity() != null) {
            try {
                this.game.setPlayerDivinity(message.player.getNAME(), message.player.getDivinity());
            } catch (NoSuchPlayerException e) {
                e.printStackTrace();
            }
        }

        else{
            if(message.cmd.commandType == CommandType.CHANGE_TURN)
                changeTurnPlayer();
            else if(message.cmd.commandType == CommandType.ADD_WORKER){
                addWorker(
                        message.cmd.cellX,
                        message.cmd.cellY
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
