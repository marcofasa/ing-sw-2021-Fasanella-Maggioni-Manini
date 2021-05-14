package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.server.VirtualClient;

public class RequestDiscardCardLeader extends ClientRequest{

    CardLeader cardLeader;

    public RequestDiscardCardLeader(CardLeader cardLeaderToDiscard) {
        super(null, null);
        this.cardLeader = cardLeaderToDiscard;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestDiscardCardLeader(cardLeader);
    }
}
