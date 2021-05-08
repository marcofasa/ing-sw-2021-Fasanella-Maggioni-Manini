package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class RequestBuyDevelopmentCard extends ClientRequest {

    /**
     * Buy card development from market
     * @param rowIndex index row from 0 to 2
     * @param columnIndex index column from 0 to 3
     */
    public RequestBuyDevelopmentCard(Integer rowIndex, Integer columnIndex) {
        super(Integer.toString(rowIndex), Integer.toString(columnIndex));
    }

    public RequestBuyDevelopmentCard(Integer rowIndex, Integer columnIndex, Integer timeoutID) {
        super(Integer.toString(rowIndex), Integer.toString(columnIndex), timeoutID);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getServer().getServerCommandDispatcher().
                requestBuyDevelopmentCard(virtualClient, Integer.parseInt(super.getPayload()),
                Integer.parseInt(super.getKey()));
    }
}
