package it.polimi.ingsw.view.gui;


import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.utils.GodType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GetImages {

    private static final int IMAGE_BASE_WIDTH = 84;
    private static final int IMAGE_BASE_HEIGHT = 141;

    private static Image board;

    // Load Gods' images and save them in an HashMap
    private static Map<String, Image> godImages = new HashMap<>();
    private static void loadGods() {
        for (GodType godType : GodType.values()) {
            try {
                Image imgGod = (ImageIO.read(GetImages.class.getClassLoader().getResource(godType.getCapitalizedName() + ".png"))).getScaledInstance(IMAGE_BASE_WIDTH, IMAGE_BASE_HEIGHT, Image.SCALE_DEFAULT);
                godImages.put(godType.getName(), imgGod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load Workers' images and save them in an HashMap
    private static Map<String, Image> workerImages = new HashMap<>();
    private static void loadWorkers() {
        for (GodType godType : GodType.values()) {
            try {
                Image imgWorker = ImageIO.read(GetImages.class.getClassLoader().getResource(godType.getCapitalizedName() + "_Worker.png"));
                workerImages.put(godType.getName(), imgWorker);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Load Powers' images and save them in an HashMap
    private static Map<String, Image> powerImages = new HashMap<>();
    private static void loadPowers() {
        for (GodType godType : GodType.values()) {
            try {
                Image imgPower = ImageIO.read(GetImages.class.getClassLoader().getResource(godType.getCapitalizedName() + "_power.png"));
                powerImages.put(godType.getName(), imgPower);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Load board pieces and save them in an HashMap
    private static Map<String, Image> piecesImages = new HashMap<>();
    private static void loadPieces() {
        for (Height height : Height.values()) {
            if (height!=Height.GROUND) {
                try {
                    Image imgPiece = ImageIO.read(GetImages.class.getClassLoader().getResource("_" + height.toString() + ".png"));
                    piecesImages.put(height.toString(), imgPiece);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Load board image and save it in an Image
    private static void loadBoard() {
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
    public static Image getPieceImage(String height) {
        return piecesImages.get(height.toUpperCase());
    }

    public static Image getPowerImage(String actualGod) {
        return powerImages.get(actualGod.toUpperCase());
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

    public static void loadAll() {
        loadGods();
        loadWorkers();
        loadPowers();
        loadPieces();
        loadBoard();
    }

    /*private static void main (String[] args) {

        for (Height height : Height.values()) {
            System.out.println(height.toString());
        }

    }*/

}
