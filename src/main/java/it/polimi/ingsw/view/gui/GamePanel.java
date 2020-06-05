package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePanel extends JPanel implements Runnable {

    Socket socket;
    readProxyBoard reader;
    BoardListener listener;
    ObjectOutputStream outputStream;

    static class readProxyBoard implements Observer<BoardProxy> {

        JTextField displayText;

        public readProxyBoard(JTextField displayText) {
            this.displayText = displayText;
        }

        @Override
        public void update(BoardProxy message) {
            displayText.setText(message.getTurnPlayer());
        }

    }

    public GamePanel(Socket connSocket) {
        this.socket = connSocket;
        setUpUI();
        StaticFrame.mainFrame.repaint();
    }

    @Override
    public void run() {


        try {
            listener = new BoardListener(new ObjectInputStream(socket.getInputStream()));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.addObserver(reader);
        new Thread(listener).start();

    }

    public void setUpUI() {

        StaticFrame.addPanel(this);
        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // simple info label
        JLabel lblInfo = new JLabel("this player is choosing gods: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(lblInfo, constraints);


        // textBox for IO
        JTextField txtIO = new JTextField();
        constraints.gridx = 1;
        constraints.gridy = 0;
        this.add(txtIO, constraints);

        // this textBox is also updated by the boardProxy reader
        reader = new readProxyBoard(txtIO);

    }

}
