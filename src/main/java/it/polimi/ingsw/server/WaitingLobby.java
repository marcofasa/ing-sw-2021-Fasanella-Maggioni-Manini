package it.polimi.ingsw.server;

import java.util.concurrent.Semaphore;

public class WaitingLobby {

    private boolean empty;

    private int lobbySize;

    private Semaphore semaphore;

    private Semaphore semaphore2;

    private Object firstPlayerLock;

    public WaitingLobby(){
        empty = true;
        semaphore = new Semaphore(0);
    }

    public void addFirstPlayer(VirtualClient virtualClient) {
        try {
            if(!empty){
                semaphore.release();
                addPlayer(virtualClient);
                return;
            }
            virtualClient.send("RequestPlayerSize");
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void addPlayer(VirtualClient virtualClient) {
    }


    public void setLobbySize(int size){
        lobbySize = size;
        semaphore.release();
    }

    public boolean isEmpty() {
        return empty;
    }
}
