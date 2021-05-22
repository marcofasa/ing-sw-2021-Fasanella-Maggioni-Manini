package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.CardDevelopment;

import java.util.ArrayList;

public class ResponseTopCardsDevelopment extends ServerResponse {

    final ArrayList<CardDevelopment> developmentCards;

    public ResponseTopCardsDevelopment(ArrayList<CardDevelopment> _developmentCards) {
        super(null, null);
        developmentCards = _developmentCards;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setTopCardsDevelopment(developmentCards);
    }
}
