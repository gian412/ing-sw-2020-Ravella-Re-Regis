package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Height;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardMaker {

    private static final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";

    /**
     * draws the towers on the board given.
     * all the measures are in pixels
     * the board is assumed a perfect square
     *
     * @param g the graphics object to draw with
     * @param board the board to draw
     * @param offset the initial offset from the border of the board
     * @param width the length of the single cell of the board
     * @param interstitialWidth the space between two cells
     * @author Elia Ravella
     */
    public static void drawTowers(Graphics g, BoardProxy board, int offset, int width, int interstitialWidth, Component obs){
        BufferedImage first, second, third, dome;
        try {
            first = ImageIO.read(new File(PATH + "_towerFirstFloor.png"));
            second = ImageIO.read(new File(PATH + "_towerSecondFloor.png"));
            third = ImageIO.read(new File(PATH + "_towerThirdFloor.png"));
            dome = ImageIO.read(new File(PATH + "_towerDome.png"));
        }catch(IOException x){
            x.printStackTrace();
            return;
        }

        for(int row = 0; row < board.getBoardScheme().length; row++)
            for(int col = 0; col < board.getBoardScheme()[row].length; col++){
                switch(board.getBoardScheme()[row][col]){
                    case GROUND: break;
                    case FIRST_FLOOR:
                        g.drawImage(first,
                            offset + (width + interstitialWidth) * col,
                            offset + (width + interstitialWidth) * row,
                            width,
                            width,
                            obs
                        );
                        break;
                    case SECOND_FLOOR:
                        g.drawImage(second,
                                offset + (width + interstitialWidth) * col,
                                offset + (width + interstitialWidth) * row,
                                width,
                                width,
                                obs
                        );
                        break;
                    case THIRD_FLOOR:
                        g.drawImage(third,
                            offset + (width + interstitialWidth) * col,
                            offset + (width + interstitialWidth) * row,
                            width,
                            width,
                            obs
                        );
                        break;
                    case DOME:
                        g.drawImage(dome,
                            offset + (width + interstitialWidth) * col,
                            offset + (width + interstitialWidth) * row,
                            width,
                            width,
                            obs
                        );
                        break;
                }
            }
    }
}
