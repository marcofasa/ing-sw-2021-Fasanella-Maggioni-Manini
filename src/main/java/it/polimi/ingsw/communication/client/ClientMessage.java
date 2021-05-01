package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.communication.SerializedNetworkMessage;
import it.polimi.ingsw.server.ServerCommandDispatcher;
import it.polimi.ingsw.server.VirtualClient;

public abstract class ClientMessage extends SerializedNetworkMessage {

    private final String message;

    private final String key;

    public ClientMessage(String message, String key){
        this.message = message;
        this.key = key;
    }

    public String getKey(){
        return key;
    }

    public String getPayload(){
        return message;
    }

    public abstract void read(VirtualClient virtualClient);

}
