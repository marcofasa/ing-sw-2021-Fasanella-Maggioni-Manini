package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.communication.SerializedNetworkMessage;

public abstract class ClientMessage extends SerializedNetworkMessage {

    private final String message;

    private final String keyValues;

    public ClientMessage(String message, String keyValues){
        this.message = message;
        this.keyValues = keyValues;
    }

    public String getKeyValues(){
        return keyValues;
    }

    public String getPayload(){
        return message;
    }
}
