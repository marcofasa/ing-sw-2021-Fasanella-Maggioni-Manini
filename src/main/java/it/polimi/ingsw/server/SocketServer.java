package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

/**
 * Class to handle new socket connections
 */
public class SocketServer implements Runnable{

    private ServerSocket serverSocket;
    private final Server server;
    private final ExecutorService executor;
    private Integer nextClientID;
    private volatile boolean quit = false;

    /**
     * Constructor of the class
     * @param port port of the server
     * @param server runner of the class
     */
    public SocketServer(Integer port, Server server) {
        this.server = server;
        nextClientID = 0;
        executor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Accepts connections of the new clients
     */
    @Override
    public void run() {
        System.out.println("Server is now open");
        executor.submit(this::quitHandler);
        while (!quit) {
            try {
                VirtualClient virtualClient = new VirtualClient(serverSocket.accept(), server, nextClientID);
                System.out.println("Player " + virtualClient + " is now connected to the server");
                nextClientID++;
                executor.submit(virtualClient);
            } catch (SocketException ex) {
                System.out.println("Shutting down server");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exit(0);
    }

    /**
     * Handles server shutdown
     */
    private void quitHandler() {
        final Scanner in = new Scanner(System.in);
        while(!quit){
            if(in.nextLine().equals("quit")){
                quit = true;
                try {
                    server.disconnectAllPlayers();
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
