package it.polimi.ingsw.communication.timeout_handler;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimedOutException;
import it.polimi.ingsw.communication.client.ClientMessage;

import java.util.concurrent.TimeoutException;

/**
 * Proxy class from which to call TimeoutHandler
 * It only allows ClientMessage s through preventing a client to send ServerMessage s
 */
public class ClientTimeoutHandler {
    final TimeoutHandler timeoutHandler;

    public ClientTimeoutHandler(Client client){
        timeoutHandler = new TimeoutHandler(client);
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
     * sends a message to the Server and waits until a ServerResponse regarding this message is received
     * @param clientMessage message to be sent
     * @param timeoutInSeconds timeout given to the server to respond. -1 to wait indefinitely
     * @throws TimeoutException thrown if the given timeout expires
     */
    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws TimeoutException {
        timeoutHandler.sendAndWait(clientMessage, timeoutInSeconds);
    }

    /**
     * definitely removes the timer and releases the waiting thread.
     * @param timeoutID timeoutID to release
     */
    public void defuse(int timeoutID) {
        timeoutHandler.defuse(timeoutID);
    }
}
