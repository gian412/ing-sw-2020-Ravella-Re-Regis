package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.utils.GodType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GetImages {

    private static final int IMAGE_BASE_WIDTH = 84;
    private static final int IMAGE_BASE_HEIGHT = 141;

    public static Image img;
    public static Image board;

    // Load Gods' images and save them in an HashMap
    public static Map<String, Image> godImages = new HashMap<>();
    static {
        for (GodType godType : GodType.values()) {
            try {
                img = (ImageIO.read(GetImages.class.getClassLoader().getResource(godType.getCapitalizedName() + ".png"))).getScaledInstance(IMAGE_BASE_WIDTH, IMAGE_BASE_HEIGHT, Image.SCALE_DEFAULT);;
                godImages.put(godType.getName(), img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load Workers' images and save them in an HashMap
    public static Map<String, Image> workerImages = new HashMap<>();
    static {
        for (GodType godType : GodType.values()) {
            try {
                img = ImageIO.read(GetImages.class.getClassLoader().getResource(godType.getCapitalizedName() + "_Worker.png"));
                godImages.put(godType.getName(), img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load board pieces and save them in an HashMap
    public static Map<String, Image> piecesImages = new HashMap<>();
    static {
        for (Height height : Height.values()) {
            try {
                img = ImageIO.read(GetImages.class.getClassLoader().getResource("_" + height.toString() + ".png"));
                piecesImages.put(height.toString(), img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load board image and save it in an Image
    static {
        try {
            board = ImageIO.read(GetImages.class.getClassLoader().getResource("_BOARD.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to get the image of the given God
     *
     * @author Gianluca Regis
     * @param actualGod String whit the name of the requested God
     * @return The Image of the God
     */
    public static Image getGodImage(String actualGod) {
        return godImages.get(actualGod.toUpperCase());
    }

    /**
     * Method used to get the image of the worker of the given God
     *
     * @author Gianluca Regis
     * @param actualGod String whit the name of the God
     * @return The Image of the worker
     */
    public static Image getWorkerImage(String actualGod) {
        return workerImages.get(actualGod.toUpperCase());
    }

    /**
     * Method used to get the image of the piece
     *
     * @author Gianluca Regis
     * @param height String whit the name piece
     * @return The Image of the piece
     */
    public static Image getPiece(String height) {
        return piecesImages.get(height.toUpperCase());
    }

    /**
     * Method used to get the image of the board
     *
     * @author Gianluca Regis
     * @return The Image of the board
     */
    public static Image getBoard() {
        return board;
    }

    /*public static void main (String[] args) {

        for (Height height : Height.values()) {
            System.out.println(height.toString());
        }

    }*/

}
