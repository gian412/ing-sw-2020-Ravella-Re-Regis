package it.polimi.ingsw.view;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTest {

    @Test
    @DisplayName("Check constructor")
    public void constructorTest() throws  IOException{
        Server myServer = new Server();

        assertEquals(myServer.getClientsNumber(), 0);
        assertTrue(myServer.isLobbyEmpty());

    }

    @Test
    @DisplayName("Connection Test")
    public void connectionTest(){
        Thread srv = new Thread(new Server());

        Thread clientTest = new Thread(() -> {
            try {
                Thread.sleep(1000);
                Socket connSocket = new Socket("127.0.0.1", 1337);
                Scanner in = new Scanner(connSocket.getInputStream());
                assertEquals("Insert your name, buddy!", in.nextLine());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        });

        srv.start();
        clientTest.start();
    }
}
