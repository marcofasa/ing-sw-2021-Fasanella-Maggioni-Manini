package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.SerializedNetworkMessage;
import it.polimi.ingsw.server.ServerCommandDispatcher;

public abstract class ServerMessage extends SerializedNetworkMessage {

    private final String message;

    private final String key;

    private final int timeoutID;

    public ServerMessage(String message, String key){
        this.message = message;
        this.key = key;
        timeoutID = -1;
    }

    public ServerMessage(String message, String key, int timeoutID){
        this.message = message;
        this.key = key;
        this.timeoutID = timeoutID;
    }

    public String getKey(){
        return key;
    }

    public String getPayload(){
        return message;
    }

    /**
     * Calls the method specified in the read function
     * @param commandDispatcher Game dispatcher
     */
    public abstract void read(ClientCommandDispatcher commandDispatcher) throws RequestTimeoutException;

    public int getTimeoutID() {
        return timeoutID;
    }
}
