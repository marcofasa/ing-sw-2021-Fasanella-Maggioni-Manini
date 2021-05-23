package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;

import java.util.ArrayList;

public class ResponseGameHasStarted extends ServerResponse {

    private final ArrayList<String> playersNickname;

    public ResponseGameHasStarted(int nextGameID, ArrayList<String> playersNickname) {
        super(Integer.toString(nextGameID), null);
        this.playersNickname = playersNickname;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.gameHasStarted(Integer.parseInt(super.getPayload()), playersNickname);
    }
}
