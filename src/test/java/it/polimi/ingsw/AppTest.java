package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.model.god.GodTest;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    public static void main(String[] args){
        GodTest godTest = new GodTest();
        godTest.checkWinYesTest();
        godTest.checkWinNoTest();
    }
}
