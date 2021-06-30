package it.polimi.ingsw.communication.timeout_handler;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimedOutException;
import it.polimi.ingsw.communication.SerializedNetworkMessage;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Main TimeoutHandler class
 * Handles the sendAndWait of Client and Server
 */
class TimeoutHandler {

    private final Client client;
    private final VirtualClient virtualClient;
    private final ArrayList<Semaphore> semaphores;
    private final ExecutorService executors;
    private final HashMap<Integer, Semaphore> semaphoreByID;
    private final HashMap<Integer, Boolean> idIsInTime;
    private final boolean isServerHandler;

    /**
     * Client side constructor
     * @param client owner of the class
     */
    public TimeoutHandler(Client client) {
        this.virtualClient = null;
        this.client = client;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
        isServerHandler = false;
    }

    /**
     * Server side constructor
     * @param virtualClient owner of the class
     */
    public TimeoutHandler(VirtualClient virtualClient) {
        this.client = null;
        this.virtualClient = virtualClient;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
        isServerHandler = true;
    }

    /**
     * private method to create a new semaphore
     * @return the newly created semaphore
     */
    private Semaphore getNewSemaphore(){
        Semaphore newSem = new Semaphore(0);
        semaphores.add(newSem);
        return newSem;
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
        if(messageTimeoutID == -1)
            return;
        if(!idIsInTime.get(messageTimeoutID)){
            semaphoreByID.get(messageTimeoutID).release();
            throw new RequestTimedOutException();
        } else {
            semaphoreByID.get(messageTimeoutID).release();
        }
    }

    /**
     * unregisters the timeoutID
     * @param messageTimeoutID to be unregistered
     */
    private void clearID(int messageTimeoutID) {
        if(messageTimeoutID != -1) {
            semaphoreByID.get(messageTimeoutID).release();
            semaphores.remove(semaphoreByID.get(messageTimeoutID));
            semaphoreByID.remove(messageTimeoutID);
            idIsInTime.remove(messageTimeoutID);
        }
    }

    /**
     * Sends serverMessage to the Server and awaits answer for timeoutInSeconds time
     * @param serverMessage message to be sent
     * @param timeoutInSeconds timeout in seconds to wait for a server answer; -1 to wait indefinitely
     * @throws TimeoutException thrown when timeout is expired
     */
    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws TimeoutException {
        if(!isServerHandler) throw new RuntimeException("This method can only be called from a Virtual Client!");
        if(timeoutInSeconds<0 && timeoutInSeconds != -1) throw new IllegalArgumentException("timeoutInSeconds must be > 0 or == -1");
        int messageTimeoutID = getID();
        Semaphore semaphore = setupTimerAndGetSemaphore(serverMessage, messageTimeoutID);
        virtualClient.send(serverMessage);
        pauseHandler(timeoutInSeconds, semaphore, messageTimeoutID);
    }

    private Semaphore setupTimerAndGetSemaphore(SerializedNetworkMessage message, int messageTimeoutID) {
        Semaphore semaphore = getNewSemaphore();
        semaphoreByID.put(messageTimeoutID, semaphore);
        idIsInTime.put(messageTimeoutID, true);
        message.setTimeoutID(messageTimeoutID);
        return semaphore;
    }

    /**
     * Sends clientMessage to the Server and awaits answer for timeoutInSeconds time
     * @param clientMessage message to be sent
     * @param timeoutInSeconds timeout in seconds to wait for a server answer; -1 to wait indefinitely
     * @throws TimeoutException thrown when timeout is expired
     */
    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws TimeoutException {
        if(isServerHandler) throw new RuntimeException("This method can only be called from an actual Client!");
        if(timeoutInSeconds<0 && timeoutInSeconds != -1) throw new IllegalArgumentException("timeoutInSeconds must be > 0 or == -1");
        // client.getView().displayWaiting(timeoutInSeconds);
        int messageTimeoutID = getID();
        Semaphore semaphore = setupTimerAndGetSemaphore(clientMessage, messageTimeoutID);
        client.send(clientMessage);
        pauseHandler(timeoutInSeconds, semaphore, messageTimeoutID);
    }

    /**
     * Core method of the class
     * This class launches 2 semaphore, the first one is the actual timer, the second one exist to allow the
     * message to execute and then releases the method
     * @param timeoutInSeconds timeout to wait
     * @param semaphore to wait on
     * @param messageTimeoutID ID of the message to send in the TimeoutException
     * @throws TimeoutException thrown if timeout is reached
     */
    private void pauseHandler(int timeoutInSeconds, Semaphore semaphore, int messageTimeoutID) throws TimeoutException {
        if(timeoutInSeconds == -1){
            try {
                semaphore.acquire();
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                executors.submit(() -> {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).get(timeoutInSeconds, TimeUnit.SECONDS);
                semaphore.acquire();
            } catch (TimeoutException e) {
                timeoutExpired(messageTimeoutID);
                throw new TimeoutException();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * definitely removes the timer and releases the waiting thread.
     * @param timeoutID timeoutID to release
     */
    public void defuse(int timeoutID) {
        clearID(timeoutID);
    }

    /**
     * sets the message timeout map to false, when a message containing this timeout is received
     * an error is thrown
     * @param messageTimeoutID timeoutID
     */
    private void timeoutExpired(int messageTimeoutID) {
        idIsInTime.put(messageTimeoutID, false);
    }

    /**
     * gets a new randomized id
     * @return timeoutID
     */
    private int getID(){
        int nextInt = ThreadLocalRandom.current().nextInt();
        while (semaphoreByID.get(nextInt) != null){
            nextInt = ThreadLocalRandom.current().nextInt();
        }
        return nextInt;
    }
}