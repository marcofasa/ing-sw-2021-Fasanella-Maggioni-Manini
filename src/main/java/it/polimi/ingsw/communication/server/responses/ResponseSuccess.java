package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.communication.server.requests.GamePhase;

public class ResponseSuccess extends ServerResponse {

    private final GamePhase gamePhase;

    public ResponseSuccess(GamePhase gamePhase) {
        super(null, null);
        this.gamePhase = gamePhase;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.success(gamePhase);
    }
}
