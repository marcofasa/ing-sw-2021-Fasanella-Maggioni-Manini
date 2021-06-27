package it.polimi.ingsw.communication.client.requests;

import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.server.VirtualClient;

public class RequestDiscardCardLeader extends ClientRequest {

    final Integer cardLeaderIndex;

    public RequestDiscardCardLeader(Integer _cardLeaderIndex) {
        super(null, null);
        this.cardLeaderIndex = _cardLeaderIndex;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestDiscardCardLeader(cardLeaderIndex, super.getTimeoutID());
    }
}
