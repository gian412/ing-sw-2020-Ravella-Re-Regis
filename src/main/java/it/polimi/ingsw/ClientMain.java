package it.polimi.ingsw;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.gui.GUILoader;
import sun.net.util.IPAddressUtil;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

public class ClientMain {

    public static void main(String[] args) {

        if(args.length == 1) {
            if(args[0].equals("cli")) {
                Client client = new Client(); // Inizializzo il client
                try {
                    client.run(); // Faccio partire il client
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid CLI parameters");
            }
        } else if (args.length == 3 && args[0].equals("cli")){
            Client client = new Client();

            //checking validity of IP address
            if(IPAddressUtil.isIPv4LiteralAddress(args[1])) client.setIP(args[1]);
            else {
                System.out.println("Invalid address!");
                return;
            }

            //check validity of PORT parameter
            int port;
            try{
                 port = Integer.parseInt(args[2]);
            } catch (Exception x){
                System.out.println("Not a port");
                return;
            }

            if(port > 1024 && port < 65535)
            {
                client.setPORT(port);
                try{
                    client.run();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("Not a valid port");
                return;
            }
        } else {
            GUILoader.loadGUI();
        }
    }

}
