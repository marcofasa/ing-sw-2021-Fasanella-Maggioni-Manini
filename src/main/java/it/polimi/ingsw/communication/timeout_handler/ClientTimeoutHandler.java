package it.polimi.ingsw.communication.timeout_handler;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.client.ClientMessage;

import java.util.concurrent.TimeoutException;

public class ClientTimeoutHandler {
    final TimeoutHandler timeoutHandler;

    public ClientTimeoutHandler(Client client){
        timeoutHandler = new TimeoutHandler(client);
    }

    public void tryDisengage(int messageTimeoutID) throws RequestTimeoutException {
        timeoutHandler.tryDisengage(messageTimeoutID);
    }

    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws TimeoutException {
        timeoutHandler.sendAndWait(clientMessage, timeoutInSeconds);
    }

    public void defuse(int timeoutID) {
        timeoutHandler.defuse(timeoutID);
    }
}
