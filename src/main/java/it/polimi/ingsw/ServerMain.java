package it.polimi.ingsw;

import it.polimi.ingsw.view.Server;
import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {
        Server server; // Istanzio il server
        server = new Server(); // Inizializzo il server
        server.run(); // Faccio partire il server

    }
}
