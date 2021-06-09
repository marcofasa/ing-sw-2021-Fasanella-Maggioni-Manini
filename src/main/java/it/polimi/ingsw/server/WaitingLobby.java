package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.requests.RequestRequestPlayersNumber;
import it.polimi.ingsw.communication.server.ServerMessage;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Class to handle of the pre game phase.
 */
public class WaitingLobby {

    private final Server server;

    private volatile boolean empty;

    private volatile int lobbyCapacity;

    private ArrayList<VirtualClient> players;

    /**
     * Constructor of the class
     * @param server creator of the lobby
     */
    public WaitingLobby(Server server){
        this.server = server;
        setupLobby();
    }

    private void setupLobby() {
        lobbyCapacity = -1;
        empty = true;
        players = new ArrayList<>();
    }

    /**
     * private method to add the first player of the lobby. It's called by addPlayer
     * @param virtualClient client to add
     */
    private void addFirstPlayer(VirtualClient virtualClient) {
        try {
            virtualClient.sendAndWait(new RequestRequestPlayersNumber(), 60);
            players.add(virtualClient);
            empty = false;
        } catch (RequestTimeoutException e) {
            server.unregisterClientTimeoutExceeded(virtualClient);
        }
    }

    /**
     * Add player to this waiting lobby.
     * Starts the game automatically when maximum capacity is reached
     * @param virtualClient client to add
     */
    public void addPlayer(VirtualClient virtualClient) {
        synchronized (this) {
            if (empty)
                addFirstPlayer(virtualClient);
            else {
                players.add(virtualClient);
            }
            if( players.size() == lobbyCapacity ){
                server.startGame();
            }
        }
    }

    /**
     * set number of player before the lobby is full
     * @param size number of players
     */
    public void setLobbyCapacity(int size){
        lobbyCapacity = size;
    }

    public ArrayList<VirtualClient> getPlayers() {
        return players;
    }

    /**
     * send message to all clients in this lobby
     * @param serverMessage to be sent
     */
    public void sendAll(ServerMessage serverMessage){
        for (VirtualClient player :
                players) {
            player.send(serverMessage);
        }
    }

    /**
     * removes this player from the lobby, resets the lobby in case it's empty
     * @param virtualClient player to remove
     */
    public synchronized void removePlayer(VirtualClient virtualClient) {
        players.remove(virtualClient);
        if (players.size() == 0) {
            empty = true;
            lobbyCapacity = -1;
        }
    }
}
