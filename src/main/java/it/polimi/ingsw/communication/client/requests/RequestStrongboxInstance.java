package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.server.VirtualClient;

public class RequestStrongboxInstance extends ClientRequest {

    public RequestStrongboxInstance() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestStrongboxInstance(getTimeoutID());
    }
}
