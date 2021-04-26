package it.polimi.ingsw.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer implements Runnable{

    private ServerSocket serverSocket;
    private Integer port;
    private Server server;
    ExecutorService executor;
    private Integer nextClientID;

    public SocketServer(Integer port, Server server) {
        this.port = port;
        this.server = server;
        executor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                VirtualClient virtualClient = new VirtualClient(serverSocket.accept() , server, nextClientID);
                nextClientID++;
                executor.submit(virtualClient);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
