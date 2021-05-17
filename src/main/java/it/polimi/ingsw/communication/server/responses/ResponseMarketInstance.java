package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.MarbleType;

import java.util.ArrayList;

public class ResponseMarketInstance extends ServerResponse {

    ArrayList<ArrayList<MarbleType>> marketClone;

    public ResponseMarketInstance(ArrayList<ArrayList<MarbleType>> _marketClone) {
        super(null, null);
        marketClone = _marketClone;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException {
        commandDispatcher.setMarketInstance(marketClone);
    }
}
