package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public abstract class ClientRequest extends ClientMessage {

    public ClientRequest(String message, String key) {
        super(message, key);
    }

    @Override
    public abstract void read(VirtualClient virtualClient);
}
