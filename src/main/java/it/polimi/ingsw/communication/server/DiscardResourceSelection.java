package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.model.Marble;

import java.util.ArrayList;

public class DiscardResourceSelection extends ServerRequest {

    private ArrayList<Marble> marbles;

    public DiscardResourceSelection(ArrayList<Marble> marbles) {
        super(null, null);
        this.marbles = marbles;
    }

    /**
     * Calls the method specified in the read function
     *
     * @param commandDispatcher Game dispatcher
     */
    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.discardResourceSelection(marbles);
    }
}
