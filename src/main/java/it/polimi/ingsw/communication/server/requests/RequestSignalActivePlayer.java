package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;

public class RequestSignalActivePlayer extends ServerRequest {

    private final GamePhase phase;

    public RequestSignalActivePlayer(String nickname, GamePhase phase) {
        super(nickname, null);
        this.phase = phase;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.displayTurn(getPayload(), phase);
    }
}
