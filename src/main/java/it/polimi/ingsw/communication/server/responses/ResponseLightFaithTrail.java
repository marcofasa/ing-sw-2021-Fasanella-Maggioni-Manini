package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.enums.FaithTileStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseLightFaithTrail extends ServerResponse {

    final ArrayList<FaithTileStatus> tileStatuses;
    final HashMap<String, Integer> playerPositions;

    public ResponseLightFaithTrail(ArrayList<FaithTileStatus> _tileStatuses, HashMap<String, Integer> _playerPositions) {
        super(null, null);
        tileStatuses = _tileStatuses;
        playerPositions = _playerPositions;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setFaithTrail(playerPositions, tileStatuses);
    }
}
