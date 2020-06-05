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

public class ChooseGodsPanel extends JPanel implements Runnable {

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

    public ChooseGodsPanel(Socket connSocket) {
        this.socket = connSocket;
        setUpUI();
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


        this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();



        // this textBox is also updated by the boardProxy reader
        //reader = new readProxyBoard(txtIO);

        StaticFrame.addPanel(this);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Image image = new ImageIcon("../../utils/graphics/Apollo.png").getImage();
        g.drawImage(image, 10, 10, this);
    }
}
