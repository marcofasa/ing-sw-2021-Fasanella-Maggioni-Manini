package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class PlayersNumber extends ClientResponse{

    public PlayersNumber(String playersNumber) {
        super(playersNumber, null);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        virtualClient.getServer().getServerCommandDispatcher().setLobbySize();
    }
}
