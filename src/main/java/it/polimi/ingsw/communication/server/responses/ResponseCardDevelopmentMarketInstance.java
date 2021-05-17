package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;
import it.polimi.ingsw.model.CardDevelopment;

import java.util.ArrayList;

public class ResponseCardDevelopmentMarketInstance extends ServerResponse {

    ArrayList<ArrayList<CardDevelopment>> cardMarketClone;

    public ResponseCardDevelopmentMarketInstance(ArrayList<ArrayList<CardDevelopment>> _cardMarketClone) {
        super(null, null);
        cardMarketClone = _cardMarketClone;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException {
        commandDispatcher.setCardDevelopmentMarketInstance(cardMarketClone);
    }
}
