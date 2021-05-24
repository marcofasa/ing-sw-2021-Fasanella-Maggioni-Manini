package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.CardDevelopmentSlotID;
import it.polimi.ingsw.server.VirtualClient;

public class ResponseDevelopmentCardPlacement extends ClientRequest {
    public ResponseDevelopmentCardPlacement(Integer slotID) {
        super(slotID.toString(), null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().setNewDevelopmentPlacement(Integer.parseInt(super.getPayload()));
    }
}
