package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        JLayeredPane layeredPane = frame.getLayeredPane();
        File image1 = new File("/Users/gianlucaregis/Desktop/27.png");
        File image2 = new File("/Users/gianlucaregis/Desktop/15.png");

        BufferedImage myPicture1 = ImageIO.read(image1);
        BufferedImage myPicture2 = ImageIO.read(image2);

        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        picLabel1.setBounds(200, 200, 200, 200);
        JLabel picLabel2 = new JLabel(new ImageIcon(myPicture2));
        picLabel2.setBounds(300, 300, 200, 200);

        layeredPane.add(picLabel1, 1);
        layeredPane.add(picLabel2, 2);

        //frame.add(layeredPane);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(5000, 5000);
        frame.setVisible(true);

    }
}
