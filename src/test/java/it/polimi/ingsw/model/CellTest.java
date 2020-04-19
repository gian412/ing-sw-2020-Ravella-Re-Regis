package it.polimi.ingsw.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellTest {

    @Test
    @DisplayName("getDirectionTest")
    public void getDirectionTest() {

        Cell cell1 = new Cell(1,1);
        Cell cell2 = new Cell(0, 0);

        Pair pair = new Pair( -1, -1);

        assertTrue("the distance between the two cells is right",
                cell1.getDirection(cell2).x == pair.x && cell1.getDirection(cell2).y == pair.y);
    }

    @Test
    @DisplayName("cellDistanceTrueTest")
    public void cellDistance1Test() {

        Cell cell1 = new Cell(1,1);
        Pair pair = new Pair(0, 0);


        assertTrue("the distance is 1 cell",
                cell1.cellDistance(pair));
    }

    @Test
    @DisplayName("cellDistanceFalseTest")
    public void cellDistance2Test() {

        Cell cell1 = new Cell(1,1);
        Pair pair = new Pair(3, 0);

        assertFalse("the distance is not 1 cell",
                cell1.cellDistance(pair));
    }

}
