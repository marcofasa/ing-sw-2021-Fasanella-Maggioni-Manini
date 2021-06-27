package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class ClientKeepAlive extends ClientMessage {
    public ClientKeepAlive() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
    }
}
