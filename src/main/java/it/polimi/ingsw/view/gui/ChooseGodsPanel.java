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

public class ChooseGodsPanel extends JPanel implements Runnable {

    private static final int IMAGE_BASE_WIDTH = 84;
    private static final int IMAGE_BASE_HEIGHT = 141;
    private final int playerNumber;
    private String chooseGod = "";
    private static final String PATH = "src/main/java/it/polimi/ingsw/utils/graphics/";
    
    private final Socket socket;
    private final ReadProxyBoard reader;
    private BoardListener listener;
    private ObjectOutputStream outputStream;

    /**
     * Inner class to observe the BoardListener object
     * and display the elements that arrive from the socket
     *
     * @author Elia Ravella, Gianluca Regis
     */
    class ReadProxyBoard implements Observer<BoardProxy> {

        ChooseGodsPanel displayPanel;

        public ReadProxyBoard(ChooseGodsPanel displayPanel) {
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
                    refreshView();
                    break;

                // during the "adding_worker" phase the user should see the board
                case ADDING_WORKER:
                    clearView();
                    showBoard();
                    break;
                case PLAYING:
                case TERMINATOR:
            }
        }
    }


    public ChooseGodsPanel(Socket connSocket, int playerNumber) {
        this.playerNumber = playerNumber;
        this.socket = connSocket;
        reader = new ReadProxyBoard(this);
        this.setUpUI();
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
    private void setUpUI() {
        this.refreshView();
        this.setLayout(new GridBagLayout());
    }

    /**
     * loads and show a grid with ALL the gods available to be chosen
     *
     * @author Elia Ravella, Gianluca Regis
     */
    public void showGodButtons(){
        for(int i = 0; i < 14; i++){

            String actualGod = GodType.values()[i].getCapitalizedName();

            Image image;
            JButton imageButton;
            try {
                image = ImageIO.read(new File(PATH + actualGod + ".png"))
                        .getScaledInstance(IMAGE_BASE_WIDTH, IMAGE_BASE_HEIGHT, Image.SCALE_DEFAULT);
                imageButton = new JButton(new ImageIcon(image));
            } catch (IOException e) {
                imageButton = new JButton(actualGod);
            }

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


    }

    /**
     * overload of the previous method, just with a reduced set of gods
     * @param gods the gods to be shown
     * @author Elia Ravella, Gianluca Regis
     */
    public void showGodButtons(String gods) {
        for(int i = 0; i < gods.split(" ").length; i++){

            String actualGod = gods.split(" ")[i];
            actualGod = actualGod.trim();

            if(actualGod.equals("")) continue; // in case the string is poorly formatted

            Image image;
            JButton imageButton;
            try {
                 image = (ImageIO.read(new File(PATH + actualGod + ".png")))
                         .getScaledInstance(IMAGE_BASE_WIDTH, IMAGE_BASE_HEIGHT, Image.SCALE_DEFAULT);
                 imageButton = new JButton(new ImageIcon(image));
            }catch(IOException e){
                imageButton = new JButton(actualGod);
            }

            imageButton.setName(actualGod);

            imageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (chooseGod.equals("")) {
                        ((JButton)e.getSource()).setEnabled(false); // ... get God's name from the button...
                        chooseGod = ((JButton) e.getSource()).getName(); // ... and save it
                    }
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
     * @author Elia Ravella, Gianluca Regis
     */
    private void showBoard(){
    
        //load next panel
        GamePanel gamePanel = new GamePanel(this.socket);
        StaticFrame.removePanel(this);
        StaticFrame.addPanel(gamePanel);
        new Thread(gamePanel).start();
       
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
     * reloads the view, loading all new dynamically added components
     *
     * @author Elia Ravella
     */
    private void refreshView(){
        this.invalidate();
        this.validate();
        this.repaint();
    }
}
