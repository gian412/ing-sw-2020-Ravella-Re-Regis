package it.polimi.ingsw.model;

import java.io.Serializable;

public class Pair implements Serializable {
    private static final long serialVersionUID = 1;

    public final int x;
    public final int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return this.x == ((Pair) obj).x && this.y == ((Pair) obj).y;
        }
        return false;
    }
}
