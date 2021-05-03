package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.ClientAccepted;
import it.polimi.ingsw.controller.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private SocketServer socketServer;
    private final HashMap<Integer, VirtualClient> virtualClientIDMap;
    private final HashMap<VirtualClient, Game> gameMap;
    private final HashMap<String, VirtualClient> clientsNickname;
    private final WaitingLobby lobby;
    private Game currentGame;
    private final ServerCommandDispatcher serverCommandDispatcher;
    private final ExecutorService executors;

    public Server(){
        currentGame = new Game();
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
        clientsNickname = new HashMap<>();
        gameMap = new HashMap<>();
        virtualClientIDMap = new HashMap<>();
        executors = Executors.newCachedThreadPool();
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

    public void startGame() {
        ArrayList<VirtualClient> players = lobby.getPlayers();
        currentGame.addAllPlayers(players);
        executors.submit(currentGame);
        for (VirtualClient player :
                players) {
            gameMap.put(player, currentGame);
        }
        currentGame = new Game();
    }

    public ServerCommandDispatcher getServerCommandDispatcher() {
        return serverCommandDispatcher;
    }
}