package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class GUILoader extends JFrame{

    /**
     * @author Elia Ravella
     * @param args cli arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        JPanel panel = new JPanel();
        new LoginGridBagPanel(panel);

        mainFrame.add(panel);
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
