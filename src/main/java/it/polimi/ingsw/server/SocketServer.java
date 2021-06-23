package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to handle new socket connections
 */
public class SocketServer implements Runnable{

    private ServerSocket serverSocket;
    private final Integer port;
    private final Server server;
    ExecutorService executor;
    private Integer nextClientID;

    /**
     * Constructor of the class
     * @param port port of the server
     * @param server runner of the class
     */
    public SocketServer(Integer port, Server server) {
        this.port = port;
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
        while (true) {
            try {
                VirtualClient virtualClient = new VirtualClient(serverSocket.accept() , server, nextClientID);
                System.out.println("Player " + virtualClient + " is now connected to the server");
                nextClientID++;
                executor.submit(virtualClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
