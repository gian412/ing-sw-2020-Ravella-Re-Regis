package it.polimi.ingsw.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;

public class GodTypeTest {
    @Test
    @DisplayName("Capitalized name method test")
    public void capitalizedTest(){
        GodType myGod = GodType.CHARON;
        System.out.println(myGod.getCapitalizedName());
        assertEquals("Charon", myGod.getCapitalizedName());
    }
}
