package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class GUILoader extends JFrame{

    /**
     * @author Elia Ravella
     * @param args cli arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        // LoginPanel login = new LoginPanel(board);
        //LoginGridBagPanel login = new LoginGridBagPanel(board);
        LoginGridPanel login = new LoginGridPanel();

        mainFrame.add(login);
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
