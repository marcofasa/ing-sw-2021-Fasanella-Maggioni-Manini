package it.polimi.ingsw.communication.server;

public class ServerStringMessageForTesting extends ServerMessage{
    private final String message;

    public ServerStringMessageForTesting(String message){
        this.message = message;
    }

    public String getPayload(){
        return message;
    }
}
