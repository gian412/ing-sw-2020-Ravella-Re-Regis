package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class GUILoader extends JFrame{

    /**
     * @author Elia Ravella
     * @param args cli arguments
     */
    public static void main(String[] args) {
        //JFrame mainFrame = new JFrame("Santorini");
        StaticFrame.initFrame("Santorini");
        JPanel panel = new JPanel();
        new LoginGridBagPanel(panel);
        StaticFrame.addPanel(new LoginPanel());

        StaticFrame.setSize(500, 500);
        StaticFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        StaticFrame.setVisible(true);
    }

}
