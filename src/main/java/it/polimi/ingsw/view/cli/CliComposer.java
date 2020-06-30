package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.gui.StaticFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CliComposer {

    //Pattern to write the title SANTORINI in the Cli
    final static int[][] SANTORINI =
                 {  {0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1 ,0, 1},
                    {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                    {0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
                    {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
                    {0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1}
                 };

    final static int[][] WELCOME_TO =
                {   {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0},
                    {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 ,1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0}
            };

    final static int[][] YOU =
                {   {1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                    {0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                    {0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0}
            };

    final static int[][] WIN =
            {   {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1},
                {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                {0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                {0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1}
            };

    final static int[][] LOSE =
            {   {1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0 ,1, 0, 0 ,1, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0 ,0, 1, 0, 1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0}
            }; 



    private String playerName;
    private int numberOfPlayer;

    /**
     * CLiComposer constructor
     *
     * Author Marco Re
     */
    public CliComposer(String name, int number){
        playerName = name;
        numberOfPlayer = number;
    }

    /**
     * CLiComposer constructor
     *
     * Author Marco Re
     */
    public CliComposer(){
    }

    /**
     * create a formatted string for the title of the game 
     *
     * @author Marco Re
     *
     * @return the title
     */
    public String titleMaker(){

        StringBuilder write = new StringBuilder();
        Ansi maker = new Ansi();

        //set font and background
        String change = maker.bgAndFont(Ansi.BACKGROUND_WHITE_B, Ansi.BLUE_B);
        write.append(change);

        //put an empty line
        for(int t = 0; t < 130; t++)
            write.append(' ');
        write.append(Ansi.RESET);
        write.append('\n');

        //insert the title (WELCOME TO)
        for(int i = 0; i < 7; i++){
            write.append(change);
            for(int t = 0; t < 40; t++) {
                write.append(' ');
            }

            for(int j = 0; j < 53; j++) {
                if(WELCOME_TO[i][j] == 0)
                    write.append(' ');
                else
                    write.append(Unicode.SQUARE);
            }

            for(int t = 0; t < 37; t++) {
                write.append(' ');
            }

            write.append(Ansi.RESET);
            write.append(" \n");
        }

        for(int i = 0; i < 2; i++) {
            write.append(change);
            for (int t = 0; t < 130; t++)
                write.append(' ');

            write.append(Ansi.RESET);
            write.append('\n');
        }

        //insert the title (SANTORINI)
        for(int i = 0; i < 7; i++) {
            for(int z = 0; z < 2; z++){

                write.append(change);
                for(int t = 0; t < 20; t++) {
                    write.append(' ');
                }

                for(int j = 0; j < 45; j++) {
                    for(int k = 0; k < 2; k++){
                        if(SANTORINI[i][j] == 0)
                            write.append(" ");
                        else
                            write.append(Unicode.SQUARE);
                    }
                }

                for(int t = 0; t < 20; t++) {
                    write.append(' ');
                }

                write.append(Ansi.RESET);
                write.append("\n");
            }
        }

        write.append(change);

        for(int t = 0; t < 130; t++)
            write.append(' ');

        write.append(Ansi.RESET);
        write.append('\n');

         return write.toString();
    }

    /**
     *create a formatted string to put at the beginning of the terminal
     *
     * @author Marco Re
     *
     * @return the banner to put at the beginning of the terminal
     */
    public String bannerMaker(){

        StringBuilder write = new StringBuilder();
        Ansi maker = new Ansi();

        String change = maker.bgAndFont(Ansi.BACKGROUND_WHITE_B, Ansi.BLUE_B);

        //put an empty line
        write.append(change);
        for(int t = 0; t < 130; t++)
            write.append(' ');

        write.append(Ansi.RESET);
        write.append('\n');

        //insert the line with the name of the game
        write.append(change);
        write.append("   WELCOME TO SANTORINI");
        for(int t = 0; t < 107; t++)
            write.append(' ');

        write.append(Ansi.RESET);
        write.append('\n');

        //put an empty line
        write.append(change);
        for(int t = 0; t < 130; t++)
            write.append(' ');

        write.append(Ansi.RESET);
        write.append('\n');

        return write.toString();
    }


    /**
     * print the list of all the possible gods
     *
     * @author Marco Re
     *
     * @param proxy is the proxyboard which arrive from the net
     */
    public void godList(BoardProxy proxy){

        GodType[] gods = {GodType.APOLLO, GodType.ARTEMIS, GodType.ATHENA, GodType.ATLAS, GodType.CHARON, GodType.CHRONUS,
            GodType.DEMETER, GodType.HEPHAESTUS, GodType.HESTIA, GodType.MINOTAUR, GodType.PAN, GodType.PROMETHEUS,
            GodType.TRITON, GodType.ZEUS};
        Ansi maker = new Ansi();
        String change;

        System.out.println(Ansi.RESET_SCREEN);
        System.out.println(bannerMaker() + "\n");

        for(GodType x : gods) {

            if(proxy.getChoosingGods().equals(""))
                change = maker.font(Ansi.BLUE_B);
            else if(proxy.getChoosingGods().toUpperCase().contains(x.toString()))
                change = maker.font(Ansi.GREEN_B);
            else
                change = maker.font(Ansi.BLUE);

            System.out.println("     " + change + x.toString());
            System.out.println("     " + getDescription(x) + "\n");
        }

        if(!proxy.getChoosingGods().equals("") && proxy.getTurnPlayer().equals(playerName))
            System.out.println("Choose between GREEN gods\n" + "Please push ENTER to continue ......");
        else
            if(proxy.getTurnPlayer().equals(playerName))
                System.out.println("Now is your turn. Please push ENTER to continue......");
    }

    /**
     * return the description of a specific god
     *
     * @author Marco Re
     *
     * @param god il the god which i want the description
     *
     * @return a formatted string with the description of the god
     */
    private String getDescription(GodType god) {
        Ansi maker = new Ansi();
        String font = maker.font(Ansi.BLUE_B);

        switch (god) {
            case APOLLO:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "your worker may move in into an opponent Worker's space by forcing their Worker to the space yours just vacated";
            case ARTEMIS:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Your Worker may move one additional time, but not back to its initial space.";
            case ATHENA:
                return font +
                        "OPPONENT'S TURN: " + Ansi.RESET +
                        "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
            case ATLAS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build a dome at any level.";
            case CHARON:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other \n          " +
                        "side of your Worker, if that space is unoccupied.";
            case CHRONUS:
                return font +
                        "CONDITION WIN: " + Ansi.RESET +
                        "You also win when there are at least five Complete Towers on the board.";
            case DEMETER:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional time, but not on the same space.";
            case HEPHAESTUS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional block (not dome) on top of your first block.";
            case HESTIA:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build one additional time, but this cannot be on a perimeter space.";
            case MINOTAUR:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Your Worker may move into an opponent Worker’s space, if their Worker can be forced\n          " +
                        "one space straight backwards to an unoccupied space at any level.";
            case PAN:
                return font +
                        "WIN CONDITION: " + Ansi.RESET +
                        "You also win if your Worker moves down two or more levels.";
            case PROMETHEUS:
                return font +
                        "YOUR TURN: " + Ansi.RESET +
                        "If your Worker does not move up, it may build both before and after moving.";
            case TRITON:
                return font +
                        "YOUR MOVE: " + Ansi.RESET +
                        "Each time your Worker moves into a perimeter space, it may immediately move again.";
            case ZEUS:
                return font +
                        "YOUR BUILD: " + Ansi.RESET +
                        "Your Worker may build a block under itself.";
            default:
                return null;
        }
    }

    /**
     * print the board
     *
     * @author Marco Re
     *
     * @param board is the proxyboard which arrive from the net
     */
    public void boardMaker(BoardProxy board){

        StringBuilder out = new StringBuilder("");
        Ansi maker = new Ansi();

        //create an array with the name of the players and remove the name of the turnplayer
        Object[] players3 = board.getGods().keySet().toArray();
        List<String> players2 = new ArrayList<>();
        if(numberOfPlayer ==3)
            for( int i = 0; i < 3; i ++)
                if(!players3[i].toString().equals(playerName))
                    players2.add(players3[i].toString());

        //reset the screen and print the banner
        System.out.println(Ansi.RESET_SCREEN);
        System.out.println(bannerMaker() + "\n\n");

        System.out.println("           " + "   1  " + "   2  " + "   3  " + "   4  " + "   5  ");

        //first line of the board
        out.append("\n           ");
        for (int k = 0; k < 31; k++) {
            out.append("-");
        }
        out.append("\n");

        for(int i = 0; i < 5; i++) {
            for(int k = 0; k < 3; k++) {
                out.append("      ");

                //if it is the center of the line indicate the number of the line
                if(k == 1)
                    out.append(" " + (i+1) + "   ");
                else
                    out.append("     ");

                //scan the cells of the line, every cell is a 3x3 and the function creates a line every time
                for (int j = 0; j < 5; j++) {

                    if(k == 0){
                        boolean found = false;
                        for(Map.Entry<String, Pair> entry : board.getWorkers().entrySet()){
                            if((entry.getValue().y == i) && (entry.getValue().x == j)) {
                                if(entry.getKey().contains(playerName)){
                                    out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_GREEN_B, Ansi.BLACK_B) + "     ");
                                    out.append(Ansi.RESET);
                                    found = true;
                                }
                                else{
                                    if(numberOfPlayer == 2){
                                        out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_RED_B, Ansi.RED) + "     ");
                                        out.append(Ansi.RESET);
                                        found = true;
                                    }

                                    if(numberOfPlayer == 3){
                                         // the first player is blue
                                        if(entry.getKey().contains(players2.get(0))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_BLUE_B, Ansi.BLUE) + "     ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }

                                        //the second player is yellow
                                        System.out.println(entry.getKey() + " r" + players2.get(1));
                                        if(entry.getKey().contains(players2.get(1))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_YELLOW_B, Ansi.YELLOW) + "     ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!found)
                            out.append("|     ");
                    }

                    if(k == 1){
                        boolean found = false;
                        for(Map.Entry<String, Pair> entry : board.getWorkers().entrySet()){
                            if((entry.getValue().y == i) && (entry.getValue().x == j)) {
                                if(entry.getKey().contains(playerName)){
                                    out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_GREEN_B, Ansi.GREEN) + " " + board.getBoardScheme()[i][j].toInt() + "/");
                                    out.append(entry.getKey().charAt(entry.getKey().length() - 1) + " ");
                                    out.append(Ansi.RESET);
                                    found = true;
                                }
                                else{
                                    if(numberOfPlayer == 2){
                                        out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_RED_B, Ansi.RED) + " " + board.getBoardScheme()[i][j].toInt() + "/");
                                        out.append(entry.getKey().charAt(entry.getKey().length() - 1) + " ");
                                        out.append(Ansi.RESET);
                                        found = true;
                                    }
                                    if(numberOfPlayer == 3){
                                        if(entry.getKey().contains(players2.get(0))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_BLUE_B, Ansi.BLUE) + " " + board.getBoardScheme()[i][j].toInt() + "/");
                                            out.append(entry.getKey().charAt(entry.getKey().length() - 1) + " ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }

                                        if(entry.getKey().contains(players2.get(1))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_YELLOW_B, Ansi.YELLOW) + " " + board.getBoardScheme()[i][j].toInt() + "/");
                                            out.append(entry.getKey().charAt(entry.getKey().length() - 1) + " ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!found)
                            out.append("| " + board.getBoardScheme()[i][j].toInt() + "/  ");
                    }

                    if(k == 2){
                        boolean found = false;
                        for(Map.Entry<String, Pair> entry : board.getWorkers().entrySet()){
                            if((entry.getValue().y == i) && (entry.getValue().x == j)) {
                                if(entry.getKey().contains(playerName)){
                                    out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_GREEN_B, Ansi.BLACK_B) + "     ");
                                    out.append(Ansi.RESET);
                                    found = true;
                                }

                                else{
                                    if(numberOfPlayer == 2){
                                        out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_RED_B, Ansi.RED) + "     ");
                                        out.append(Ansi.RESET);
                                        found = true;
                                    }

                                    if(numberOfPlayer == 3){
                                        // the first player is blue
                                        if(entry.getKey().contains(players2.get(0))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_BLUE_B, Ansi.BLUE) + "     ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }

                                        //the second player is yellow
                                        if(entry.getKey().contains(players2.get(1))){
                                            out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_YELLOW_B, Ansi.YELLOW) + "     ");
                                            out.append(Ansi.RESET);
                                            found = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!found)
                            out.append("|     ");
                    }
                }
                //put the last | at the end of the line
                out.append("|\n");
            }

            //put the last line at the end of every line
            out.append("           ");
            for (int k = 0; k < 31; k++) {
                out.append("-");
            }
            out.append("\n");
        }

        //print the board
        System.out.println(out.toString());

        //print the message for the current player
        if(board.getTurnPlayer().equals(playerName))
            System.out.println("Now is your turn. Please press ENTER to continue ........");
    }

    /**
     * print the message of finish game
     *
     * @author Marco Re
     *
     * @param board is the proxyboard which arrive from the net
     */
    public void terminateGame(BoardProxy board){

        Ansi maker = new Ansi();
        StringBuilder write = new StringBuilder("");

        System.out.println(Ansi.RESET_SCREEN);
        System.out.println(bannerMaker() + "\n");

        if(board.getWinner().equals("Unexpected Game Over")){

            System.out.println("   Unexpected connection ERROR. Other Client Down\n\n");
            System.out.println("   Press ENTER to exit from the game......");
            return;
        }

        else if(board.getWinner().equals("Server down")){
            System.out.println("   Unexpected connection ERROR. Serve Down\n\n");
            System.out.println("   Press ENTER to exit from the game......");
            return;
        }

        else{
            //set font and background
            String change = maker.bgAndFont(Ansi.BACKGROUND_WHITE_B, Ansi.BLUE_B);
            write.append(change);

            //put an empty line
            for (int i = 0; i < 130; i++)
                write.append(' ');
            write.append(Ansi.RESET);
            write.append('\n');

            //insert the title (YOU)
            for (int i = 0; i < 7; i++) {
                write.append(change);
                for (int t = 0; t < 56; t++) {
                    write.append(' ');
                }

                for (int j = 0; j < 17; j++) {
                    if (YOU[i][j] == 0)
                        write.append(' ');
                    else
                        write.append(Unicode.SQUARE);
                }

                for (int t = 0; t < 57; t++) {
                    write.append(' ');
                }

                write.append(Ansi.RESET);
                write.append(" \n");
            }

            for (int i = 0; i < 130; i++)
                write.append(' ');
            write.append(Ansi.RESET);
            write.append('\n');

            if(board.getWinner().equals(playerName)){
                //insert the title (WIN)
                for (int i = 0; i < 7; i++) {
                    write.append(change);
                    for (int t = 0; t < 57; t++) {
                        write.append(' ');
                    }

                    for (int j = 0; j < 15; j++) {
                        if (YOU[i][j] == 0)
                            write.append(' ');
                        else
                            write.append(Unicode.SQUARE);
                    }

                    for (int t = 0; t < 58; t++) {
                        write.append(' ');
                    }

                    write.append(Ansi.RESET);
                    write.append(" \n");
                }

                for (int i = 0; i < 130; i++)
                    write.append(' ');
                write.append(Ansi.RESET);
                write.append('\n');
            }

            else {
                //insert the title (LOSE)
                for (int i = 0; i < 7; i++) {
                    write.append(change);
                    for (int t = 0; t < 53; t++) {
                        write.append(' ');
                    }

                    for (int j = 0; j < 17; j++) {
                        if (YOU[i][j] == 0)
                            write.append(' ');
                        else
                            write.append(Unicode.SQUARE);
                    }

                    for (int t = 0; t < 54; t++) {
                        write.append(' ');
                    }

                    write.append(Ansi.RESET);
                    write.append(" \n");
                }

                for (int i = 0; i < 130; i++)
                    write.append(' ');
                write.append(Ansi.RESET);
                write.append('\n');
            }
            System.out.println(write.toString());
            return;
        }
    }

}