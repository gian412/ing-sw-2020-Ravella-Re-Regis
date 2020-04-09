package it.polimi.ingsw.view;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

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
    }
}
