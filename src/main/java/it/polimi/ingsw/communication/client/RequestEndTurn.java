package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class RequestEndTurn extends ClientRequest {

    public RequestEndTurn() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getServer().getServerCommandDispatcher().requestEndTurn(virtualClient);
    }
}
