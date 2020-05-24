package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GUILoader extends JFrame{

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        mainFrame.add(new LoginPanel());
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

}
