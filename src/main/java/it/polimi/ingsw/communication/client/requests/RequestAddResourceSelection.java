package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualClient;

import java.util.HashMap;

public class RequestAddResourceSelection extends ClientRequest {

    HashMap<Resource, Integer> resources;

    public RequestAddResourceSelection(HashMap<Resource, Integer> resources) {
        super(null, null);
        this.resources = resources;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().discardResourceSelection(resources, super.getTimeoutID());
    }
}
