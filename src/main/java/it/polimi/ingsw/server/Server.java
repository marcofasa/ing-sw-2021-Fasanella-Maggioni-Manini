package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.KillConnectionMessage;
import it.polimi.ingsw.communication.server.responses.ResponseClientAccepted;
import it.polimi.ingsw.communication.server.responses.ResponseGameHasStarted;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;

/**
 * Class to launch a Maestro Del Rinascimento server
 */
public class Server {
    final Boolean debug;
    private final HashMap<String, Game> disconnectedNicknamesGameMap;
    final Boolean timeoutEnabled;
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
    public Server(Boolean debug, Boolean timeoutEnabled){
        this.debug = debug;
        this.timeoutEnabled = timeoutEnabled;
        nextGameID = 1;
        gamesID = new HashMap<>();
        currentGame = new Game(debug, this);
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

    /* Single Player Server
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
        currentGame = new Game(debug, this);
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

    /**
     * This private method handles the unregistration of a single client from the Server maps
     * @param virtualClient to be unregistered
     */
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
        System.out.println("request timed out by client: " + virtualClient);
    }

    public static void main(String[] args) {
        boolean debug = false;
        boolean timeoutEnabled = true;
        int port = 51214;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "--h" -> {
                    System.out.println("--d to start in debug");
                    return;
                }
                case "--d" -> {
                    System.out.println("debug mode on");
                    debug = true;
                }
                case "--no-timeout" -> {
                    System.out.println("running with timeout disabled");
                    timeoutEnabled = false;
                }
                case "--port" -> {
                    if(args.length - 1 < i + 1){
                        System.out.println("you need to input a port when using the tag --port");
                        exit(1);
                    }
                    try {
                        port = Integer.parseInt( args[i+1] );
                        if(!(port > 1024 && port < 65535)){
                            System.out.println("Port must be a number between 1024 and 65535");
                            exit(1);
                        }
                    } catch (NumberFormatException e){
                        System.out.println("The port given is not valid");
                        exit(1);
                    }
                    System.out.println("selected port " + port);
                    debug = true;
                }
            }
        }
        if (debug)
            System.out.println("Server is running in debug!");
        Server server = new Server(debug, timeoutEnabled);
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    /**
     * handles the disconnection of a client from the server
     * @param virtualClient to be disconnected
     * @param game virtualClient was playing in
     * @param nickname of the client
     */
    public void disconnectClient(VirtualClient virtualClient, Game game, String nickname) {
        if (isPlayerWaiting(virtualClient)){
            System.out.println("Player " + nickname + " disconnected from lobby");
            unregisterClient(virtualClient);
            lobby.removePlayer(virtualClient);
        } else {
            System.out.println("Player " + nickname + " disconnected");
            disconnectedNicknamesGameMap.put(nickname, game);
            game.notifyDisconnectionOfClient(virtualClient);
        }
    }

    /**
     * handles the reconnection of a client to the server
     * @param nickname of the client
     * @param game the client was playing on
     * @param virtualClient client reconnected
     */
    private void resumePlayer(String nickname, Game game, VirtualClient virtualClient) {
        System.out.println("Player " + nickname + " reconnected");
        disconnectedNicknamesGameMap.remove(nickname);
        game.notifyReconnection(nickname, virtualClient);
    }

    /**
     * verifies whether a player is waiting in lobby or not
     * @param virtualClient the be verified
     * @return true if virtualClient is waiting, false if he is playing or otherwise
     */
    public boolean isPlayerWaiting(VirtualClient virtualClient){
        return lobby.getPlayers().contains(virtualClient);
    }

    /**
     * check if nickname is not disconnected
     * @param nickname to verify
     * @return true if he is not in the disconnected list, true otherwise
     */
    public boolean isNotDisconnected(String nickname) {
        return disconnectedNicknamesGameMap.get(nickname) == null;
    }

    /**
     * unregisters all players from the Server, to be used only in extreme cases or shutdown
     */
    public void disconnectAllPlayers() {
        for (VirtualClient client :
                clientsNickname.keySet()) {
            unregisterClient(client);
        }
    }
}