package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.god.Athena;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.gui.BoardMaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class test {
    public static void main(String[] args) {


        // setting up the GUI
        JFrame mainFrame = new JFrame("My Frame");
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage img;
                try {
                    img = ImageIO.read(new File("src/main/java/it/polimi/ingsw/utils/graphics/_board.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
                this.setSize(img.getWidth(), img.getHeight());
                paintTowers(g, this);
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Pair coo = BoardMaker.map(e.getX(), e.getY());
                JOptionPane.showMessageDialog(e.getComponent(), "Cell: " + coo.x + " " + coo.y);
            }
        });

        mainFrame.setSize(835, 835);
        mainFrame.add(panel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public static void paintTowers(Graphics g, Component obs){
        BoardMaker.drawElements(g, genBoard(), 19, 137, 22, obs);
    }

    public static BoardProxy genBoard(){
        // generating a random board

        BoardProxy bp = new BoardProxy();
        Random gen = new Random();

        for(int row = 0; row < 5; row++)
            for(int col = 0; col < 5; col++)
                if(gen.nextDouble() < 0.3)
                    bp.addHeight(row, col, Height.GROUND);
                else if (gen.nextDouble() < 0.5)
                    bp.addHeight(row, col, Height.FIRST_FLOOR);
                else if (gen.nextDouble() < 0.7)
                    bp.addHeight(row, col, Height.SECOND_FLOOR);
                else if (gen.nextDouble() < 0.9)
                    bp.addHeight(row, col, Height.THIRD_FLOOR);
                else
                    bp.addHeight(row, col, Height.DOME);


        HashMap<String, String> godsMap = new HashMap();
        godsMap.put("Player1", "Athena");
        godsMap.put("Player2", "Charon");
        godsMap.put("Player3", "Prometheus");
        bp.setGods(godsMap);

        bp.addWorker("Player10", new Pair(0, 1));
        bp.addWorker("Player11", new Pair(3, 3));
        bp.addWorker("Player20", new Pair(1, 1));
        bp.addWorker("Player21", new Pair(3, 4));
        bp.addWorker("Player30", new Pair(0, 3));
        bp.addWorker("Player31", new Pair(4, 4));

        return bp;


    }
}
