package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.client.*;

public class ClientCommandDispatcher {

    private Client client;

    public ClientCommandDispatcher(Client client){
        this.client = client;
    }

    public void clientAccepted() {
        System.out.println("Connected to server");
    }

    public void requestPlayersNumber() {
        System.out.println("Request Players Number received");
        client.send(new PlayersNumber(Integer.toString(client.askPlayersNumber())));
    }

    public void nicknameIsUnavailable(){ /* TODO */
        System.out.println("Nickname is unavailable");
    }

    public void gameHasStarted(int gameID) {
        System.out.println("Game Has Started. Game ID: " + gameID);
    }
}
