package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;

public class ResponseMainMoveAlreadyMade extends ServerResponse {

    public ResponseMainMoveAlreadyMade() {
        super(null, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.mainMoveAlreadyMade();
    }
}
