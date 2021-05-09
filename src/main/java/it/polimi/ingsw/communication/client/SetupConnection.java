package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.server.VirtualClient;

public class SetupConnection extends ClientMessage{

    public SetupConnection(String nickname) {
        super(nickname, "nickname");
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getServer().getServerCommandDispatcher().setupConnection(super.getPayload(), virtualClient);
    }
}
