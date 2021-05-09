package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.client.TimeoutHandler;

public abstract class ServerResponse extends ServerMessage{

    public ServerResponse(String message, String key) {
        super(message, key);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public abstract void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException;
}
