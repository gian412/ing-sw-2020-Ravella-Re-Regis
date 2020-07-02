package it.polimi.ingsw;

import it.polimi.ingsw.view.Client;
import it.polimi.ingsw.view.gui.GUILoader;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientMain {

    public static void main(String[] args) {

        switch (args.length) {
            case 0:
                GUILoader.loadGUI();
                break;
            case 1:
                if(args[0].equals("cli")) {
                    Client client = new Client(); // Initialize client
                    try {
                        client.run(); // Start client
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.println("Invalid CLI parameter.");
                    System.out.println("Insert 'cli' as a parameter to run CLI version or nothing to run GUI version");
                }
                break;
            case 2:
                if (args[0].equals("cli")) {

                    Client client = new Client();

                    //checking validity of IP address
                    if(isIPv4(args[1])) {
                        client.setIP(args[1]);
                        try{
                            client.run();
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.err.println("'" + args[1] + "' " + "is not a valid IP address");
                    }
                } else {
                    System.err.println("Invalid CLI parameters.");
                    System.out.println("Insert 'cli' as a parameter to run CLI version or nothing to run GUI version. " +
                            "After 'cli' insert IP address to use a custom IP or nothing to use the default IP (127.0.0.1)");
                }
                break;
            case 3:
                if (args[0].equals("cli")) {

                    Client client = new Client();

                    //checking validity of IP address
                    if(isIPv4(args[1])) {
                        client.setIP(args[1]);
                    } else {
                        System.err.println("'" + args[1] + "' " + "is not a valid IP address.");
                        return;
                    }

                    //check validity of PORT parameter
                    int port;
                    try{
                        port = Integer.parseInt(args[2]);
                    } catch (Exception x){
                        System.err.println( "'" + args[2] + "' " + "is not a port");
                        return;
                    }

                    if(port > 1024 && port < 65535) {
                        client.setPORT(port);
                        try{
                            client.run();
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        System.err.println("'" + args[2] + "' " + "is not a valid port");
                    }

                } else {
                    System.err.println("Invalid CLI parameters.");
                    System.out.println("Insert 'cli' as a parameter to run CLI version or nothing to run GUI version. " +
                            "After 'cli' insert IP address to use a custom IP or nothing to use the default IP (127.0.0.1). " +
                            "After IP address insert a valid port number between 1025 and 65534 to use a custom port or " +
                            "nothing to use the default port (13300)");
                }
                break;
            default:
                System.err.println("Invalid CLI parameters.");
                System.out.println("Insert 'cli' as a parameter to run CLI version or nothing to run GUI version. " +
                        "After 'cli' insert IP address to use a custom IP or nothing to use the default IP (127.0.0.1). " +
                        "After IP address insert a valid port number between 1025 and 65534 to use a custom port or " +
                        "nothing to use the default port (13300)");
                break;
        }
    }

    public static boolean isIPv4(String ipAddress) {

        boolean isIPv4 = false;

        if (ipAddress != null) {
            try {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                isIPv4 = (inetAddress instanceof Inet4Address) && inetAddress.getHostAddress().equals(ipAddress);
            } catch (UnknownHostException ex) {}
        }
        return isIPv4;
    }

}
