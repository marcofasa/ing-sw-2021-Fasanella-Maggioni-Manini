package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.*;

import java.util.HashMap;

public class Server {
    private SocketServer socketServer;
    private HashMap<Integer, VirtualClient> virtualClientIDMap;
    private HashMap<VirtualClient, Game> gameMap;
    private HashMap<String, VirtualClient> clientsNickname;
    private final WaitingLobby lobby;
    private Integer currentLobbySize;
    private final Game currentGame;
    private final ServerCommandDispatcher serverCommandDispatcher;

    public Server(){
        currentGame = new Game();
        currentLobbySize = null;
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
    }

    //Single Player Server
    public Server(boolean SinglePlayer){
        currentGame = new Game();
        currentLobbySize = null;
        lobby = new WaitingLobby(this);
        serverCommandDispatcher = new ServerCommandDispatcher(this);
    }

    synchronized void registerClient(VirtualClient virtualClient, String nickname) throws NicknameAlreadyInUseException {
        if(clientsNickname.containsKey(nickname)) throw new NicknameAlreadyInUseException();
        virtualClientIDMap.put(virtualClient.getID(), virtualClient);
        lobby.addPlayer(virtualClient);
    }

    public static void main(String[] args) {
        Server server = new Server();
        Integer port = 25556;
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    void setCurrentLobbySize(Integer currentLobbySize) {
        this.currentLobbySize = currentLobbySize;
    }

    Game getGameByPlayerID(Integer playerID){
        return gameMap.get(virtualClientIDMap.get(playerID));
    }

    public void startGame() {

    }

    public ServerCommandDispatcher getServerCommandDispatcher() {
        return serverCommandDispatcher;
    }
}