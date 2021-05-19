package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.server.VirtualClient;


public class RequestCardDevelopmentMarketInstance extends ClientRequest {

    public RequestCardDevelopmentMarketInstance() {
        super(null, null);
    }


    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().getCardDevelopmentMarketInstance(getTimeoutID());
    }
}
