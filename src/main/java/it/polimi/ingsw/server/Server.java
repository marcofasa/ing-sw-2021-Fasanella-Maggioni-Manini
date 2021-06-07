package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.KillConnectionMessage;
import it.polimi.ingsw.communication.server.responses.ResponseClientAccepted;
import it.polimi.ingsw.communication.server.responses.ResponseGameHasStarted;
import it.polimi.ingsw.controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to launch a Maestro Del Rinascimento server
 */
public class Server {
    private final Boolean debug;
    private final HashMap<String, Game> disconnectedNicknamesGameMap;
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

    /**
     * Constructor of the class
     * @param debug if true, the server wont check for duplicated main moves
     */
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
        disconnectedNicknamesGameMap = new HashMap<String, Game>();
    }

    /*
    //Single Player Server
    public Server(boolean SinglePlayer){
        currentGame = new Game();
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
        clientsNickname = new HashMap<>();
    }*/

    /**
     * Registers a new client in this server
     * @param virtualClient to be registered
     * @param nickname of the client
     * @throws NicknameAlreadyInUseException if another client has already taken this name
     */
    void registerClient(VirtualClient virtualClient, String nickname) throws NicknameAlreadyInUseException {
        if(disconnectedNicknamesGameMap.containsKey(nickname)){
            resumePlayer(nickname, disconnectedNicknamesGameMap.get(nickname), virtualClient);
            return;
        }
        if(clientsNickname.containsValue(nickname)) throw new NicknameAlreadyInUseException();
        virtualClientIDMap.put(virtualClient.getID(), virtualClient);
        clientsNickname.put(virtualClient, nickname);
        virtualClient.send(new ResponseClientAccepted());
        synchronized (lobbyLocked) {
            lobby.addPlayer(virtualClient);
        }
    }


    /**
     * Set the size of the lobby
     * @param currentLobbySize size
     */
    void setCurrentLobbySize(Integer currentLobbySize) {
        lobby.setLobbyCapacity(currentLobbySize);
    }

    Game getGameByPlayerID(Integer playerID){
        return gameMap.get(virtualClientIDMap.get(playerID));
    }

    public Integer getIDbyGame(Game game){
        return gamesID.get(game);
    }

    /**
     * Starts the current lobby in a new game and prepares a new empty lobby for players to join
     */
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

    /**
     * Unregisters a client from this server
     * @param virtualClient to be unregistered
     */
    public void unregisterClientTimeoutExceeded(VirtualClient virtualClient) {
        System.out.println("Timeout exceeded, unregistering client " + virtualClient);
        unregisterClient(virtualClient);
    }

    private void unregisterClient(VirtualClient virtualClient) {
        virtualClient.send(new KillConnectionMessage());
        virtualClient.close();
        virtualClientIDMap.remove(virtualClient.getID());
        clientsNickname.remove(virtualClient);
    }

    /**
     * Handles when a client didn't respond for too much time
     * @param virtualClient VirtualClient istance
     */
    public void requestTimedOut(VirtualClient virtualClient) {
        System.out.println("request Timedout by client: " + virtualClient);
    }

    public static void main(String[] args) {
        boolean debug = false;
        for (String arg :
                args) {
            switch (arg) {
                case "--h" -> {
                    System.out.println("--d to start in debug");
                    return;
                }
                case "--d" -> {
                    System.out.println("debug mode on");
                    debug = true;
                }
            }
        }
        if (debug)
            System.out.println("Client is running in debug!");
        Server server = new Server(true);
        Integer port = 51214;
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    public void notifyDisconnectionOfClient(VirtualClient virtualClient, Game game, String nickname) {
        System.out.println("Player " + nickname + " disconnected");
        disconnectedNicknamesGameMap.put(nickname, game);
        game.notifyDisconnectionOfClient(virtualClient);
    }

    private void resumePlayer(String nickname, Game game, VirtualClient virtualClient) {
        System.out.println("Player " + nickname + " reconnected");
        disconnectedNicknamesGameMap.remove(nickname);
        game.notifyReconnection(nickname, game, virtualClient);
    }
}