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

public class ChooseGodsPanel extends JPanel implements Runnable {

    Socket socket;
    readProxyBoard reader;
    BoardListener listener;
    ObjectOutputStream outputStream;

    class readProxyBoard implements Observer<BoardProxy> {

        ChooseGodsPanel displayPanel;

        public readProxyBoard(ChooseGodsPanel displayPanel) {
            this.displayPanel = displayPanel;
        }

        @Override
        public void update(BoardProxy message) {
            if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
                displayPanel.showAllGods();
            }
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

        reader = new readProxyBoard(this);
        StaticFrame.addPanel(this);
        /*this.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        try {
            BufferedImage image = ImageIO.read(new File("/home/gianluca/IdeaProjects/ingSw/ing-sw-2020-Ravella-Re-Regis/src/main/java/it/polimi/ingsw/utils/graphics/Apollo.png"));
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            this.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }



        // this textBox is also updated by the boardProxy reader
        //reader = new readProxyBoard(txtIO);

        StaticFrame.addPanel(this);*/

    }

    public void showAllGods(){
        try {
            BufferedImage image = ImageIO.read(new File("src/main/java/it/polimi/ingsw/utils/graphics/Apollo.png"));
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            this.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
