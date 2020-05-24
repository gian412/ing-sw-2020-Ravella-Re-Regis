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

public class GamePanel{
    Socket socket;
    JPanel gamePanel;
    readProxyBoard reader;

    class readProxyBoard implements Observer<BoardProxy>{
        JTextField displayText;

        public readProxyBoard(JTextField displayText){
            this.displayText = displayText;
        }

        @Override
        public void update(BoardProxy message) {
            displayText.setText(message.getTurnPlayer());
        }
    }

    public GamePanel(JPanel panel, Socket connSocket) throws IOException {
        this.gamePanel = panel;
        this.socket = connSocket;

        JTextField txtChoosingGod = new JTextField();
        reader = new readProxyBoard(txtChoosingGod);
        JLabel lblDescription = new JLabel("This player is choosing the gods: ");

        BoardListener listener = new BoardListener(new ObjectInputStream(connSocket.getInputStream()));
        new ObjectOutputStream(socket.getOutputStream());
        listener.addObserver(reader);
        new Thread(listener).start();

        panel.add(lblDescription);
        panel.add(txtChoosingGod);

    }
}
