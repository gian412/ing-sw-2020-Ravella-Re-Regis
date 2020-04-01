package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class Controller {

    private Game game;

    public Controller(Game g){
        this.game = g;
    }


    public boolean commitMove(Player player, Command command, int workerID){
        if(game.getTurnPlayer().equals(player)){
            try{
                game.getTurnPlayer().getDivinity().makeMove(
                        game.getTurnPlayer().getWorkers()[workerID],
                        command
                );
            }catch(IllegalMoveException moveExc){
                return  false;
            }
        }
        return true;
    }

    public boolean addPlayer(String playerName, int age){
        return game.addPlayer(playerName, age);
    }

    public void changeTurnPlayer(){
        try{
            game.getTurnPlayer().getDivinity().makeMove(
                    null,
                    new Command(0,0, CommandType.RESET)
            );
        }catch(IllegalMoveException x){
            System.err.println(x.getMessage());
        }

        catch (NullPointerException x){
            System.err.println(x.getMessage());
        }

        finally {
            game.getTurnPlayer().setTurnPlayer(false);
            game.getBoard().changeTurnPlayer();
            game.getTurnPlayer().setTurnPlayer(true);
        }

    }

    public void startGame(){
        game.startGame();
    }


}
