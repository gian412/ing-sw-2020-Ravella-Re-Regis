package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {
    
    private final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
    
    public BoardPanel () {
    
    }
    
    /**
     * override of the JPanel original paint method
     * allows to do some serious custom painting
     *
     * @param g the "Graphics2D" object
     * @author Elia Ravella, Gianluca Regis
     */
    @Override
    public void paintComponent( Graphics g){
        super.paintComponent(g);
        BufferedImage img;
        try {
            img = ImageIO.read(new File(PATH + "_board.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
        
        
    }
    
}
