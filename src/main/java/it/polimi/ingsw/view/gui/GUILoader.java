package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GUILoader extends JFrame{

    /**
     * @author Elia Ravella
     * @param args cli arguments
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        GamePanel board = new GamePanel();
        LoginPanel login = new LoginPanel(board);

        mainFrame.add(login);
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
