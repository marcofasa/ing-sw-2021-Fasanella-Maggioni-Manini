package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.communication.server.requests.GamePhase;

public class ResponseSuccess extends ServerResponse {

    public ResponseSuccess() {
        super(GamePhase.Unmodified.toString(), null);
    }

    public ResponseSuccess(GamePhase gamePhase) {
        super(gamePhase.toString(), null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.success(GamePhase.valueOf(super.getPayload()));
    }
}
