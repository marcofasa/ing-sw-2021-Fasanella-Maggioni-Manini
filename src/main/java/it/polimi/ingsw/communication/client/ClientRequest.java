package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.client.TimeoutHandler;
import it.polimi.ingsw.server.VirtualClient;

public abstract class ClientRequest extends ClientMessage {

    private final int timeoutID;

    public ClientRequest(String message, String key) {
        super(message, key);
        timeoutID = -1;
    }

    public ClientRequest(String message, String key, int timeoutID) {
        super(message, key);
        this.timeoutID = timeoutID;
    }

    public int getTimeoutID(){
        return timeoutID;
    }

    @Override
    public abstract void read(VirtualClient virtualClient);
}
