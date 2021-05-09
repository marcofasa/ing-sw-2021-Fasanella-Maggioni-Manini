package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;

public class ResponseDiscardResourceSelection extends ClientResponse {

    ArrayList<Marble> discardSelection;

    public ResponseDiscardResourceSelection(ArrayList<Marble> discardSelection) {
        super(null, null);
        this.discardSelection = discardSelection;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().discardResourceSelection(discardSelection);
    }
}
