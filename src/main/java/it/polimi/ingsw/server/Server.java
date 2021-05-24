package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.responses.ResponseClientAccepted;
import it.polimi.ingsw.communication.server.responses.ResponseGameHasStarted;
import it.polimi.ingsw.controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final Boolean debug;
    private SocketServer socketServer;
    private final HashMap<Integer, VirtualClient> virtualClientIDMap;
    private final HashMap<VirtualClient, Game> gameMap;
    private final HashMap<VirtualClient, String> clientsNickname;
    private final HashMap<Game, Integer> gamesID;
    private int nextGameID;
    private WaitingLobby lobby;
    private Game currentGame;
    private final ExecutorService executors;
    private final Object lobbyLocked;

    public Server(Boolean debug){
        this.debug = debug;
        nextGameID = 1;
        gamesID = new HashMap<>();
        currentGame = new Game(debug);
        lobby = new WaitingLobby(this);
        clientsNickname = new HashMap<>();
        gameMap = new HashMap<>();
        virtualClientIDMap = new HashMap<>();
        executors = Executors.newCachedThreadPool();
        gamesID.put(currentGame, nextGameID);
        nextGameID++;
        lobbyLocked = new Object();
    }

    /*
    //Single Player Server
    public Server(boolean SinglePlayer){
        currentGame = new Game();
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
        clientsNickname = new HashMap<>();
    }*/

    void registerClient(VirtualClient virtualClient, String nickname) throws NicknameAlreadyInUseException {
        if(clientsNickname.containsValue(nickname)) throw new NicknameAlreadyInUseException();
        virtualClientIDMap.put(virtualClient.getID(), virtualClient);
        clientsNickname.put(virtualClient, nickname);
        virtualClient.send(new ResponseClientAccepted());
        synchronized (lobbyLocked) {
            lobby.addPlayer(virtualClient);
        }
    }


    public static void main(String[] args) {
        Boolean debug = true;
        if (debug)
            System.out.println("Launching server with debug on!");
        Server server = new Server(debug);
        Integer port = 25556;
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    void setCurrentLobbySize(VirtualClient virtualClient, Integer currentLobbySize) {
        lobby.setLobbyCapacity(currentLobbySize);
    }

    Game getGameByPlayerID(Integer playerID){
        return gameMap.get(virtualClientIDMap.get(playerID));
    }

    public Integer getIDbyGame(Game game){
        return gamesID.get(game);
    }

    public void startGame() {
        ArrayList<VirtualClient> players = lobby.getPlayers();
        ArrayList<String> playersNickname = new ArrayList<>();
        for (VirtualClient player :
                players) {
            playersNickname.add(clientsNickname.get(player));
        }
        currentGame.addAllPlayers(players, playersNickname);
        executors.submit(currentGame);
        for (VirtualClient player :
                players) {
            gameMap.put(player, currentGame);
        }
        lobby.sendAll(new ResponseGameHasStarted(nextGameID - 1, playersNickname));
        currentGame = new Game(debug);
        gamesID.put(currentGame, nextGameID);
        nextGameID++;
        lobby = new WaitingLobby(this);
    }

    public void unregisterClientTimeoutExceeded(VirtualClient virtualClient) {
        System.out.println("Timeout exceeded, unregistering client " + virtualClient);
        unregisterClient(virtualClient);
    }

    private void unregisterClient(VirtualClient virtualClient) {
        ;
    }

    public void requestTimedout(VirtualClient virtualClient) {
        System.out.println("request Timedout by client: " + virtualClient);
    }
}