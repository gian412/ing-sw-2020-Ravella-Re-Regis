package controller;

public class Command {
    public final int cellX;
    public final int cellY;
    public final CommandType commandType;

    public Command(int x, int y, CommandType ct){
        this.cellX = x;
        this.cellY = y;
        this.commandType = ct;
    }
}
