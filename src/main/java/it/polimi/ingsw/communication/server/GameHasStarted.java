package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class GameHasStarted extends ServerResponse {
    public GameHasStarted(int nextGameID) {
        super(Integer.toString(nextGameID), null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.gameHasStarted(Integer.parseInt(super.getPayload()));
    }
}
