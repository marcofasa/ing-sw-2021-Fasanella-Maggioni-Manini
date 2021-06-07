package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class KillConnectionMessage extends ServerMessage {

    public KillConnectionMessage() {
        super(null, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.killConnection();
    }
}
