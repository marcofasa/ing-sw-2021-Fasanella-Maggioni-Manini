package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarbleType;

import java.util.ArrayList;

public class ResponseMarketInstance extends ServerResponse {

    private final Marble spareMarble;
    ArrayList<ArrayList<MarbleType>> marketClone;

    public ResponseMarketInstance(ArrayList<ArrayList<MarbleType>> _marketClone, Marble spareMarble) {
        super(null, null);
        marketClone = _marketClone;
        this.spareMarble = spareMarble;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setMarketInstance(marketClone, spareMarble);
    }
}
