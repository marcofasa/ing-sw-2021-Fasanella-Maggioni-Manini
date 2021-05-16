package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.server.VirtualClient;

public class RequestBuyDevelopmentCard extends ClientRequest {

    Integer rowIndex, columnIndex, placementIndex;

    /**
     * Buy card development from market
     * @param _rowIndex index row from 0 to 2
     * @param _columnIndex index column from 0 to 3
     * @param _placementIndex index of target slot from 0 to 2
     */
    public RequestBuyDevelopmentCard(Integer _rowIndex, Integer _columnIndex, Integer _placementIndex) {
        super(null, null);

        rowIndex = _rowIndex;
        columnIndex = _columnIndex;
        placementIndex = _placementIndex;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().
                requestBuyAndPlaceDevelopmentCard(rowIndex, columnIndex, placementIndex, super.getTimeoutID());
    }
}
