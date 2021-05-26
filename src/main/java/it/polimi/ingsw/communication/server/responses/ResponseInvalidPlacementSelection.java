package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerMessage;

public class ResponseInvalidPlacementSelection extends ServerMessage {

    public ResponseInvalidPlacementSelection() {
        super(null, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.displayInvalidPlacementSelection();
    }
}
