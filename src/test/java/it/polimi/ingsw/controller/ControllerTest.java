package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.exceptions.NoSuchPlayerException;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.Athena;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class ControllerTest {
    @Test
    @DisplayName("checking the base 'add player' function")
    public void checkAddPlayer(){
        Game g = new Game();
        Controller controller = new Controller(g);
        Player player = new Player("name", 10);

        controller.addPlayer(player.getNAME(), player.getAge());

        assertTrue(g.getPlayers().contains(player.getNAME()));
        assertTrue(g.getPlayers().contains("10"));

    }

    @Test
    @DisplayName("the turnplayer actually change when issued the 'changeTurnPlayer' command")
    public void checkTurnPlayer(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Elia", 20);

        controller.startGame();

        Player p1 = g.getTurnPlayer();
        controller.changeTurnPlayer();
        Player p2 = g.getTurnPlayer();

        assertNotEquals("After a change of turn, player should change", p1, p2);

    }

    @Test
    @DisplayName("Check initialization of divinity")
    public void checkSetDivinity(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();

        assertNotNull(g.getTurnPlayer().getDivinity());

    }

    @Test
    @DisplayName("Check the addWorker function")
    public void checkAddWorker(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();
        controller.addWorker(1, 0);

        assertNotNull("a worker must be in (1, 0)", g.getBoard().getCell(new Pair (1,0)).getWorker());
        assertEquals("controller.addWorker adds a playerTurn's worker",
                g.getBoard().getCell(new Pair (1,0)).getWorker().getOwner(),
                g.getTurnPlayer()
        );
    }

    @Test
    @DisplayName("test the build function")
    public void checkBuild(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();
        controller.addWorker(0, 0);
        controller.commitCommand("Marco", new Command(new Pair(0, 1), CommandType.MOVE), 0);
        controller.commitCommand("Marco", new Command(new Pair(0, 0), CommandType.BUILD), 0);

        assertEquals("Built a floor in a cell", g.getBoard().getCell(new Pair (0,0)).getHeight(), Height.FIRST_FLOOR);

    }

    @Test
    @DisplayName("test the move function")
    public void checkMove(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();
        controller.addWorker(0, 0);
        controller.commitCommand("Marco", new Command(new Pair(1, 1), CommandType.MOVE), 0);

        assertNotNull("moved a worker in (1, 1)", g.getBoard().getCell(new Pair (1,1)).getWorker());
        assertNull("in (0, 0) the worker must be null", g.getBoard().getCell(new Pair (0,0)).getWorker());
    }

    @Test
    @DisplayName("testing the ordering")
    public void testOrderPlayersAtStartGame(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 35);
        controller.addPlayer("Gianluca", 31);

        controller.startGame();

        assertEquals("Gianluca", g.getTurnPlayer().getNAME() );
    }

    @Test
    @DisplayName("testing the illegal move error catching")
    public void testIllegalMoveWorker(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();

        // after the initialization of the game, try to
        // do a illegal move (out-of-bound move)
        controller.addWorker(0, 0);

        controller.commitCommand("Marco", new Command(new Pair(3, 3), CommandType.MOVE), 0);

        assertEquals("Illegal move", g.getBoard().getProxy().getIllegalMoveString());
    }

    @Test
    @DisplayName("testing the illegal add error catching")
    public void testIllegalAddWorker(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        try {
            g.setPlayerDivinity("Marco", new Apollo(g.getBoard()));
            g.setPlayerDivinity("Gianluca", new Athena(g.getBoard()));
        }catch(NoSuchPlayerException x){
            System.err.println(x.getMessage());
        }

        controller.startGame();

        // after the initialization of the game, try to
        // do a illegal move (adding a worker in an occupied cell)
        controller.addWorker(0, 0);
        controller.changeTurnPlayer();
        controller.addWorker(0, 0); // this is illegal


        assertEquals("Illegal cell", g.getBoard().getProxy().getIllegalMoveString());
    }

    @Test
    @DisplayName("full update-function check")
    public void updateCheck(){
        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 30);
        controller.addPlayer("Gianluca", 35);

        PlayerCommand setPlayerDivinity = new PlayerCommand(
                "Marco",
                new Command(new Pair(0, 0), CommandType.CHOOSE_GOD),
                0
        );
        setPlayerDivinity.setMessage("Apollo");

        controller.update(setPlayerDivinity);
        controller.startGame();

        assertTrue("Marco should be an 'Apollo' type", g.getTurnPlayer().getDivinity() instanceof Apollo);
    }
}
























