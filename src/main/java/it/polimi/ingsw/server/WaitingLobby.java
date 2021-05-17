package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.requests.RequestRequestPlayersNumber;
import it.polimi.ingsw.communication.server.ServerMessage;

import java.util.ArrayList;
import java.util.concurrent.*;

public class WaitingLobby {

    private final Server server;
    private final ExecutorService executors;

    private volatile boolean empty;

    private volatile int lobbyCapacity;

    private final Semaphore semaphore;

    private ArrayList<VirtualClient> players;

    public WaitingLobby(Server server){
        this.server = server;
        lobbyCapacity = -1;
        empty = true;
        semaphore = new Semaphore(0);
        players = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
    }

    private void addFirstPlayer(VirtualClient virtualClient) {
        try {
            virtualClient.sendAndWait(new RequestRequestPlayersNumber(), -1);
            players.add(virtualClient);
            empty = false;
        } catch (RequestTimeoutException e) {
            server.unregisterClientTimeoutExceeded(virtualClient);
        }
    }

    public void addPlayer(VirtualClient virtualClient) {
        synchronized (this) {
            if (empty)
                addFirstPlayer(virtualClient);
            else {
                players.add(virtualClient);
                if( players.size() == lobbyCapacity ){
                    server.startGame();
                }
            }
        }
    }

    public ArrayList<VirtualClient> startGame(){
        lobbyCapacity = -1;
        empty = true;
        ArrayList<VirtualClient> playersTemp = new ArrayList<>(players);
        players = new ArrayList<>();
        return playersTemp;
    }

    public void setLobbyCapacity(int size){
        lobbyCapacity = size;
    }

    public ArrayList<VirtualClient> getPlayers() {
        return players;
    }

    public void sendAll(ServerMessage serverMessage){
        for (VirtualClient player :
                players) {
            player.send(serverMessage);
        }
    }
}
