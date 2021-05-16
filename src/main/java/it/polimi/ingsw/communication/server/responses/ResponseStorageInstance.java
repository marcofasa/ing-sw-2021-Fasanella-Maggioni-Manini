package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.Resource;

import java.util.HashMap;

public class ResponseStorageInstance extends ServerResponse {

    boolean isDeposit;
    HashMap<Resource, Integer> storage;

    public ResponseStorageInstance(boolean _isDeposit, HashMap<Resource, Integer> _storage) {
        super(null, null);
        isDeposit = _isDeposit;
        storage = _storage;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException {

        if (isDeposit) {
            commandDispatcher.setDeposit(storage);
        } else {
            commandDispatcher.setStrongbox(storage);
        }
    }
}
