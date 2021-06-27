package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class NotifyDisconnectionOf extends ServerMessage {
    public NotifyDisconnectionOf(String nickname) {
        super(nickname, null);
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.notifyDisconnectionOf(super.getPayload());
    }
}
