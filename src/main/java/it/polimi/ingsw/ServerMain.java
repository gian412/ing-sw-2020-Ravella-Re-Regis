package it.polimi.ingsw;

import it.polimi.ingsw.view.Server;
import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {

        Server server; // Instantiate Server

        switch (args.length) {

            case 0:
                server = new Server(); // Initialize Server
                server.run(); // Run Server
                break;

            case 1:
                try {
                    int port = Integer.parseInt(args[0]);
                    if (port > 1024 && port < 65535) {
                        server = new Server(port); // Initialize server
                        server.run(); // Run server
                    } else {
                        System.err.println( "'" + args[0] + "' " + "is not a port.");
                        System.out.println("Insert a valid port number between 1025 and 65534 to use a custom port or nothing to use the default port (13300)");
                    }
                } catch (NumberFormatException e) {
                    System.err.println( "'" + args[0] + "' " + "is not a port.");
                    System.out.println("Insert a valid port number between 1025 and 65534 to use a custom port or nothing to use the default port (13300)");
                }
                break;

            default:
                System.err.println( "Invalid parameters.");
                System.out.println("Insert a valid port number between 1025 and 65534 to use a custom port or nothing to use the default port (13300)");
                break;

        }

    }
}
