package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.GameState;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.ClientHandler;

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

    public CliComposer(){
    }

    /**
     * create a formatted string for the title of the game
     *
     * @authors Marco Re
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
     * @authors Marco Re
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

        if(!proxy.getChoosingGods().equals(""))
            System.out.println("Choose between GREEN god\n");
    }

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







    public void boardMaker(BoardProxy board){

        StringBuilder out = new StringBuilder("");
        Ansi maker = new Ansi();

        System.out.println(Ansi.RESET_SCREEN);
        System.out.println(bannerMaker() + "\n\n");

        System.out.println("number of worker:" + board.getWorkers().size() + "\n");


        out.append("\n     ");
        for (int k = 0; k < 31; k++) {
            out.append("-");
        }
        out.append("\n");



        for(int i = 0; i < 5; i++) {
            for(int k = 0; k < 3; k++) {
                out.append("     ");
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
                                    out.append("|" + maker.bgAndFont(Ansi.BACKGROUND_GREEN_B, Ansi.BLACK_B) + " " + board.getBoardScheme()[i][j].toInt() + "/");
                                    out.append(entry.getKey().charAt(entry.getKey().length() - 1) + " ");
                                    out.append(Ansi.RESET);
                                    found = true;
                                }
                                else{

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


                            }
                        }

                        if(!found)
                            out.append("|     ");
                    }
                }
                out.append("|\n");
            }

            out.append("     ");
            for (int k = 0; k < 31; k++) {
                out.append("-");
            }

            out.append("\n");

        }

        System.out.println(out.toString());









        
/*
        System.out.println("choosingGods: " + board.getChoosingGods());

        if(board.getTurnPlayer() != null)
            System.out.println("Is Playing " + board.getTurnPlayer());

        if(board.getIllegalMoveString() != null)
            System.out.println("illegal move:  " + board.getIllegalMoveString());

        if(board.getStatus() == GameState.SELECTING_GOD)
            System.out.println("SELECTING GOD");
        if(board.getStatus() == GameState.TERMINATOR)
            System.out.println("TERMINATOR");


        for(int i = 0; i < 5; i++){

            for(int j = 0; j < 5; j++){
                System.out.print("|");
                System.out.print(board.getBoardScheme()[i][j].toString().charAt(0));

                boolean found = false;
                for(Map.Entry<String, Pair> entry : board.getWorkers().entrySet()){
                    if((entry.getValue().y == i) && (entry.getValue().x == j)) {
                        System.out.print(entry.getKey().charAt(0));
                        found = true;
                    }
                }

                if(!found) {
                    System.out.print(" ");
                }
                System.out.print("| ");
            }

            System.out.print("\n");
        }
*/
    }
}

