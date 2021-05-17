package it.polimi.ingsw.communication;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.server.VirtualClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

class TimeoutHandler {

    private final Client client;
    private final VirtualClient virtualClient;
    private final ArrayList<Semaphore> semaphores;
    private final ExecutorService executors;
    private final HashMap<Integer, Semaphore> semaphoreByID;
    private final HashMap<Integer, Boolean> idIsInTime;
    private final boolean isServerHandler;

    public TimeoutHandler(Client client) {
        this.virtualClient = null;
        this.client = client;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
        isServerHandler = false;
    }

    public TimeoutHandler(VirtualClient virtualClient) {
        this.client = null;
        this.virtualClient = virtualClient;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
        isServerHandler = true;
    }

    private Semaphore getNewSemaphore(){
        Semaphore newSem = new Semaphore(0);
        semaphores.add(newSem);
        return newSem;
    }

    public void tryDisengage(int messageTimeoutID) throws RequestTimeoutException {
        if(messageTimeoutID == -1)
            return;
        if(!idIsInTime.get(messageTimeoutID)){
            semaphoreByID.get(messageTimeoutID).release();
            throw new RequestTimeoutException();
        } else {
            semaphoreByID.get(messageTimeoutID).release();
        }
    }

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

    public void defuse(int timeoutID) {
        clearID(timeoutID);
    }

    private void timeoutExpired(int messageTimeoutID) {
        idIsInTime.put(messageTimeoutID, false);
    }

    private int getID(){
        int nextInt = ThreadLocalRandom.current().nextInt();
        while (semaphoreByID.get(nextInt) != null){
            nextInt = ThreadLocalRandom.current().nextInt();
        }
        return nextInt;
    }
}

/*
OLD:
 public void waitOn(ServerResponse serverMessage, int timeoutInSeconds) throws TimeoutException {
        Semaphore semaphore = getNewSemaphore();
        semaphoreMessageMap.put(serverMessage, semaphore);
        messageIsInTimeMap.put(serverMessage, true);
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
            // fai le cose
            timeoutExpired(serverMessage);
            throw new TimeoutException();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void timeoutExpired(ServerMessage serverMessage) {
        messageIsInTimeMap.put(serverMessage, false);
    }

    public void checkAndSuspend(ServerMessage serverMessage) throws RequestTimeoutException{
        if(!messageIsInTimeMap.get(serverMessage)){
            disengage(serverMessage);
            throw new RequestTimeoutException();
        }
        semaphoreMessageMap.get(serverMessage).release();
    }

    public void disengage(ServerMessage serverMessage) throws RequestTimeoutException{
        semaphoreMessageMap.get(serverMessage).release();
        semaphores.remove(semaphoreMessageMap.get(serverMessage));
        messageIsInTimeMap.remove(serverMessage);
        semaphoreMessageMap.remove(serverMessage);
    }

 */