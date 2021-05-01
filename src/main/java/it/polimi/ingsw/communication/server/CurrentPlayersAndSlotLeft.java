package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class CurrentPlayersAndSlotLeft extends ServerResponse{
    public CurrentPlayersAndSlotLeft(String message, String keyValues) {
        super(message, keyValues);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {

    }
}
