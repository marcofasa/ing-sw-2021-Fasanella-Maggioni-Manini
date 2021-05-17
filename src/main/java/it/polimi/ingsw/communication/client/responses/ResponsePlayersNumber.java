package it.polimi.ingsw.communication.client.responses;

import it.polimi.ingsw.communication.client.ClientResponse;
import it.polimi.ingsw.server.VirtualClient;

public class ResponsePlayersNumber extends ClientResponse {

    public ResponsePlayersNumber(Integer playersNumber) {
        super(playersNumber.toString(), null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getCommandDispatcher().setLobbySize(Integer.parseInt(super.getPayload()));
    }
}
