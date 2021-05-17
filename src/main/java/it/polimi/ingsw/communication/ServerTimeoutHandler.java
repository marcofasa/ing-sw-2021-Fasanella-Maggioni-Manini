package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServerTimeoutHandler {

    TimeoutHandler timeoutHandler;

    public ServerTimeoutHandler(VirtualClient virtualClient){
        timeoutHandler = new TimeoutHandler(virtualClient);
    }

    public void tryDisengage(int messageTimeoutID) throws RequestTimeoutException {
        timeoutHandler.tryDisengage(messageTimeoutID);
    }

    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws TimeoutException {
        timeoutHandler.sendAndWait(serverMessage, timeoutInSeconds);
    }

    public void defuse(int timeoutID) {
        timeoutHandler.defuse(timeoutID);
    }
}
