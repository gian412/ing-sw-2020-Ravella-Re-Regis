package it.polimi.ingsw.view.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        File prove = new File("..\\..\\..\\..\\..\\..\\graphics\\graphics_0000s_0001_god_and_hero_cards_0055_Tyche.png");

        BufferedImage myPicture = ImageIO.read(prove);
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        frame.add(picLabel);

    }
}
