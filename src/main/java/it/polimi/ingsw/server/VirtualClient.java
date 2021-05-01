package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.server.ServerStringMessageForTesting;
import it.polimi.ingsw.controller.Game;

import java.io.*;
import java.net.Socket;

public class VirtualClient implements Runnable{
    private final Integer clientID;
    private final Socket clientSocket;
    private final Server server;
    private Game game;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private boolean connected;

    public VirtualClient(Socket socket, Server server, Integer clientID) {
        this.clientSocket = socket;
        this.server = server;
        this.clientID = clientID;
        connected = true;
    }

    public void send(String string){
        try {
            outputStream.reset();
            outputStream.writeObject(new ServerStringMessageForTesting(string, null));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ClientMessage inputClass;
            while (connected) {
                inputClass = (ClientMessage) inputStream.readObject();
                inputClass.read(this);
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
