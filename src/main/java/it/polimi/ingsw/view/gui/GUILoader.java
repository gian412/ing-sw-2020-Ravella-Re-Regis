package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class GUILoader extends JFrame{

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Santorini");
        Socket connSocket = null;
        try {
            connSocket = new Socket("127.0.0.1", 1337);
            Scanner x = new Scanner(connSocket.getInputStream());
            PrintStream y = new PrintStream(connSocket.getOutputStream());
            y.println("GUIclient");
            y.flush();
            y.println(1);
            y.flush();
            x.nextLine();
            x.nextLine();

            GamePanel board = new GamePanel(connSocket);
            mainFrame.add(board);
            board.activeGamePanel();

            mainFrame.setSize(500, 500);
            mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            mainFrame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
