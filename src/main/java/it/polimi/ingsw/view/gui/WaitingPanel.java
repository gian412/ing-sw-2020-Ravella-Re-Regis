package it.polimi.ingsw.view.gui;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WaitingPanel extends JPanel {

    Scanner input;

    public WaitingPanel() {
        JLabel waitingLabel = new JLabel("Waiting for other users connection");
        this.add(waitingLabel);
    }

    public void execute(Socket connSocket, Scanner input, int playerNumber) {
        try {
            this.input = input;
            input.nextLine(); // final dialog

            //load next panel
            ChooseGodsPanel chooseGodsPanel = new ChooseGodsPanel(connSocket, playerNumber);
            StaticFrame.removePanel(this);
            StaticFrame.addPanel(chooseGodsPanel);

            StaticFrame.refresh();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}