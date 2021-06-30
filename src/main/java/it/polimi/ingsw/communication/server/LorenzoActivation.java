package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.model.enums.ActionCardEnum;

public class LorenzoActivation extends ServerMessage {
    public LorenzoActivation(ActionCardEnum actionCardType) {
        super(actionCardType.toString(), null);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.displayLorenzoActivation(ActionCardEnum.valueOf(super.getPayload()));
    }
}
