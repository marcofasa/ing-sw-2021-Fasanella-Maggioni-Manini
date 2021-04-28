package it.polimi.ingsw.communication.client;

public class SetupConnection extends ClientMessage{
    private final String message;

    public SetupConnection(String message){
        this.message = message;
    }

    public String getPayload(){
        return message;
    }
}
