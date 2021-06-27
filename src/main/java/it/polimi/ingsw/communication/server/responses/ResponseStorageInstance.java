package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ResponseStorageInstance extends ServerResponse {

    boolean isDeposit;
    HashMap<Resource, Integer> storage;
    ArrayList<Resource> leaderResources;
    HashMap<Resource, Integer> leaderContent;

    public ResponseStorageInstance(
            boolean _isDeposit,
            HashMap<Resource, Integer> _storage,
            ArrayList<Resource> _leaderResources,
            HashMap<Resource, Integer> _leaderContent)
    {
        super(null, null);
        isDeposit = _isDeposit;
        storage = _storage;
        leaderResources = _leaderResources;
        leaderContent = _leaderContent;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {

        if (isDeposit) {
            commandDispatcher.setDeposit(storage, leaderResources, leaderContent);
        } else {
            commandDispatcher.setStrongbox(storage);
        }
    }
}
