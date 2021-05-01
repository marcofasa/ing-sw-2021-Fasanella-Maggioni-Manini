package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public abstract class ServerRequest extends ServerMessage {
    public ServerRequest(String message, String key) {
        super(message, key);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public abstract void read(ClientCommandDispatcher commandDispatcher);
}

