package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class NotifyReconnectionOf extends ServerMessage {
    public NotifyReconnectionOf(String nickname) {
        super(nickname, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.notifyReconnectionOf(super.getPayload());
    }
}
