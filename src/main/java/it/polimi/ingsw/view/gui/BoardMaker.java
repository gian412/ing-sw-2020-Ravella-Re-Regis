package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.model.Pair;

import java.awt.*;

/**
 * this class offers static methods to the panels of the GUI to draw the Boardproxy on the window of the
 * application.
 *
 * @author Elia Ravella
 */
public class BoardMaker {

    private static final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
    private static final int
            IMAGE_WIDTH = 810,
            IMAGE_HEIGHT = 810,
            CELL_LENGTH = 162;

    /**
     * draws the towers on the board given.
     * all the measures are in pixels
     * the board is assumed a perfect square
     *
     * @param g the graphics object to draw with
     * @param board the board to draw
     * @param offset the initial offset from the border of the board
     * @param width the length of the single cell of the board
     * @param obs the observer
     * @param interstitialWidth the space between two cells
     * @author Elia Ravella
     */
    public static void drawElements(Graphics g, BoardProxy board, int offset, int width, int interstitialWidth, Component obs){
        Image first, second, third, dome, godImage;
        try {
            first = GetImages.getPieceImage(Height.FIRST_FLOOR.toString());
            second = GetImages.getPieceImage(Height.SECOND_FLOOR.toString());
            third = GetImages.getPieceImage(Height.THIRD_FLOOR.toString());
            dome = GetImages.getPieceImage(Height.DOME.toString());
        }catch(Exception x){
            x.printStackTrace();
            return;
        }

        // draw towers
        for(int row = 0; row < board.getBoardScheme().length; row++)
            for(int col = 0; col < board.getBoardScheme()[row].length; col++){
                switch(board.getBoardScheme()[row][col]){
                    case GROUND: break;
                    case FIRST_FLOOR:
                        g.drawImage(first,
                            offset + (width + interstitialWidth) * row,
                            offset + (width + interstitialWidth) * col,
                            width,
                            width,
                            obs
                        );
                        break;
                    case SECOND_FLOOR:
                        g.drawImage(second,
                                offset + (width + interstitialWidth) * row,
                                offset + (width + interstitialWidth) * col,
                                width,
                                width,
                                obs
                        );
                        break;
                    case THIRD_FLOOR:
                        g.drawImage(third,
                            offset + (width + interstitialWidth) * row,
                            offset + (width + interstitialWidth) * col,
                            width,
                            width,
                            obs
                        );
                        break;
                    case DOME:
                        g.drawImage(dome,
                            offset + (width + interstitialWidth) * row,
                            offset + (width + interstitialWidth) * col,
                            width,
                            width,
                            obs
                        );
                        break;
                }
            }

        // draw the workers
        for(int i = 0; i < board.getWorkers().keySet().size(); i++){
            String player = board.getWorkers().keySet().toArray()[i].toString().substring(
                    0,
                    board.getWorkers().keySet().toArray()[i].toString().length() - 1
            );
            String god = board.getGods().get(player);
            Pair actualCoordinates = deMap(board.getWorkers().get(board.getWorkers().keySet().toArray()[i]));

            try {
                godImage = GetImages.getWorkerImage(god);
            }catch(Exception x){
                x.printStackTrace();
                return;
            }

            g.drawImage(godImage,
                    actualCoordinates.x,
                    actualCoordinates.y,
                    width,
                    width,
                    obs
            );
        }
    }

    /**
     * maps the cells from the image to the grid
     *
     * @param x abscissa
     * @param y ordinate
     * @return the Pair with the mapped coordinates
     */
    public static Pair map(int x, int y){
        return new Pair(
                (x/CELL_LENGTH) % (IMAGE_WIDTH/CELL_LENGTH),
                (y/CELL_LENGTH) % (IMAGE_HEIGHT/CELL_LENGTH)
        );
    }

    /**
     * maps the cells from the grid to the image
     * @param pair pair to be converted
     * @return  the converted coord.
     */
    private static Pair deMap(Pair pair) {
        return new Pair(
                pair.x * CELL_LENGTH,
                pair.y * CELL_LENGTH
        );
    }
}
