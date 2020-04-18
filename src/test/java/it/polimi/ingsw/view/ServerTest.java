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
    public void constructorTest(){
        Server myServer = new Server();

        assertEquals(myServer.getClientsNumber(), 0);
        assertTrue(myServer.isLobbyEmpty());

    }
}
