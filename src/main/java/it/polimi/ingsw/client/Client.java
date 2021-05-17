package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.communication.ClientTimeoutHandler;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.SetupConnection;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;


public class Client {

    private volatile static boolean connected;
    private final ClientTimeoutHandler timeoutHandler;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket clientSocket;
    private ClientCommandDispatcher clientCommandDispatcher;
    private final ViewInterface view;
    private final ExecutorService executors;


    public Client(Boolean cli){
        executors = Executors.newCachedThreadPool();
        this.clientCommandDispatcher = new ClientCommandDispatcher(this);
        this.timeoutHandler = new ClientTimeoutHandler(this);
        if(cli){
            view = new CLI(this);
        } else {
            view = new GUI();
        }
    }

    public void startConnectionAndListen(String ip, int port, String nickname) {
        try {
            clientSocket = new Socket(ip, port);
            connected = true;
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            send(new SetupConnection(nickname));
            ServerMessage inputClass = null;
            while (connected) {
                try {
                    inputClass = (ServerMessage) inputStream.readObject();
                    ServerMessage finalInputClass = inputClass;
                    if(inputClass instanceof ServerResponse) {
                        timeoutHandler.tryDisengage(inputClass.getTimeoutID());
                        executors.submit(() -> {
                            finalInputClass.read(clientCommandDispatcher);
                        });
                        timeoutHandler.defuse(inputClass.getTimeoutID());
                    } else {
                        executors.submit(() -> {
                            finalInputClass.read(clientCommandDispatcher);
                        });
                    }
                } catch (RequestTimeoutException e) {
                    System.err.println("Request " + inputClass + " is timed out.");
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyConnected(){
        System.out.println("Il client è connesso al server");
    }

    public void send(ClientMessage clientMessage){
        try {
            outputStream.reset();
            outputStream.writeObject(clientMessage);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send Message and waits for answer
     * @param clientMessage message to be sent
     * @param timeoutInSeconds time before RequestTimedOutException is thrown, -1 to wait indefinitely
     * @throws RequestTimeoutException thrown if timeout is exceeded.
     */
    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws RequestTimeoutException {
        try {
            timeoutHandler.sendAndWait(clientMessage, timeoutInSeconds);
        } catch (TimeoutException e) {
            System.out.println("Timeout on message expired.");
            throw new RequestTimeoutException();
        }

    }

    public static void main(String[] args) {
        Client client = new Client(true);
        System.out.println("Client has started");
        int port = 25556;
        String ip = "127.0.0.1";
        client.executors.submit(()-> client.startConnectionAndListen(ip,port, client.getView().askNickName()));
    }

    public ViewInterface getView() {
        return view;
    }

    public ClientTimeoutHandler getTimeoutHandler() {
        return timeoutHandler;
    }
}
