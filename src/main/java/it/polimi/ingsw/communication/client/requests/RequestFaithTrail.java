package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.server.VirtualClient;

public class RequestFaithTrail extends ClientRequest {

    public RequestFaithTrail() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestFaithTrail(virtualClient, getTimeoutID());
    }
}
