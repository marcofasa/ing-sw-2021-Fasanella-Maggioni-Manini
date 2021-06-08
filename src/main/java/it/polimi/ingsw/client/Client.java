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
import static java.lang.Thread.sleep;
import static java.time.LocalTime.now;


public class Client {

    private volatile static boolean connected = false;
    private final Boolean debug;
    private ConnectionInfo connectionInfo;
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
    private final ArrayList<BriefModel> players;
    private final HashMap<String, BriefModel> modelByNickname;
    private String nickname = "";
    public static Semaphore connectionSetupSemaphore = new Semaphore(0);
    private String ip;
    private volatile boolean running;


    public Client(Boolean cli, Boolean debug) {
        this.debug = debug;
        players = new ArrayList<>();
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

    public void startConnectionAndListen(String ip, int port, String nickname) throws IOException {
        clientSocket = new Socket(ip, port);
        clientSocket.setSoTimeout(5000);
        connected = true;
        outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
        inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
        send(new SetupConnection(nickname));
        ServerMessage inputClass;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::startHeartBeat, 500, 2500, TimeUnit.MILLISECONDS);
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
                        } catch (RequestTimeoutException e) {
                            getView().displayTimeoutError();
                        } catch (ExecutionException | InterruptedException ignored) {
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

    private void startHeartBeat() {
        send(new ClientKeepAlive());
    }


    private void handleResponse(ServerMessage finalInputClass) throws RequestTimeoutException, ExecutionException, InterruptedException {
        timeoutHandler.tryDisengage(finalInputClass.getTimeoutID());
        finalInputClass.read(clientCommandDispatcher);
        timeoutHandler.defuse(finalInputClass.getTimeoutID());
    }

    public void send(ClientMessage clientMessage) {
        executors.submit(() -> sendExecutor(clientMessage));
    }

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
     * @param clientMessage    message to be sent
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
        boolean debug = false;
        boolean CLI = true;
        for (String arg :
                args) {
            switch (arg) {
                case "--h" -> {
                    System.out.println("--d to start in debug");
                    System.out.println("--g to start in GUI");
                    return;
                }
                case "--d" -> {
                    System.out.println("debug mode on");
                    debug = true;
                }
                case "--g" -> CLI = false;
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

    private void parametersSetup(boolean CLI) {
        Client client = this;
        if(!CLI) {
            try {
                Client.connectionSetupSemaphore.acquire();
            } catch (InterruptedException h) {
                h.printStackTrace();
            }
            System.out.println("Waiting Semaphore");
        }
        client.connectionInfo = client.getView().getConnectionInfo();
        client.port = client.connectionInfo.getPort();
        client.ip = client.connectionInfo.getIp();
        client.nickname = client.connectionInfo.getNickname();
    }

    public ViewInterface getView() {
        return view;
    }

    public ClientTimeoutHandler getTimeoutHandler() {
        return timeoutHandler;
    }

    public void setConnected(boolean connected) {
        Client.connected = connected;
    }

    public void closeStream() {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void printModelByPlayer(String nickname) {
        System.out.println(modelByNickname.get(nickname).toString());
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

}
