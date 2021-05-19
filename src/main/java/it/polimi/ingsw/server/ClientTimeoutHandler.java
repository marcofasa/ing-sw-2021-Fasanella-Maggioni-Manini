package it.polimi.ingsw.server;

import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.server.ServerMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.*;

public class ClientTimeoutHandler {

    private final VirtualClient client;
    private final ArrayList<Semaphore> semaphores;
    private final ExecutorService executors;
    private final HashMap<Integer, Semaphore> semaphoreByID;
    private final HashMap<Integer, Boolean> idIsInTime;

    public ClientTimeoutHandler(VirtualClient client) {
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

    public void tryDisengage(int messageTimeoutID) throws RequestTimeoutException {
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
     * Sends serverMessage to the Server and awaits answer for timeoutInSeconds time
     * @param serverMessage message to be sent
     * @param timeoutInSeconds timeout in seconds to wait for a server answer; -1 to wait indefinitely
     * @throws TimeoutException thrown when timeout is expired
     */
    public void sendAndWait(ServerMessage serverMessage, int timeoutInSeconds) throws TimeoutException {
        if(timeoutInSeconds<0 && timeoutInSeconds != -1) throw new IllegalArgumentException("timeoutInSeconds must be > 0 or == -1");
        Semaphore semaphore = getNewSemaphore();
        int messageTimeoutID = getID();
        semaphoreByID.put(messageTimeoutID, semaphore);
        idIsInTime.put(messageTimeoutID, true);
        serverMessage.setTimeoutID(messageTimeoutID);
        client.send(serverMessage);
        if(timeoutInSeconds == -1){
            try {
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
