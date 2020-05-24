package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class LoginPanel extends JPanel {
    // login page components
    JTextField txtName, txtAge;
    JButton btnLogin;

    // next panel to be loaded
    GamePanel nextPanel;

    /**simple constructor
     *
     * initializes and instances the GUI elements of the login page and displays them
     *
     * @author Elia Ravella
     * @param toDisplay next panel to be loaded
     */
    public LoginPanel(GamePanel toDisplay){
        this.nextPanel = toDisplay;
        txtName = new JTextField("Name");
        txtAge = new JTextField("Age");
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> login());

        add(txtName);
        add(txtAge);
        add(btnLogin);

    }

    /**
     * login function
     *
     * connects to the server, send the arguments (player's name and player's age) and loads next panel
     * @author Elia Ravella
     */
    public void login(){
        Socket connSocket = null;
        try {
            connSocket = new Socket("127.0.0.1", 1337);
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
