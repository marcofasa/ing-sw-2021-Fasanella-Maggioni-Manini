package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;

public class ResponseGameHasStarted extends ServerResponse {
    public ResponseGameHasStarted(int nextGameID) {
        super(Integer.toString(nextGameID), null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.gameHasStarted(Integer.parseInt(super.getPayload()));
    }
}
