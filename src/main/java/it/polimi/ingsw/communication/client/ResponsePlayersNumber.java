package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class ResponsePlayersNumber extends ClientResponse{

    public ResponsePlayersNumber(String playersNumber) {
        super(playersNumber, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().setLobbySize(Integer.parseInt(super.getPayload()));
    }
}
