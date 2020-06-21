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
import java.awt.image.BufferedImage;
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
    String path = "src/main/java/it/polimi/ingsw/utils/graphics/";

    Socket socket;
    readProxyBoard reader;
    BoardListener listener;
    ObjectOutputStream outputStream;

    /**
     * Inner class to observe the BoardListener object
     * and display the elements that arrive from the socket
     *
     * @author Elia Ravella, Gianluca Regis
     */
    class readProxyBoard implements Observer<BoardProxy> {

        ChooseGodsPanel displayPanel;

        public readProxyBoard(ChooseGodsPanel displayPanel) {
            this.displayPanel = displayPanel;
        }

        /**
         * classic update function in the Observer pattern: it receives a BoardProxy object
         * and interprets the content
         * @param message object of the update
         * @author Elia Ravella, Gianluca Regis
         */
        @Override
        public void update(BoardProxy message) {
            switch (message.getStatus()) {
                // if the BoardProxy signals a "selecting_god status", a grid with all available gods must be shown
                case SELECTING_GOD:
                    clearView();
                    if(message.getTurnPlayer().equals(StaticFrame.getPlayerName())){
                        if(message.getChoosingGods().equals("")){ // this happens when the player is in charge of choosing ALL the gods
                            showGodButtons();
                        } else {
                            showGodButtons(message.getChoosingGods());
                        }
                    }
                    break;

                // during the "adding_worker" phase the user should see the board
                case ADDING_WORKER:
                    showBoard();
                    break;
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

    /**
     * loads a basic panel in the staticFrame
     *
     * @author Elia Ravella, Gianluca Regis
     */
    public void setUpUI() {
        this.setLayout(new GridBagLayout());
        reader = new readProxyBoard(this);
        StaticFrame.addPanel(this);

    }

    /**
     * loads and show a grid with ALL the gods available to be chosen
     *
     * @author Elia Ravella, Gianluca Regis
     */
    public void showGodButtons(){
        clearView();
        try {
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
                        chooseGod = ""; // Clear the list of chosen Gods
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

    /**
     * overload of the previous method, just with a reduced set of gods
     * @param gods the gods to be shown
     * @author Elia Ravella, Gianluca Regis
     */
    public void showGodButtons(String gods) {
        clearView();
        try {
            String path = "src/main/java/it/polimi/ingsw/utils/graphics/";
            for(int i = 0; i < gods.split(" ").length; i++){

                String actualGod = gods.split(" ")[i];
                actualGod = actualGod.trim();

                Image image = ImageIO.read(new File(path + actualGod +  ".png"));
                image = image.getScaledInstance(imageBaseWidth, imageBaseHeight, Image.SCALE_DEFAULT);

                JButton imageButton = new JButton(new ImageIcon(image));
                imageButton.setName(actualGod);

                imageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ((JButton)e.getSource()).setEnabled(false); // ... get God's name from the button...
                        chooseGod = ((JButton) e.getSource()).getName(); // ... and save it

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

    /**
     * removes all component from this panel
     * @author Elia Ravella
     */
    private void clearView() {
        for (Component component : this.getComponents())
            this.remove(component);
    }

    /**
     * sets up a basic "GridBagConstraint" object. used during the aligning of the objects in the grid
     * @param gridX column
     * @param gridY row
     * @return a GridBagConstraints objects
     * @author Elia Ravella, Gianluca Regis
     */
    private  GridBagConstraints setConstraint(int gridX, int gridY){
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = gridX;
        cons.gridy =  gridY;
        cons.weightx = 0.142;
        cons.weighty = 0.5;

        return cons;
    }

    /**
     * loads and shows the game board
     * @author Elia Ravella
     */
    private void showBoard(){
        clearView();
        try{
            BufferedImage image = ImageIO.read(new File(path + "_board.png"));
            super.paintComponent(this.getGraphics());
            this.getGraphics().drawImage(image, 0, 0, this);
        }catch(Exception x){
            x.printStackTrace();
        }
    }
}
