package it.polimi.ingsw.communication.client;

import it.polimi.ingsw.communication.SerializedNetworkMessage;

public class ClientMessage extends SerializedNetworkMessage {

    protected String message;

    public String getPayload(){
        return message;
    }
}
