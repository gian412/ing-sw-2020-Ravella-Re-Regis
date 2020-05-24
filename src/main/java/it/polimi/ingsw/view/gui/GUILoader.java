package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class GUILoader extends JFrame{

    /**
     * @author Elia Ravella
     * @param args cli arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        GamePanel board = new GamePanel();
        // LoginPanel login = new LoginPanel(board);
        LoginGridBagPanel login = new LoginGridBagPanel(board);

        mainFrame.add(login);
        mainFrame.setSize(5000, 5000);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
