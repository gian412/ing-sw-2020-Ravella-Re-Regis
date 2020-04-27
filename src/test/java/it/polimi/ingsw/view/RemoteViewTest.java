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


    @Test
    @DisplayName("Checking the correct behavior at startGame (multithreaded)")
    public void checkStartGameMultiThread(){
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
                Thread rvThread = new Thread(rv);
                rvThread.start();

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

    @Test
    @DisplayName("disconnection of client test")
    public  void disconnectTest(){
        Thread server = new Thread(() -> {
            try {
                ServerSocket srv = new ServerSocket(7007);
                Socket connSocket1 = srv.accept(), // the one to disconnect
                        connSocket2 = srv.accept(); // the one to play (should receive a different proxy)

                Game g = new Game();
                Controller controller = new Controller(g);

                controller.addPlayer("Marco", 15);
                controller.addPlayer("Gianluca", 16);

                // Instancing the remoteView
                RemoteView rv = new RemoteView(connSocket1, controller, "Marco"),
                        rv2 = new RemoteView(connSocket2, controller, "Gianluca");

                rv.addObserver(controller);
                rv2.addObserver(controller);

                g.getBoard().addView(rv);
                g.getBoard().addView(rv2);

                Thread rvThread = new Thread(rv),
                    rv2Thread = new Thread(rv2);

                rvThread.start();
                rv2Thread.start();

                controller.startGame();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread clientToDisconnect = new Thread(() -> {
            try {
                Socket connSocket = new Socket("127.0.0.1", 7007);

                ObjectOutputStream outputStream = new ObjectOutputStream(connSocket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(connSocket.getInputStream());

                inputStream.readObject();

                // disconnecting after initial data received: this triggers a "GAME OVER" message
                System.out.println("Disconnecting client...");
                connSocket.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        server.start();
        clientToDisconnect.start();

        try {
            Socket actualClient = new Socket("127.0.0.1", 7007);

            ObjectOutputStream outputStream = new ObjectOutputStream(actualClient.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(actualClient.getInputStream());


            inputStream.readObject(); // initial data
            BoardProxy gameOver = (BoardProxy) inputStream.readObject();

            assertEquals("Unexpected Game Over", ((BoardProxy) inputStream.readObject()).getWinner().getNAME());


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

