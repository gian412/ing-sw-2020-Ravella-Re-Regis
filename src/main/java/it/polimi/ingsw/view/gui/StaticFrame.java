package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.utils.GodType;

import javax.swing.*;
import java.awt.*;

public class StaticFrame {

    private static String playerName;
    private static GodType god;

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
        mainFrame.add(panel, 0);
    }

    public static void removePanel(JPanel panel) {
        mainFrame.remove(panel);
    }


    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName) {
        StaticFrame.playerName = playerName;
    }

    public static GodType getGod() {
        return god;
    }

    public static void setGod(GodType god) {
        StaticFrame.god = god;
    }

    public static void refresh(){
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }
}
