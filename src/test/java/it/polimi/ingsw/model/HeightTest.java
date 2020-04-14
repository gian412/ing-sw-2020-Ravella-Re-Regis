package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HeightTest {

    @Test
    @DisplayName("getDifferenceTest")
    public void getDifferenceTest() {

        byte result;

        result = Height.GROUND.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == 2);
    }



}
