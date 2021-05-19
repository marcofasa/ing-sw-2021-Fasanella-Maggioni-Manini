package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;

public class StartingEndGameMessage extends ServerMessage {


    public StartingEndGameMessage(String nickname) {
        super(nickname,null);
    }


    @Override
    public void read(ClientCommandDispatcher commandDispatcher) {
        commandDispatcher.startingEndGame(super.getPayload());
    }

}
