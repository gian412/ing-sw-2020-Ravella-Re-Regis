package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Game;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class RemoteViewTest {

    @Test
    @DisplayName("Checking the correct behavior at startGame")
    /**
     * in this test I check the correct order of the Players during the startGame procedure
     * but i make this check as seen by the client. in this test i don't test any of the
     * Server class functionality
     *
     * @authors Ravella Elia
     */
    public void checkStartGame(){
        Thread server = new Thread(() -> {
            try {
                // setting up a server
                ServerSocket serverSocket = new ServerSocket(7007);
                Socket myClient = serverSocket.accept();
                System.out.println("[SERVER]Client connected, starting game");

                // after the client connection, instancing and starting the game
                Game myGame = new Game();
                Controller controller = new Controller(myGame);

                // adding player: the youngest should receive the "modified" board at startup
                controller.addPlayer("Marco", 20);
                controller.addPlayer("Gianluca", 15);

                // Instancing the remoteView
                RemoteView rv = new RemoteView(myClient, controller, "Gianluca");
                myGame.getBoard().addView(rv);

                // starting the game
                Thread.sleep(1500); // little delay to "wait for the client"
                controller.startGame();


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

        server.start();

        // client code
        Socket clientSocket;
        try {
            // connecting to the server
            clientSocket = new Socket("127.0.0.1", 7007);

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            BoardProxy proxy = (BoardProxy) inputStream.readObject();

            assertEquals(proxy.getChoosingGods(), "Gianluca");
            assertNull(proxy.getWinner());

            System.out.println("[CLIENT]\n" + proxy.toString());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
