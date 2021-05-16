package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;

public class ResponseLeaderRequirementsNotMet extends ServerResponse {
    public ResponseLeaderRequirementsNotMet() {
        super(null, null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.leaderRequirementsNotMet();
    }
}
