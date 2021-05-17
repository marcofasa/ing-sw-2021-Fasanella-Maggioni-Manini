package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.model.FaithTileStatus;

import java.util.ArrayList;

public class ResponseFaithTileStatuses extends ServerMessage {

    ArrayList<FaithTileStatus> tileStatuses;

    public ResponseFaithTileStatuses(ArrayList<FaithTileStatus> _tileStatuses) {
        super(null, null);
        tileStatuses = _tileStatuses;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setTileStatuses(tileStatuses);
    }
}
