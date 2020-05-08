package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.model.Pair;

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

    public String boardMaker(BoardProxy board){

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

        return null;
    }

}

