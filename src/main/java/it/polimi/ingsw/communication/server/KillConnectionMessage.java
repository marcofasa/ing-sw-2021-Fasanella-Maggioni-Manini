package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class KillConnectionMessage extends ServerMessage {

    private final Boolean error;

    public KillConnectionMessage() {
        super(null, null);
        error = false;
    }

    public KillConnectionMessage(boolean error) {
        super(null, null);
        this.error = error;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.killConnection(error);
    }
}
