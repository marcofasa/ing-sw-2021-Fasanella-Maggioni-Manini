package it.polimi.ingsw.communication.server.requests;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerRequest;
import it.polimi.ingsw.model.CardLeader;

import java.util.ArrayList;

public class RequestInitialSelection extends ServerRequest {
    private final ArrayList<CardLeader> cardLeaders;
    private final int playerNumber;

    public RequestInitialSelection(ArrayList<CardLeader> cardLeaders, int playerNumber) {
        super(null, null);
        this.cardLeaders = cardLeaders;
        this.playerNumber = playerNumber;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher){
        commandDispatcher.requestInitialSelection(cardLeaders, playerNumber, getTimeoutID());
    }
}
