package it.polimi.ingsw.server;

import it.polimi.ingsw.communication.server.RequestPlayersNumber;
import it.polimi.ingsw.communication.server.ServerMessage;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class WaitingLobby {

    private final Server server;

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
    }

    private void addFirstPlayer(VirtualClient virtualClient) {
        try {
            virtualClient.send(new RequestPlayersNumber());
            semaphore.acquire(1);
            players.add(virtualClient);
            empty = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        semaphore.release();
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
