package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.server.VirtualClient;

public class RequestMarketUse extends ClientRequest {
    /**
     * Create a RequestMarketUse with specified column or row
     * @param number from 1 to 3 if key is row, from 1 to 4 if key is column
     * @param columnOrRow either "row" or "column"
     */
    public RequestMarketUse(Integer number, String columnOrRow){
        super(number.toString(), columnOrRow);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        //Controller.useMarket(virtualClient, getPayload(), getKey());
        virtualClient.getCommandDispatcher().useMarket(Integer.parseInt(getPayload()), getKey(), super.getTimeoutID());
    }
}
