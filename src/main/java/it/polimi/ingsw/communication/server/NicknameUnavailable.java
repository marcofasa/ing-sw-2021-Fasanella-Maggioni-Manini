package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class NicknameUnavailable extends ServerResponse{
    public NicknameUnavailable() {
        super(null, null);
    }
    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.nicknameIsUnavailable();
    }
}
