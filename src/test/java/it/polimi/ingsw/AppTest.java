package it.polimi.ingsw;

import static org.junit.Assert.assertTrue;

import it.polimi.ingsw.model.god.Apollo;
import it.polimi.ingsw.model.god.ApolloTest;
import it.polimi.ingsw.model.god.Artemis;
import it.polimi.ingsw.model.god.ArtemisTest;

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

        // class Apollo
        ApolloTest apolloTest = new ApolloTest();
        apolloTest.hadMoveTest();
        apolloTest.hadMoveAndForcedTest();
        apolloTest.hadBuildNotDomeTest();
        apolloTest.hadBuildDomeTest();
        apolloTest.hadWinYesTest();
        apolloTest.hadWinNoTest();

        // class Artemis
        ArtemisTest artemisTest = new ArtemisTest();
        artemisTest.hadMoveTest();
        artemisTest.hadMoveSecondTest();
        artemisTest.hadBuildNotDomeTest();
        artemisTest.hadBuildDomeTest();
        artemisTest.hadWinYesTest();
        artemisTest.hadWinNoTest();
    }

}
