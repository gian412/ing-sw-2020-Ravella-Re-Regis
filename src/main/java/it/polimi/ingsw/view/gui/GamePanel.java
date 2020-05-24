package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePanel extends JPanel {
    Socket socket;

    class readProxyBoard implements Observer<BoardProxy>{
        JPanel displayPanel;

        public readProxyBoard(JPanel displayPanel){
            this.displayPanel = displayPanel;
        }

        @Override
        public void update(BoardProxy message) {
            displayPanel.add(new JLabel(message.toString()));
        }
    }

    public void activeGamePanel() throws IOException {
        BoardListener bl = new BoardListener(new ObjectInputStream(socket.getInputStream()));
        new ObjectOutputStream(socket.getOutputStream());
        bl.addObserver(new readProxyBoard(this));
        new Thread(bl).start();
    }

    public GamePanel(Socket connSocket){
        this.socket = connSocket;
    }
}
