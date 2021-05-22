package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.model.BriefModel;
import it.polimi.ingsw.model.PlayerBoard;

public class NotifyBriefModel extends ServerMessage {

    private BriefModel briefModel;

    public NotifyBriefModel(PlayerBoard playerBoard) {
        super(null, null);
        briefModel = new BriefModel(playerBoard);
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.notifyBriefModel(briefModel);
    }
}
