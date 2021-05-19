package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.server.VirtualClient;

public class RequestMarketInstance extends ClientRequest {

    public RequestMarketInstance() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {

        virtualClient.getCommandDispatcher().requestMarketInstance(getTimeoutID());

    }
}
