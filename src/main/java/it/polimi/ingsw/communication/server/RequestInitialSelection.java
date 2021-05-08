package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.model.CardLeader;

import java.util.ArrayList;

public class RequestInitialSelection extends ServerRequest {
    private final ArrayList<CardLeader> cardLeaders;

    public RequestInitialSelection(ArrayList<CardLeader> cardLeaders) {
        super(null, null);
        this.cardLeaders = cardLeaders;
    }

    public RequestInitialSelection(ArrayList<CardLeader> cardLeaders, int timeoutID){
        super(null,null, timeoutID);
        this.cardLeaders = cardLeaders;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException {
        commandDispatcher.requestLeaderCardSelection(cardLeaders);
    }
}
