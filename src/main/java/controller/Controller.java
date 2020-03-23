package controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.Atlas;
import it.polimi.ingsw.model.god.Hephaestus;

import java.util.Scanner;

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
        return game.addPlayer(new Player(playerName, age));
    }

    public void changeTurnPlayer(){
        game.getTurnPlayer().setTurnPlayer(false);
        game.getBoard().setTurnPlayer(game.getTurnPlayer().getNextPlayer());
        game.getTurnPlayer().setTurnPlayer(true);
    }

    public static void main(String[] args) {


    }

}
