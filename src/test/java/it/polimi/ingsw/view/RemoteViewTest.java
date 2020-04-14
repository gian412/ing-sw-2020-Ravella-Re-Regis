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
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteViewTest {

    @Test
    @DisplayName("Checking the correct behavior at startGame")
    public void checkStartGame(){
        Thread srv = new Thread(() -> {
            Socket rvSocket = null;
            try {
                ServerSocket srvSck = new ServerSocket(7007);
                rvSocket = srvSck.accept();
                Thread.sleep(150);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }

            Game g = new Game();
            Controller controller = new Controller(g);

            controller.addPlayer("Marco", 35);
            controller.addPlayer("Gianluca", 31);

            RemoteView rv = new RemoteView(rvSocket, controller, "Marco");
            g.getBoard().addView(rv);

            controller.startGame();
        });

        Thread client = new Thread(() -> {
            Socket clientSocket;
            try {
                 clientSocket = new Socket("127.0.0.1", 7007);
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                BoardProxy myProxy = (BoardProxy) in.readObject();
                assertTrue(myProxy.getChoosingGods() == "Gianluca", "Player should be the youngest");
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        });

        srv.start();
        client.start();
    }
}
