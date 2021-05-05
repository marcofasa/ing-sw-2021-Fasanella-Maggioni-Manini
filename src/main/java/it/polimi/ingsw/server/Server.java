package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.ClientAccepted;
import it.polimi.ingsw.communication.server.GameHasStarted;
import it.polimi.ingsw.controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Server {
    private SocketServer socketServer;
    private final HashMap<Integer, VirtualClient> virtualClientIDMap;
    private final HashMap<VirtualClient, Game> gameMap;
    private final HashMap<String, VirtualClient> clientsNickname;
    private final HashMap<Game, Integer> gamesID;
    private int nextGameID;
    private WaitingLobby lobby;
    private Game currentGame;
    private final ServerCommandDispatcher serverCommandDispatcher;
    private final ExecutorService executors;
    private final ArrayList<Runnable> timeouts;

    public Server(){
        nextGameID = 1;
        gamesID = new HashMap<>();
        currentGame = new Game();
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
        clientsNickname = new HashMap<>();
        gameMap = new HashMap<>();
        virtualClientIDMap = new HashMap<>();
        executors = Executors.newCachedThreadPool();
        gamesID.put(currentGame, nextGameID);
        nextGameID++;
        timeouts = new ArrayList<>();
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
        if(clientsNickname.containsKey(nickname)) throw new NicknameAlreadyInUseException();
        virtualClientIDMap.put(virtualClient.getID(), virtualClient);
        clientsNickname.put(nickname, virtualClient);
        virtualClient.send(new ClientAccepted());
        synchronized (lobby) {
            lobby.addPlayer(virtualClient);
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        Integer port = 25556;
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    void setCurrentLobbySize(VirtualClient virtualClient, Integer currentLobbySize) {
        // IF ... /* TODO */
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
        currentGame.addAllPlayers(players);
        executors.submit(currentGame);
        for (VirtualClient player :
                players) {
            gameMap.put(player, currentGame);
        }
        lobby.sendAll(new GameHasStarted(nextGameID - 1));
        currentGame = new Game();
        gamesID.put(currentGame, nextGameID);
        nextGameID++;
        lobby = new WaitingLobby(this);
    }

    public ServerCommandDispatcher getServerCommandDispatcher() {
        return serverCommandDispatcher;
    }

    public void unregisterClientTimeoutExceeded(VirtualClient virtualClient) {
        System.out.println("Timeout exceeded, unregistering client " + virtualClient);
        unregisterClient(virtualClient);
    }

    private void unregisterClient(VirtualClient virtualClient) {
        ;
    }
}