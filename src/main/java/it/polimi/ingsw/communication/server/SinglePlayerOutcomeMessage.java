package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class SinglePlayerOutcomeMessage extends ServerMessage {

    boolean hasWon;
    Integer playerPoints;

    public SinglePlayerOutcomeMessage(boolean b, Integer _playerPoints) {
        super(null, null);
        hasWon = b;
        playerPoints = _playerPoints;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.displaySinglePlayerOutcome(hasWon, playerPoints);
    }
}
