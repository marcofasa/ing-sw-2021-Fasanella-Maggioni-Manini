package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;

public class ResponseUnexpectedAnswer extends ServerResponse {

    public ResponseUnexpectedAnswer() {
        super(null, null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException {
        //niente per ora
    }
}
