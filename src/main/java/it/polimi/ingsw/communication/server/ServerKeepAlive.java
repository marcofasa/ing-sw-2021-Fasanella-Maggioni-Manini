package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class ServerKeepAlive extends ServerMessage {
    public ServerKeepAlive() {
        super(null, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        ;
    }
}
