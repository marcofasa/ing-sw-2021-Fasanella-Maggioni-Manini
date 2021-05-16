package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestDiscardResourceSelection extends ServerRequest {

    private HashMap<Resource,Integer> resources;

    public RequestDiscardResourceSelection(HashMap<Resource,Integer> resources) {
        super(null, null);
        this.resources = resources;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.requestDiscardResourceSelection(resources, getTimeoutID());
    }
}
