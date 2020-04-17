package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.BoardProxy;
import it.polimi.ingsw.model.Game;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteViewTest {

    @Test
    @DisplayName("Checking the correct behavior at startGame")
    public void checkStartGame(){
        Thread srv = new Thread(() -> {

        });

        Thread client = new Thread(() -> {
            Socket clientSocket;
            try {
                clientSocket = new Socket("127.0.0.1", 7007);
                System.out.println("Connected");
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                BoardProxy myProxy = (BoardProxy) in.readObject();
                System.out.println(myProxy.toString());
                System.out.println(myProxy.getChoosingGods());
                System.out.println(myProxy.getWinner());

                assertTrue(myProxy.getChoosingGods() == "Gianluca", "Player should be the youngest");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });

        //srv.start();


        Socket rvSocket = null;
        try {
            ServerSocket srvSck = new ServerSocket(7007);
            System.err.println("Server up");

            client.start();

            rvSocket = srvSck.accept();
            System.err.println("Connected");
            Thread.sleep(150);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        Game g = new Game();
        Controller controller = new Controller(g);

        controller.addPlayer("Marco", 35);
        controller.addPlayer("Gianluca", 31);

        System.err.println("Instancing remoteview");
        RemoteView rv = new RemoteView(rvSocket, controller, "Marco");

        System.out.println("adding observer");
        g.getBoard().addView(rv);

        System.err.println("Starting game");
        controller.startGame();
    }
}
