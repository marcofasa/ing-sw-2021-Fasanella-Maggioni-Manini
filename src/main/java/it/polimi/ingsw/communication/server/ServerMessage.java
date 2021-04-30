package it.polimi.ingsw.communication.server;

import it.polimi.ingsw.communication.SerializedNetworkMessage;

public class ServerMessage extends SerializedNetworkMessage {
    private final String message;

    private final String keyValues;

    public ServerMessage(String message, String keyValues){
        this.message = message;
        this.keyValues = keyValues;
    }
}
