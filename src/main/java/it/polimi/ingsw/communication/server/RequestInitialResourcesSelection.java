package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class RequestInitialResourcesSelection extends ServerRequest {
    /**
     * Ask for selection of resources to start with
     * @param playerNumber player number
     */
    public RequestInitialResourcesSelection(Integer playerNumber) {
        super(Integer.toString(playerNumber), null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.requestInitialResourcesSelection(Integer.parseInt(super.getPayload()));
    }
}
