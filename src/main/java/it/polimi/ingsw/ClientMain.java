package it.polimi.ingsw;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.gui.GUILoader;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {

        if(args.length > 0) {
            if(args[0].equals("cli")) {
                Client client = new Client(); // Inizializzo il client
                try {
                    client.run(); // Faccio partire il client
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid CLI parameters");
            }
        } else {
            GUILoader.loadGUI();
        }
    }

}
