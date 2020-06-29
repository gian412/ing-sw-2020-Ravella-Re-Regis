package it.polimi.ingsw.view;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Height;
import it.polimi.ingsw.view.gui.BoardMaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BoardMakerTest {
    public static void main(String[] args) {
        // setting up the GUI
        JFrame mainFrame = new JFrame("My Frame");
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img;
                try {
                    img = ImageIO.read(new File("_board.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
                paintTowers(g, this);
            }
        };

        mainFrame.setSize(500, 500);
        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void paintTowers(Graphics g, Component obs){
        BoardMaker.drawElements(g, genBoard(), 34, 134, 24, obs);
    }

    public static BoardProxy genBoard(){
        // generating a random board
        BoardProxy bp = new BoardProxy();
        Random gen = new Random();
        for(int row = 0; row < 5; row++)
            for(int col = 0; col < 5; col++)
                if(gen.nextBoolean()) bp.addHeight(row, col, Height.FIRST_FLOOR);
                else bp.addHeight(row, col, Height.GROUND);

        return bp;

    }
}
