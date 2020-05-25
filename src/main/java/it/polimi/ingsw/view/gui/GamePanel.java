package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePanel implements Runnable{
    Socket socket;
    JPanel gamePanel;
    readProxyBoard reader;
    BoardListener listener;
    ObjectOutputStream outputStream;

    @Override
    public void run() {
        try {
            listener = new BoardListener(new ObjectInputStream(socket.getInputStream()));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        }catch(IOException x){
            x.printStackTrace();
        }

        listener.addObserver(reader);
        new Thread(listener).start();
    }

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
        setupUI(panel);
    }

    public void setupUI(JPanel pane){
        pane = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // simple info label
        JLabel lblInfo = new JLabel("this player is choosing gods: ");
        constraints.gridx = 0;
        constraints.gridx = 0;
        pane.add(lblInfo, constraints);


        // textbox for IO
        JTextField txtIO = new JTextField();
        constraints.gridx = 1;
        constraints.gridx = 0;
        pane.add(txtIO, constraints);
        // this textBox is also updated by the boardproxy reader
        reader = new readProxyBoard(txtIO);
    }
}
