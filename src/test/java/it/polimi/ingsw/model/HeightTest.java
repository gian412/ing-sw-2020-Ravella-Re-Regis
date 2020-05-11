package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HeightTest {

    @Test
    @DisplayName("getDifference1Test")
    public void getDifference1Test() {
        byte result;

        result = Height.GROUND.getDifference(Height.GROUND);

        assertTrue("the difference of height is right",
                result == 0);
    }

    @Test
    @DisplayName("getDifference2Test")
    public void getDifference2Test() {
        byte result;

        result = Height.GROUND.getDifference(Height.FIRST_FLOOR);

        assertTrue("the difference of height is right",
                result == 1);
    }

    @Test
    @DisplayName("getDifference3Test")
    public void getDifference3Test() {
        byte result;

        result = Height.GROUND.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == 2);
    }

    @Test
    @DisplayName("getDifference4Test")
    public void getDifference4Test() {
        byte result;

        result = Height.GROUND.getDifference(Height.THIRD_FLOOR);

        assertTrue("the difference of height is right",
                result == 3);
    }

    @Test
    @DisplayName("getDifference5Test")
    public void getDifference5Test() {
        byte result;

        result = Height.GROUND.getDifference(Height.DOME);

        assertTrue("the difference of height is right",
                result == 4);
    }

    @Test
    @DisplayName("getDifference6Test")
    public void getDifference6Test() {
        byte result;

        result = Height.FIRST_FLOOR.getDifference(Height.GROUND);

        assertTrue("the difference of height is right",
                result == -1);
    }

    @Test
    @DisplayName("getDifference7Test")
    public void getDifference7Test() {
        byte result;

        result = Height.FIRST_FLOOR.getDifference(Height.FIRST_FLOOR);

        assertTrue("the difference of height is right",
                result == 0);
    }

    @Test
    @DisplayName("getDifference8Test")
    public void getDifference8Test() {
        byte result;

        result = Height.FIRST_FLOOR.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == 1);
    }

    @Test
    @DisplayName("getDifference9Test")
    public void getDifference9Test() {
        byte result;

        result = Height.FIRST_FLOOR.getDifference(Height.THIRD_FLOOR);

        assertTrue("the difference of height is right",
                result == 2);
    }

    @Test
    @DisplayName("getDifference10Test")
    public void getDifference10Test() {
        byte result;

        result = Height.FIRST_FLOOR.getDifference(Height.DOME);

        assertTrue("the difference of height is right",
                result == 3);
    }

    @Test
    @DisplayName("getDifference11Test")
    public void getDifference11Test() {
        byte result;

        result = Height.SECOND_FLOOR.getDifference(Height.GROUND);

        assertTrue("the difference of height is right",
                result == -2);
    }

    @Test
    @DisplayName("getDifference12Test")
    public void getDifference12Test() {
        byte result;

        result = Height.SECOND_FLOOR.getDifference(Height.FIRST_FLOOR);

        assertTrue("the difference of height is right",
                result == -1);
    }

    @Test
    @DisplayName("getDifference13Test")
    public void getDifference13Test() {
        byte result;

        result = Height.SECOND_FLOOR.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == 0);
    }

    @Test
    @DisplayName("getDifference14Test")
    public void getDifference14Test() {
        byte result;

        result = Height.SECOND_FLOOR.getDifference(Height.THIRD_FLOOR);

        assertTrue("the difference of height is right",
                result == 1);
    }

    @Test
    @DisplayName("getDifference15Test")
    public void getDifference15Test() {
        byte result;

        result = Height.SECOND_FLOOR.getDifference(Height.DOME);

        assertTrue("the difference of height is right",
                result == 2);
    }

    @Test
    @DisplayName("getDifference16Test")
    public void getDifference16Test() {
        byte result;

        result = Height.THIRD_FLOOR.getDifference(Height.GROUND);

        assertTrue("the difference of height is right",
                result == -3);
    }

    @Test
    @DisplayName("getDifference17Test")
    public void getDifference17Test() {
        byte result;

        result = Height.THIRD_FLOOR.getDifference(Height.FIRST_FLOOR);

        assertTrue("the difference of height is right",
                result == -2);
    }

    @Test
    @DisplayName("getDifference18Test")
    public void getDifference18Test() {
        byte result;

        result = Height.THIRD_FLOOR.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == -1);
    }

    @Test
    @DisplayName("getDifference19Test")
    public void getDifference19Test() {
        byte result;

        result = Height.THIRD_FLOOR.getDifference(Height.THIRD_FLOOR);

        assertTrue("the difference of height is right",
                result == 0);
    }

    @Test
    @DisplayName("getDifference20Test")
    public void getDifference20Test() {
        byte result;

        result = Height.THIRD_FLOOR.getDifference(Height.DOME);

        assertTrue("the difference of height is right",
                result == 1);
    }

    @Test
    @DisplayName("getDifference21Test")
    public void getDifference21Test() {
        byte result;

        result = Height.DOME.getDifference(Height.GROUND);

        assertTrue("the difference of height is right",
                result == -4);
    }

    @Test
    @DisplayName("getDifference22Test")
    public void getDifference22Test() {
        byte result;

        result = Height.DOME.getDifference(Height.FIRST_FLOOR);

        assertTrue("the difference of height is right",
                result == -3);
    }

    @Test
    @DisplayName("getDifference23Test")
    public void getDifference23Test() {
        byte result;

        result = Height.DOME.getDifference(Height.SECOND_FLOOR);

        assertTrue("the difference of height is right",
                result == -2);
    }

    @Test
    @DisplayName("getDifference24Test")
    public void getDifference24Test() {
        byte result;

        result = Height.DOME.getDifference(Height.THIRD_FLOOR);

        assertTrue("the difference of height is right",
                result == -1);
    }

    @Test
    @DisplayName("getDifference25Test")
    public void getDifference25Test() {
        byte result;

        result = Height.DOME.getDifference(Height.DOME);

        assertTrue("the difference of height is right",
                result == 0);
    }












}
