package it.polimi.ingsw.view;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {

        Client client = new Client(); // Inizializzo il client
        try{
            client.run(); // Faccio partire il client
        }catch (IOException e){
            System.err.println(e.getMessage());
        }

    }

}
