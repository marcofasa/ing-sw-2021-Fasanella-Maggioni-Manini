package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseDiscardResourceSelection extends ClientResponse {

    HashMap<Resource, Integer> discardSelection;

    public ResponseDiscardResourceSelection(HashMap<Resource, Integer> discardSelection) {
        super(null, null);
        this.discardSelection = discardSelection;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().discardResourceSelection(discardSelection, getTimeoutID());
    }
}
