package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class ClientResponse extends ClientMessage{
    public ClientResponse(String message, String key) {
        super(message, key);
    }

    @Override
    public void read(VirtualClient virtualClient) {

    }
}
