package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;

public class RequestSignalActivePlayer extends ServerRequest {

    public RequestSignalActivePlayer(String nickname) {
        super(nickname, null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.displayTurn(getPayload());
    }
}
