package it.polimi.ingsw.communication.timeout_handler;

import it.polimi.ingsw.client.RequestTimedOutException;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.util.concurrent.TimeoutException;

/**
 * Proxy class from which to call TimeoutHandler
 * It only allows ServerMessage s through preventing a Server to send ClientMessage s
 */
public class ServerTimeoutHandler {

    final TimeoutHandler timeoutHandler;

    public ServerTimeoutHandler(VirtualClient virtualClient){
        timeoutHandler = new TimeoutHandler(virtualClient);
    }

    /**
     * tries to stop a timer from running out. This doesn't unlock the thread stuck waiting, it only
     * stops the timeout from going off while the message received is executed.
     * This is made to prevent a timeout from running out while the Message is being executed,
     * potentially breaking the game logic.
     * @param messageTimeoutID ID to stop
     * @throws RequestTimedOutException thrown if the timeout has already expired
     */
    public void tryDisengage(int messageTimeoutID) throws RequestTimedOutException {
        timeoutHandler.tryDisengage(messageTimeoutID);
    }

    /**
     * sends a message to the Client and waits until a ClientResponse regarding this message is received
     * @param serverMessage message to be sent
     * @param timeoutInSeconds timeout given to the server to respond. -1 to wait indefinitely
     * @throws TimeoutException thrown if the given timeout expires
     */
    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws TimeoutException {
        timeoutHandler.sendAndWait(serverMessage, timeoutInSeconds);
    }

    /**
     * definitely removes the timer and releases the waiting thread.
     * @param timeoutID timeoutID to release
     */
    public void defuse(int timeoutID) {
        timeoutHandler.defuse(timeoutID);
    }
}
