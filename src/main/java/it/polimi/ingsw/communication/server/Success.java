package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class Success extends ServerResponse {
    public Success() {
        super(null, null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.success();
    }
}
