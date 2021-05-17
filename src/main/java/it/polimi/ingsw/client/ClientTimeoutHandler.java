package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.client.ClientRequest;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class ClientTimeoutHandler {

    private final Client client;
    private final ArrayList<Semaphore> semaphores;
    private final ExecutorService executors;
    private final HashMap<Integer, Semaphore> semaphoreByID;
    private final HashMap<Integer, Boolean> idIsInTime;

    public ClientTimeoutHandler(Client client) {
        this.client = client;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
    }

    private Semaphore getNewSemaphore(){
        Semaphore newSem = new Semaphore(0);
        semaphores.add(newSem);
        return newSem;
    }

    public void tryDisengage(int messageTimeoutID) throws RequestTimeoutException{
        if(messageTimeoutID == -1)
            return;
        if(!idIsInTime.get(messageTimeoutID)){
            clearID(messageTimeoutID);
            throw new RequestTimeoutException();
        } else {
            clearID(messageTimeoutID);
        }
    }

    private void clearID(int messageTimeoutID) {
        semaphoreByID.get(messageTimeoutID).release();
        semaphores.remove(semaphoreByID.get(messageTimeoutID));
        semaphoreByID.remove(messageTimeoutID);
        idIsInTime.remove(messageTimeoutID);
    }

    /**
     * Sends clientMessage to the Server and awaits answer for timeoutInSeconds time
     * @param clientMessage message to be sent
     * @param timeoutInSeconds timeout in seconds to wait for a server answer; -1 to wait indefinitely
     * @throws TimeoutException thrown when timeout is expired
     */
    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws TimeoutException {
        if(timeoutInSeconds<0 && timeoutInSeconds != -1) throw new IllegalArgumentException("timeoutInSeconds must be > 0 or == -1");
        client.getView().displayWaiting(timeoutInSeconds);
        Semaphore semaphore = getNewSemaphore();
        int messageTimeoutID = getID();
        semaphoreByID.put(messageTimeoutID, semaphore);
        idIsInTime.put(messageTimeoutID, true);
        clientMessage.setTimeoutID(messageTimeoutID);
        client.send(clientMessage);
        if(timeoutInSeconds == -1){
            try {
                executors.submit(() -> {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
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
            } catch (TimeoutException e) {
                timeoutExpired(messageTimeoutID);
                throw new TimeoutException();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
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