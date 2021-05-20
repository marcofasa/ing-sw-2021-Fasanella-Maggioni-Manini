package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.ServerTimeoutHandler;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.ClientResponse;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.controller.Game;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class VirtualClient implements Runnable{
    private final Integer clientID;
    private final Socket clientSocket;
    private final Server server;
    private final ServerTimeoutHandler timeoutHandler;
    private final VirtualClientCommandDispatcher clientCommandDispatcher;
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
        timeoutHandler = new ServerTimeoutHandler(this);
        clientCommandDispatcher = new VirtualClientCommandDispatcher(this);
    }


    public void send(ServerMessage serverMessage){
        synchronized (this) {
            try {
                outputStream.reset();
                outputStream.writeObject(serverMessage);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Send Message and waits for answer
     * @param serverMessage message to be sent
     * @param timeoutInSeconds time before RequestTimedOutException is thrown, -1 to wait indefinitely
     * @throws RequestTimeoutException thrown if timeout is exceeded.
     */
    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws RequestTimeoutException {
        try {
            timeoutHandler.sendAndWait(serverMessage, timeoutInSeconds);
        } catch (TimeoutException e) {
            System.out.println("Timeout on message expired.");
            throw new RequestTimeoutException();
        }

    }

    public void run() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ClientMessage inputClass;
            while (connected) {
   //           System.out.println("Client" + this +" is waiting...");
                inputClass = (ClientMessage) inputStream.readObject();
                ClientMessage finalInputClass = inputClass;
   //           System.out.println("Reading" + inputClass);
                try {
                    if(finalInputClass instanceof ClientResponse) {
                        timeoutHandler.tryDisengage(finalInputClass.getTimeoutID());
                        executors.submit(() -> finalInputClass.read(this)).get();
                        timeoutHandler.defuse(finalInputClass.getTimeoutID());
                    } else {
                        executors.submit(() -> finalInputClass.read(this));
                    }
                } catch (RequestTimeoutException e) {
                    server.requestTimedout(this);
                    e.printStackTrace();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
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

    public void setGame(Game game){
        this.game = game;
    }

    public Server getServer() {
        return server;
    }

    public Game getGame() {
        return game;
    }

    public VirtualClientCommandDispatcher getCommandDispatcher() {
        return clientCommandDispatcher;
    }
}
