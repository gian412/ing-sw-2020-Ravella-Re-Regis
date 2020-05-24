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

public class LoginGridBagPanel extends JPanel {

    // login page components
    JTextField txtName, txtAge;
    JLabel txtError;
    JButton btnLogin;

    // next panel to be loaded
    GamePanel nextPanel;

    /**simple constructor
     *
     * initializes and instances the GUI elements of the login page and displays them
     *
     * @author Gianluca Regis
     * @param toDisplay next panel to be loaded
     */
    public LoginGridBagPanel(GamePanel toDisplay){

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        // Set panel size
        this.setSize(2000, 2000);

        // Set next panel
        this.nextPanel = toDisplay;

        // Initialization of the textFields
        txtName = new JTextField("Name");
        //txtName.setBounds(100, 50, 100, 50);
        txtAge = new JTextField("Age");
        //txtAge.setBounds(250, 50, 100, 50);
        txtError = new JLabel();
        //txtError.setBounds(250, 150, 100, 50);
        txtError.setVisible(false);

        // Adding focus listeners
        txtName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txtName.setText("");
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
                txtAge.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtAge.getText().equals("")) {
                    txtAge.setText("Age");
                } else {
                    txtAge.setBorder(new LineBorder(Color.DARK_GRAY, 0));
                    txtError.setText("");
                    txtError.setVisible(false);
                }
            }
        });

        // Initialization of the login button
        btnLogin = new JButton("Login");
        //btnLogin.addActionListener(e -> login());
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
                txtError.setText("Invalid age parameter");
                txtError.setVisible(true);
            }
        });

        // Adding elements to Panel
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        add(txtName, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        add(txtAge, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        add(txtError, constraints);

        constraints = new GridBagConstraints();
        constraints.fill= GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.33;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        add(btnLogin, constraints);

    }

    /**
     * login function
     *
     * connects to the server, send the arguments (player's name and player's age) and loads next panel
     * @author Elia Ravella
     */
    public void login(){
        Socket connSocket;
        try {
            // Declaration of connection's constants
            String IP = "127.0.0.1";
            int PORT = 1337;
            connSocket = new Socket(IP, PORT);
            Scanner input = new Scanner(connSocket.getInputStream());
            PrintStream output = new PrintStream(connSocket.getOutputStream());

            output.println(txtName.getText());
            output.flush();
            output.println(Integer.parseInt(txtAge.getText()));
            output.flush();
            input.nextLine();
            input.nextLine();

            remove(txtName);
            remove(txtAge);
            remove(btnLogin);
            add(nextPanel);
            nextPanel.activeGamePanel(connSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

}
