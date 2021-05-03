package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class SendArrayListResourcesTest extends ServerResponse{

    private ArrayList<Resource> resources;

    public SendArrayListResourcesTest(ArrayList<Resource> resources){
        super(null, null);
        this.resources = resources;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {

    }
}
