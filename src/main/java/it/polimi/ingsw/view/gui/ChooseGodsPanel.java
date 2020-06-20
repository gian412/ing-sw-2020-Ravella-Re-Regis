package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.Command;
import it.polimi.ingsw.controller.PlayerCommand;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Pair;
import it.polimi.ingsw.utils.CommandType;
import it.polimi.ingsw.utils.GodType;
import it.polimi.ingsw.view.BoardListener;
import it.polimi.ingsw.view.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChooseGodsPanel extends JPanel implements Runnable {

    private final int imageBaseWidth = 84;
    private final int imageBaseHeight = 141;
    private final int playerNumber;
    private String chooseGod = "";

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
            //clearView();
            switch (message.getStatus()) {
                case SELECTING_GOD:
                    if (message.getTurnPlayer().equals(StaticFrame.getPlayerName())) {
                        if (message.getChoosingGods().equals(""))
                            displayPanel.showGodButtons();
                        else {
                            if (!chooseGod.equals(message.getChoosingGods()))
                                displayPanel.showGodButtons(message.getChoosingGods());
                        }
                    }
                    break;
                case ADDING_WORKER:

                case PLAYING:
                case TERMINATOR:
            }
            //StaticFrame.mainFrame.setVisible(true);
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

    }

    public void showGodButtons(){
        try {
            String path = "src/main/java/it/polimi/ingsw/utils/graphics/";
            for(int i = 0; i < 14; i++){

                String actualGod = GodType.values()[i].getCapitalizedName();
                Image image = ImageIO.read(new File(path + actualGod  + ".png"));
                image = image.getScaledInstance(imageBaseWidth, imageBaseHeight, Image.SCALE_DEFAULT);
                JButton imageButton = new JButton(new ImageIcon(image));
                imageButton.setName(actualGod);

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (chooseGod.split(" ").length >= playerNumber) {
                            return;
                        }
                        JButton button = (JButton) e.getSource();
                        button.setEnabled(false);
                        chooseGod += (button.getName()) + " ";
                    }
                });

                this.add(imageButton, setConstraint(i%7, i/7));
            }

            JButton submit = new JButton("Submit your choice");
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        PlayerCommand commandToSend = new PlayerCommand(StaticFrame.getPlayerName(), new Command(new Pair(0, 0), CommandType.SET_GODS), 0);
                        commandToSend.setMessage(chooseGod);
                        outputStream.writeObject(commandToSend);
                        outputStream.flush();
                        commandToSend = new PlayerCommand(StaticFrame.getPlayerName(), new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);
                        outputStream.writeObject(commandToSend);
                        outputStream.flush();
                        showWaitMessage();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            this.add(submit, setConstraint(3,3 ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGodButtons(String gods) {
        try {
            String path = "src/main/java/it/polimi/ingsw/utils/graphics/";
            for(int i = 0; i < gods.split(" ").length; i++){

                String actualGod = gods.split(" ")[i];
                Image image = ImageIO.read(new File(path + actualGod +  ".png"));
                image = image.getScaledInstance(imageBaseWidth, imageBaseHeight, Image.SCALE_DEFAULT);
                JButton imageButton = new JButton(new ImageIcon(image));
                imageButton.setName(actualGod);

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((JButton)e.getSource()).setEnabled(false);
                        chooseGod = ((JButton) e.getSource()).getName();
                    }
                });

                this.add(imageButton, setConstraint(i%gods.split(" ").length, 0));
            }

            JButton submit = new JButton("Submit your choice");
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        PlayerCommand commandToSend = new PlayerCommand(StaticFrame.getPlayerName(), new Command(new Pair(0, 0), CommandType.CHOOSE_GOD), 0);
                        commandToSend.setMessage(chooseGod);
                        outputStream.writeObject(commandToSend);
                        outputStream.flush();
                        commandToSend = new PlayerCommand(StaticFrame.getPlayerName(), new Command(new Pair(0, 0), CommandType.CHANGE_TURN), 0);
                        outputStream.writeObject(commandToSend);
                        outputStream.flush();
                        showWaitMessage();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            });
            this.add(submit, setConstraint(gods.split(" ").length/2,1 ));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void clearView() {
        for (Component component : this.getComponents())
            this.remove(component);
    }
    
    private void showWaitMessage() {
        JLabel wait = new JLabel("Waiting other players' action");
        this.add(wait);
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
