package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;

public class RequestRequestPlayersNumber extends ServerRequest {

    public RequestRequestPlayersNumber() {
        super(null, null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.requestPlayersNumber(getTimeoutID());
    }
}
