package it.polimi.ingsw.communication.client.responses;

import it.polimi.ingsw.communication.client.ClientResponse;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;

public class ResponseInitialSelection extends ClientResponse {
    private final ArrayList<CardLeader> cardLeader;
    private final Resource resource1;
    private final Resource resource2;

    public ResponseInitialSelection(Resource resource1, Resource resource2, ArrayList<CardLeader> cardLeaders) {
        super(null, null);
        this.cardLeader = cardLeaders;
        this.resource1 = resource1;
        this.resource2 = resource2;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().initialSelection(cardLeader, resource1, resource2);
    }
}
