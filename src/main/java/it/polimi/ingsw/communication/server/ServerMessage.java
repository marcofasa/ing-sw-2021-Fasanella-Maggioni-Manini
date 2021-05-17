package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.client.ClientCommandDispatcher;
import it.polimi.ingsw.client.RequestTimeoutException;
import it.polimi.ingsw.communication.SerializedNetworkMessage;

public abstract class ServerMessage extends SerializedNetworkMessage {

    private final String message;

    private final String key;

    public ServerMessage(String message, String key){
        this.message = message;
        this.key = key;
        super.setTimeoutID(-1);
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
    public abstract void read(ClientCommandDispatcher commandDispatcher);

}
