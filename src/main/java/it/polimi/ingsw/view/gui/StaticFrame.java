package it.polimi.ingsw.view.gui;

import javax.swing.*;

public class StaticFrame {

    public static JFrame mainFrame;

    private StaticFrame() {}

    public static void initFrame(String title) {
        mainFrame = new JFrame(title);
    }

    public static void setSize(int width, int height) {
        mainFrame.setSize(width, height);
    }

    public static void setDefaultCloseOperation(int operation) {
        mainFrame.setDefaultCloseOperation(operation);
    }

    public static void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }

    public static JLayeredPane getLayeredPane() {
        return mainFrame.getLayeredPane();
    }

    public static void addPanel(JPanel panel) {
        mainFrame.add(panel);
    }

    public static void removePanel(JPanel panel) {
        mainFrame.remove(panel);
    }





}
