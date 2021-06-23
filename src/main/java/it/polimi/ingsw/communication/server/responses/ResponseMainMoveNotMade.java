package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;

public class ResponseMainMoveNotMade extends ServerResponse {

    public ResponseMainMoveNotMade() {
        super(null, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.mainMoveNotMade();
    }