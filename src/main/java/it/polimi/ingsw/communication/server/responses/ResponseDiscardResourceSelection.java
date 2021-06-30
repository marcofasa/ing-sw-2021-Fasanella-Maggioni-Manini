package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.enums.Resource;

import java.util.HashMap;

public class ResponseDiscardResourceSelection extends ServerResponse {

    private final HashMap<Resource,Integer> resources;

    public ResponseDiscardResourceSelection(HashMap<Resource,Integer> resources) {
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
        commandDispatcher.discardResourceSelection(resources);
    }
}
