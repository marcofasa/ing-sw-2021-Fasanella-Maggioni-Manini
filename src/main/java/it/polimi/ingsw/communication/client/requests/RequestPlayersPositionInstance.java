package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.server.VirtualClient;

public class RequestPlayersPositionInstance extends ClientRequest {

    public RequestPlayersPositionInstance() {
        super(null, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestPlayersPosition(getTimeoutID());
    }
}
