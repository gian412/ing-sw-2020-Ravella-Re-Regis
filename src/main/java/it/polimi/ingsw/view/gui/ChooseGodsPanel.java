package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ChooseGodsPanel extends JPanel implements Runnable {

    private final int imageBaseWidth = 84;
    private final int imageBaseHeight = 141;
    private final int playerNumber;

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
            switch (message.getStatus()) {
                case SELECTING_GOD:
                    if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
                        if (message.getChoosingGods().equals("")) {
                            displayPanel.showAllGods();
                        } else {
                            // Chose your god
                        }
                    }
                case ADDING_WORKER:
                case PLAYING:
                case TERMINATOR:
            }
        }
    }

    public ChooseGodsPanel(Socket connSocket, int playerNumber) {
        this.playerNumber = playerNumber;
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
            String path = "src/main/java/it/polimi/ingsw/utils/graphics/";
            for(int i = 0; i < 14; i++){

                Image image = ImageIO.read(new File(path + GodType.values()[i].getCapitalizedName() + ".png"));
                image = image.getScaledInstance(imageBaseWidth, imageBaseHeight, Image.SCALE_DEFAULT);
                JButton imageButton = new JButton(new ImageIcon(image));

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //todo: implement action listener to send gods list
                    }
                });

                this.add(imageButton, setConstraint(i%7, i/7));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  GridBagConstraints setConstraint(int gridX, int gridY){
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = gridX;
        cons.gridy =  gridY;
        cons.weightx = 0.142;
        cons.weighty = 0.5;

        return cons;
    }
}
