package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.*;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private SocketServer socketServer;
    private HashMap<VirtualClient, Integer> virtualClientIDMap;
    private HashMap<VirtualClient, Game> gameMap;
    private final WaitingLobby lobby;
    private Integer currentLobbySize;
    private Game currentGame;

    public Server(){
        currentGame = new Game();
        currentLobbySize = null;
        lobby = new WaitingLobby(this);
    }

    //Single Player Server
    public Server(boolean SinglePlayer){
        currentGame = new Game();
        currentLobbySize = null;
        lobby = new WaitingLobby(this);
    }

    synchronized void registerClient(VirtualClient virtualClient){
        virtualClientIDMap.put(virtualClient, virtualClient.getID());
        lobby.addPlayer(virtualClient);
    }

    public static void main(String[] args) {
        Server server = new Server();
        Integer port = 25580;
        server.socketServer = new SocketServer(port, server);
        Thread thread = new Thread(server.socketServer);
        thread.start();
    }

    void setCurrentLobbySize(Integer currentLobbySize) {
        this.currentLobbySize = currentLobbySize;
    }

    public void startGame() {

    }
}