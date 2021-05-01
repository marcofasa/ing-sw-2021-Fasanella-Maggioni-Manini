package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.server.VirtualClient;

public class PlayersNumber extends ClientResponse{

    public PlayersNumber(String message, String key) {
        super(message, key);
    }

    @Override
    public void read(VirtualClient virtualClient) {
        super.read(virtualClient);
    }
}
