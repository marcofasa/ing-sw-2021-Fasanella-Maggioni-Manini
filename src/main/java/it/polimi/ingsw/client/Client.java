package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.CLI.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.communication.timeout_handler.ClientTimeoutHandler;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.SetupConnection;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.BriefModel;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;


public class Client {

    private volatile static boolean connected = false;
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
    private HashMap<String, BriefModel> modelByNickname;
    private String nickname = "";


    public Client(Boolean cli, Boolean debug) {
        players = new ArrayList<>();
        this.lightModel =new LightModel(this);
        executors = Executors.newCachedThreadPool();
        this.clientCommandDispatcher = new ClientCommandDispatcher(this);
        this.timeoutHandler = new ClientTimeoutHandler(this);
        if (cli) {
            view = new CLI(this, debug);
        } else {
            view = new GUI();
       //     Application.launch(view);
        }
        modelByNickname = new HashMap<>();
    }

    public void startConnectionAndListen(String ip, int port, String nickname) throws IOException {
        clientSocket = new Socket(ip, port);
        connected = true;
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        send(new SetupConnection(nickname));
        ServerMessage inputClass;
        while (connected) {
            try {
                inputClass = (ServerMessage) inputStream.readObject();
                ServerMessage finalInputClass = inputClass;
                if (inputClass instanceof ServerResponse) {
                    executors.submit(() -> {
                        try {
                            handleResponse(finalInputClass);
                        } catch (RequestTimeoutException e) {
                            getView().displayTimeoutError();
                        } catch (ExecutionException | InterruptedException ignored) {}
                    });
                } else {
                    executors.submit(() -> finalInputClass.read(clientCommandDispatcher));
                }
            } catch (IOException | ClassNotFoundException ignored) {}
        }
        clientSocket.close();
    }

    private void handleResponse(ServerMessage finalInputClass) throws RequestTimeoutException, ExecutionException, InterruptedException {
        timeoutHandler.tryDisengage(finalInputClass.getTimeoutID());
        finalInputClass.read(clientCommandDispatcher);
        timeoutHandler.defuse(finalInputClass.getTimeoutID());
    }

    public void notifyConnected() {
        System.out.println("Il client è connesso al server");
    }

    public void send(ClientMessage clientMessage) {
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
        Boolean debug = false;
        Boolean CLI = true;
        for (String arg :
                args) {
            switch (arg) {
                case "--h":
                    System.out.println("--d to start in debug");
                    System.out.println("--g to start in GUI");
                    break;
                case "--d":
                    System.out.println("debug mode on");
                    debug = true;
                    break;
                case "--g":
                    CLI = false;
                    break;
            }
        }
        Client client = new Client(CLI, debug);
        System.out.println("Client has started");
        int port = 25556;
        String ip = "127.0.0.1";
        while(true) {
            try {
                client.executors.submit(() -> {
                    try {
                        client.startConnectionAndListen(ip, port, client.getView().askNickName());
                    } catch (IOException e) {
                        client.getView().displayConnectionError();
                        Client.connected = false;
                    }
                }).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
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

    public void printModelByPlayer(String nickname){
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
}
