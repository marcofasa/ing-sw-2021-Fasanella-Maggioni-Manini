package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.model.CardLeader;

import java.util.ArrayList;

public class LeaderCardSelection extends ServerRequest {
    private ArrayList<CardLeader> cardLeaders;

    public LeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        super(null, null);
        this.cardLeaders = cardLeaders;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.leaderCardSelection(cardLeaders);
    }
}
