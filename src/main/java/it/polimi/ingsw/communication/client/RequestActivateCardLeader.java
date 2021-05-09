package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.server.VirtualClient;

public class RequestActivateCardLeader extends ClientRequest {

    private final CardLeader cardLeader;

    public RequestActivateCardLeader(CardLeader cardLeader){
        super(null, null);
        this.cardLeader = cardLeader;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestActivateCardLeader(cardLeader, super.getTimeoutID());
    }
}
