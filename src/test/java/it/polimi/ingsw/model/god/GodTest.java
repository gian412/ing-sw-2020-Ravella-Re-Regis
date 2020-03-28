package it.polimi.ingsw.model.god;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.CommandType;
import it.polimi.ingsw.model.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class GodTest {

    @Test
    @DisplayName("Check win ")
    public void checkWinYesTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.THIRD_FLOOR);
        secondCell.setWorker(null);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println(e.toString());
        }

        assertTrue( "hadWin must be true", god.hadWin );

    }

    @Test
    @DisplayName("Check no win")
    public void checkWinNoTest(){

        // Initialization of the parameters
        Board board = new Board();
        Command command = new Command(1, 1, CommandType.MOVE);
        God god = new Apollo(board);
        Player player = new Player("Name", 18);
        player.setDivinity(god);
        Worker worker = new Worker("Id", player);

        // Initialization of the first cell
        Cell firstCell = new Cell(0, 1);
        firstCell.setHeight(Height.SECOND_FLOOR);
        firstCell.setWorker(worker);

        // Initialization of the second cell
        Cell secondCell = new Cell(1, 1);
        secondCell.setHeight(Height.SECOND_FLOOR);
        secondCell.setWorker(null);

        try{
            god.makeMove(worker, command);
        } catch (IllegalMoveException e){
            System.err.println(e.toString());
        }

        assertFalse( "hadWin must be false", god.hadWin );

    }
}
