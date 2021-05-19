package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

import java.util.HashMap;

public class ScoreBoardMessage extends ServerMessage {
    public ScoreBoardMessage(HashMap<String, Integer> stringIntegerHashMap) {
        super(null,null);
        showScoreBoard=stringIntegerHashMap;
    }
    private HashMap<String,Integer> showScoreBoard;

    public HashMap<String, Integer> getShowScoreBoard() {
        return showScoreBoard;
    }

    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.showScoreBoard(getShowScoreBoard());
    }
}
