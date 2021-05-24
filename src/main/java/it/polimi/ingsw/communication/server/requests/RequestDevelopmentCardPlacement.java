package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;
import it.polimi.ingsw.model.CardDevelopmentLevel;

public class RequestDevelopmentCardPlacement extends ServerRequest {
    public RequestDevelopmentCardPlacement(CardDevelopmentLevel level) {
        super(level.toString(),null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.requestDevelopmentCardPlacement(CardDevelopmentLevel.valueOf(super.getPayload()), super.getTimeoutID());
    }
}
