package it.polimi.ingsw.communication;

import it.polimi.ingsw.server.ServerCommandDispatcher;

import java.io.Serializable;

public abstract class SerializedNetworkMessage implements Serializable {

    private int timeoutID;

    public void setTimeoutID(int timeoutID){
        if(this.timeoutID == -1)
            this.timeoutID = timeoutID;
        else
            throw new RuntimeException("TimeoutID has already been set!");
    }

    public int getTimeoutID() {
        return timeoutID;
    }

}
