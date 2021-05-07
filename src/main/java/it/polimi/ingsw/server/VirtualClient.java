package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.ClientResponse;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerStringMessageForTesting;
import it.polimi.ingsw.controller.Game;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualClient implements Runnable{
    private final Integer clientID;
    private final Socket clientSocket;
    private final Server server;
    private Game game;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean connected;
    private final ExecutorService executors;

    public VirtualClient(Socket socket, Server server, Integer clientID) {
        this.clientSocket = socket;
        this.server = server;
        this.clientID = clientID;
        connected = true;
        executors = Executors.newCachedThreadPool();
    }


    public void send(ServerMessage serverMessage){
        try {
            outputStream.reset();
            outputStream.writeObject(serverMessage);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAndWait(ServerMessage serverMessage, ClientResponse clientResponse, int timeout){
        send(serverMessage);
    }

    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ClientMessage inputClass;
            while (connected) {
                inputClass = (ClientMessage) inputStream.readObject();
                ClientMessage finalInputClass = inputClass;
                executors.submit(() -> finalInputClass.read(this));
            }
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Integer getID() {
        return clientID;
    }

    public void setupConnection(String payload) {

    }

    void SetGame(Game game){
        this.game = game;
    }

    public Server getServer() {
        return server;
    }

    public Game getGame() {
        return game;
    }
}
