package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.communication.client.ClientKeepAlive;
import it.polimi.ingsw.communication.server.ServerKeepAlive;
import it.polimi.ingsw.communication.timeout_handler.ClientTimeoutHandler;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.SetupConnection;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.BriefModel;
import javafx.application.Application;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

import static java.lang.System.exit;


public class Client {

    private volatile static boolean connected = false;
    private final Boolean debug;
    private int port;
    private final ClientTimeoutHandler timeoutHandler;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket clientSocket;
    private final ClientCommandDispatcher clientCommandDispatcher;
    private final ViewInterface view;
    private final ExecutorService executors;
    private ArrayList<String> playersNickname;
    private final LightModel lightModel;
    private final HashMap<String, BriefModel> modelByNickname;
    private String nickname = "";
    public static final Semaphore connectionSetupSemaphore = new Semaphore(0);
    private String ip;
    private volatile boolean running;
    private ScheduledFuture<?> heartBeatExecutor;

    /**
     * Main client constructor, builds the client maps and starts the UI
     * @param cli true if CLI interface, false if GUI
     * @param debug true if debug mode, false otherwise
     */
    public Client(Boolean cli, Boolean debug) {
        this.debug = debug;
        this.lightModel = new LightModel(this);
        executors = Executors.newCachedThreadPool();
        this.clientCommandDispatcher = new ClientCommandDispatcher(this);
        this.timeoutHandler = new ClientTimeoutHandler(this);
        modelByNickname = new HashMap<>();
        running = true;
        if (cli) {
            view = new CLI(this, debug);
        } else {
            GUI gui= new GUI();
            gui.setClient(this);
            view = gui;
            executors.submit(() -> Application.launch(GUI.class, ""));
        }
    }

    /**
     * this method starts the connection to the server and the heartbeat
     * @param ip of the server
     * @param port of the server
     * @param nickname of this client
     * @throws IOException in case of socket.close() failure
     */
    public void startConnectionAndListen(String ip, int port, String nickname) throws IOException {
        clientSocket = new Socket(ip, port);
        clientSocket.setSoTimeout(5000);
        connected = true;
        outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        send(new SetupConnection(nickname));
        ServerMessage inputClass;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        heartBeatExecutor = executor.scheduleAtFixedRate(this::startHeartBeat, 500, 2500, TimeUnit.MILLISECONDS);
        while (connected) {
            try {
                inputClass = (ServerMessage) inputStream.readObject();
                ServerMessage finalInputClass = inputClass;
                if(!(inputClass instanceof ServerKeepAlive) && debug)
                    System.out.println(finalInputClass.toString());
                if (inputClass instanceof ServerResponse) {
                    executors.submit(() -> {
                        try {
                            handleResponse(finalInputClass);
                        } catch (RequestTimedOutException e) {
                            getView().displayTimeoutError();
                        }
                    });
                } else {
                    executors.submit(() -> finalInputClass.read(clientCommandDispatcher));
                }
            } catch (IOException | ClassNotFoundException ignored) {
            }
        }
        clientSocket.close();
    }

    /**
     * method repeated every 2.5 seconds as heartbeat to the server
     */
    private void startHeartBeat() {
        send(new ClientKeepAlive());
    }

    /**
     * Handles a ServerResponse received from the server
     * @param finalInputClass message received
     * @throws RequestTimedOutException thrown if this response is received too late and the timeout has been reached
     */
    private void handleResponse(ServerMessage finalInputClass) throws RequestTimedOutException {
        timeoutHandler.tryDisengage(finalInputClass.getTimeoutID());
        finalInputClass.read(clientCommandDispatcher);
        timeoutHandler.defuse(finalInputClass.getTimeoutID());
    }

    /**
     * Send message to the Server
     * @param clientMessage to be sent
     */
    public void send(ClientMessage clientMessage) {
        executors.submit(() -> sendExecutor(clientMessage));
    }

    /**
     * slave method of Client.send
     * @param clientMessage to be sent
     */
    private synchronized void sendExecutor(ClientMessage clientMessage) {
        if(!(clientMessage instanceof ClientKeepAlive) && debug)
            System.out.println(clientMessage.toString());
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
     *
     * @param clientMessage message to be sent
     * @param timeoutInSeconds time before RequestTimedOutException is thrown, -1 to wait indefinitely
     * @throws RequestTimedOutException thrown if timeout is exceeded.
     */
    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws RequestTimedOutException {
        try {
            timeoutHandler.sendAndWait(clientMessage, timeoutInSeconds);
        } catch (TimeoutException e) {
            System.out.println("Timeout on message expired.");
            throw new RequestTimedOutException();
        }

    }

    /**
     * gets parameters for the connection to the server
     * @param CLI true if UI is CLI
     */
    private void parametersSetup(boolean CLI) {
        if(!CLI) {
            try {
                Client.connectionSetupSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ConnectionInfo connectionInfo = getView().getConnectionInfo();
        port = connectionInfo.getPort();
        ip = connectionInfo.getAddress();
        nickname = connectionInfo.getNickname();
    }

    public ViewInterface getView() {
        return view;
    }

    public ClientTimeoutHandler getTimeoutHandler() {
        return timeoutHandler;
    }

    /**
     * set connected toggle, this stops this client from receiving any more messages
     * @param connected to be set
     */
    public void setConnected(boolean connected) {
        if(!connected){
            heartBeatExecutor.cancel(true);
        }
        Client.connected = connected;
    }

    public void closeStream() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes an array of nicknames and associates them with empty BriefModels, to be adjourned
     * every turn from the server.
     * @param playersNickname
     */
    public void setPlayersNicknames(ArrayList<String> playersNickname) {
        this.playersNickname = playersNickname;
        for (String nickname :
                playersNickname) {
            modelByNickname.put(nickname, new BriefModel());
        }
    }

    public LightModel getLightModel() {
        return lightModel;
    }

    public void setModelForPlayer(BriefModel briefModel, String nickname) {
        modelByNickname.put(nickname, briefModel);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getPlayersNickname() {
        return playersNickname;
    }

    public BriefModel getModelByNickname(String nickname) {
        return modelByNickname.get(nickname);
    }

    /**
     * Method to kill the connection to the server and ultimately this client.
     */
    public void killConnection(){
        running = false;
        try {
            clientSocket.close();
        } catch (IOException ignore) {
        }
        try {
            inputStream.close();
        } catch (IOException ignore) {
        }
        try {
            outputStream.close();
        } catch (IOException ignore) {
        }
        exit(0);
    }

    public boolean isDebug() {
        return debug;
    }

    public static void main(String[] args) {
        boolean debug = false;
        boolean CLI = false;
        for (String arg :
                args) {
            switch (arg) {
                case "--h" -> {
                    System.out.println("--d to start in debug");
                    System.out.println("--c to start in CLI");
                    return;
                }
                case "--d" -> {
                    System.out.println("debug mode on");
                    debug = true;
                }
                case "--c" -> CLI = true;
            }
        }
        Client client = new Client(CLI, debug);
        System.out.println("Client has started");
        client.parametersSetup(CLI);
        while (client.running) {
            try {
                boolean finalCLI = CLI;
                client.executors.submit(() -> {
                    try {
                        client.startConnectionAndListen(client.ip, client.port, client.nickname);
                    } catch (IOException e) {
                        client.getView().displayConnectionError();
                        Client.connected = false;
                        client.getView().displayServerUnreachable();
                        client.parametersSetup(finalCLI);
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            client.parametersSetup(CLI);
        }
    }
}
