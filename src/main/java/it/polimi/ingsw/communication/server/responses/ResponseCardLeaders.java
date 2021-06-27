package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.CardLeader;

import java.util.ArrayList;

public class ResponseCardLeaders extends ServerResponse {

    final ArrayList<CardLeader> leaderCards;

    public ResponseCardLeaders(ArrayList<CardLeader> _leaderCards) {
        super(null, null);
        leaderCards = _leaderCards;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setLeaderCards(leaderCards);
    }
}
