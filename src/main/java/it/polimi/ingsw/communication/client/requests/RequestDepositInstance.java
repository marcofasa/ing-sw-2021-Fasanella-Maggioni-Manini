package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.server.VirtualClient;

public class RequestDepositInstance extends ClientRequest {

    public RequestDepositInstance() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestDepositInstance(getTimeoutID());
    }
}
