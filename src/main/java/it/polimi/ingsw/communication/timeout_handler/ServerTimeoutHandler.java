package it.polimi.ingsw.communication.timeout_handler;

import it.polimi.ingsw.client.RequestTimedOutException;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.util.concurrent.TimeoutException;

public class ServerTimeoutHandler {

    final TimeoutHandler timeoutHandler;

    public ServerTimeoutHandler(VirtualClient virtualClient){
        timeoutHandler = new TimeoutHandler(virtualClient);
    }

    public void tryDisengage(int messageTimeoutID) throws RequestTimedOutException {
        timeoutHandler.tryDisengage(messageTimeoutID);
    }

    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws TimeoutException {
        timeoutHandler.sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void defuse(int timeoutID) {
        timeoutHandler.defuse(timeoutID);
    }
}
