package it.polimi.ingsw.view.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * first panel to be displayed when the application boots up, must have all login and connection configure options
 * @author Gianluca Regis
 */
public class LoginPanel extends JPanel {

    // login page components
    JTextField txtName, txtAge;
    JLabel labelName, labelAge, labelError;
    JButton btnConfigure, btnLogin;
    JPanel that = this;
    String ip = "127.0.0.1";
    int port = 13300;

    /**
     *
     * Initializes and instances the GUI elements of the login page and displays them
     *
     * @author Gianluca Regis
     *
     */
    public LoginPanel() {

        // Set panel layout
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        // Set panel size
        this.setSize(2000, 2500);

        // Initialization of the textFields
        txtName = new JTextField("Name");
        txtAge = new JTextField("Age");
        labelName = new JLabel("Name: ");
        labelAge = new JLabel("Age: ");
        labelError = new JLabel();
        labelError.setVisible(false);

        // Adding focus listeners
        txtName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtName.getText().equals("Name")) {
                    txtName.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtName.getText().equals("")) {
                    txtName.setText("Name");
                }
            }
        });
        txtAge.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtAge.getText().equals("Age")) {
                    txtAge.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtAge.getText().equals("")) {
                    txtAge.setText("Age");
                } else {
                    txtAge.setBorder(new LineBorder(Color.DARK_GRAY, 0));
                    labelError.setText("");
                    labelError.setVisible(false);
                }
            }
        });

        // Initialization of buttons
        btnConfigure = new JButton("Configure");
        btnLogin = new JButton("Login");

        // Adding action listener to buttons
        btnConfigure.addActionListener(e -> {

            // Checking IP address formatting
            boolean valid = false;
            while (!valid) {
                String strIp = JOptionPane.showInputDialog(that, "Insert IP address");
                String[] checkIp = strIp.split("[.]");
                if (checkIp.length==4) {
                    boolean checked = true;
                    for (String singleNumber : checkIp) {
                        try {
                            int singleNumberInt = Integer.parseInt(singleNumber);
                            checked = singleNumberInt>=0 && singleNumberInt<255;
                        } catch (NumberFormatException exception) {
                            checked = false;
                        }
                    }
                    ip = strIp;
                    valid = checked;
                }
            }
            valid = false;
            while (!valid) {
                try {
                    port = Integer.parseInt(JOptionPane.showInputDialog(that, "Insert port number"));
                    valid = true;
                } catch (NumberFormatException exception) {}
            }
        });
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int age;
                try {
                    age = Integer.parseInt(txtAge.getText());
                    if (age >= 1 && age <= 99) {
                        login();
                    } else {
                        txtAgeError();
                    }
                } catch (NumberFormatException error) {
                    txtAgeError();
                }

            }

            private void txtAgeError() {
                txtAge.setBorder(new LineBorder(Color.RED, 1));
                labelError.setText("Invalid age parameter");
                labelError.setVisible(true);
            }
        });

        // Adding elements to Panel

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(labelName, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(txtName, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 7;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(new JPanel(), constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 6;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(new JPanel(), constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        this.add(labelAge, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        this.add(txtAge, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 1;
        this.add(labelError, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 1;
        this.add(btnLogin, constraints);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        this.add(btnConfigure, constraints);

    }

    /**
     *
     * Login function
     *
     * connects to the server, send the arguments (player's name and player's age) and loads next panel
     * @author Elia Ravella, Gianluca Regis
     *
     */
    public void login() {

        Socket connSocket;
        try {
            // Declaration of connection's constants
            connSocket = new Socket(ip, port);
            Scanner input = new Scanner(connSocket.getInputStream());
            PrintStream output = new PrintStream(connSocket.getOutputStream());

            String connectedPlayers = input.nextLine(); // connected players
            String clientName = txtName.getText();

            while(connectedPlayers.contains(clientName))
                clientName = JOptionPane.showInputDialog("Player with name \"" + clientName + "\" already connected! change your name!");

            StaticFrame.setPlayerName(clientName);
            output.println(StaticFrame.getPlayerName());
            output.flush();
            output.println(Integer.parseInt(txtAge.getText()));
            output.flush();

            int playerNumber = 0;
            String message = input.nextLine(); // server prompt
            if(message.equals("Creating new game. How many player do you want to play with? (2 or 3 player allowed)")) {
                while (playerNumber!=2 && playerNumber!=3) {
                    playerNumber = Integer.parseInt(JOptionPane.showInputDialog(this, "How many players in the game?"));
                }
                output.println(playerNumber);
                output.flush();
            }else{
                playerNumber = Integer.parseInt(message.substring(56, 57));

                /* WaitingPanel waitingPanel = new WaitingPanel();
                StaticFrame.removePanel(this);
                StaticFrame.addPanel(waitingPanel);
                StaticFrame.refresh();
                waitingPanel.execute(connSocket, input, playerNumber);*/
            }

            input.nextLine(); // final dialog

            //load next panel
            ChooseGodsPanel chooseGodsPanel = new ChooseGodsPanel(connSocket, playerNumber);
            StaticFrame.removePanel(this);
            StaticFrame.addPanel(chooseGodsPanel);

            StaticFrame.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}