package it.polimi.ingsw.communication.server.responses;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.communication.server.ServerMessage;

import java.util.HashMap;
import java.util.function.Predicate;

public class ResponsePlayerPositions extends ServerMessage {

    HashMap<String, Integer> playerPositions;

    public ResponsePlayerPositions(HashMap<String, Integer> _playerPositions) {
        super(null, null);
        playerPositions = _playerPositions;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.setPlayerPositions(playerPositions);
    }
}
