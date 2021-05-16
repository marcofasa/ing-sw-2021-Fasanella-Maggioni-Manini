package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.model.ProductionSelection;
import it.polimi.ingsw.server.VirtualClient;

public class RequestActivateProduction extends ClientRequest {
    private final ProductionSelection productionSelection;

    public RequestActivateProduction(ProductionSelection productionSelection) {
        super(null, null);
        this.productionSelection = productionSelection;
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().requestActivateProduction(productionSelection, super.getTimeoutID());
    }
}
