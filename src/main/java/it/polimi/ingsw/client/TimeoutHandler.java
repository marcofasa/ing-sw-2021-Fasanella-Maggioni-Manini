package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.ClientMessage;
import it.polimi.ingsw.communication.server.ServerMessage;
import it.polimi.ingsw.communication.server.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class TimeoutHandler {

    private final Client client;
    private final ArrayList<Semaphore> semaphores;
    private final ExecutorService executors;
    private final HashMap<ServerMessage, Semaphore> semaphoreMessageMap;
    private final HashMap<ServerMessage, Boolean> messageIsInTimeMap;
    private final HashMap<Integer, Semaphore> semaphoreByID;
    private final HashMap<Integer, Boolean> idIsInTime;
    private int idTimeout;

    public TimeoutHandler(Client client) {
        this.client = client;
        semaphores = new ArrayList<>();
        executors = Executors.newCachedThreadPool();
        semaphoreMessageMap = new HashMap<>();
        messageIsInTimeMap = new HashMap<>();
        semaphoreByID = new HashMap<>();
        idIsInTime = new HashMap<>();
    }

    private Semaphore getNewSemaphore(){
        Semaphore newSem = new Semaphore(0);
        semaphores.add(newSem);
        return newSem;
    }

    public void tryDisengage(int timeoutID) throws RequestTimeoutException{
        if(!idIsInTime.get(timeoutID)){
            clearID(timeoutID);
            throw new RequestTimeoutException();
        } else {
            clearID(timeoutID);
        }
    }

    private void clearID(int timeoutID) {
        semaphoreByID.get(timeoutID).release(); //this should do nothing, but just in case
        semaphores.remove(semaphoreByID.get(timeoutID));
        semaphoreByID.remove(timeoutID);
        idIsInTime.remove(timeoutID);
    }

    public void sendAndWait(ClientMessage clientMessage, int timeoutInSeconds) throws TimeoutException {
        client.send(clientMessage); /* TODO */
        Semaphore semaphore = getNewSemaphore();
        int messageID = getID();
        semaphoreByID.put(messageID, semaphore);
        idIsInTime.put(messageID, true);
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
            timeoutExpired(messageID);
            throw new TimeoutException();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void timeoutExpired(int messageID) {
        idIsInTime.put(messageID, false);
    }

    private int getID(){
        int nextInt = ThreadLocalRandom.current().nextInt();
        while (semaphoreByID.get(nextInt) != null){
            nextInt = ThreadLocalRandom.current().nextInt();
        }
        idTimeout = nextInt;
        return idTimeout;
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