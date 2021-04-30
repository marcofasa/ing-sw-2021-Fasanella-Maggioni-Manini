package it.polimi.ingsw.communication.server;

public class ServerRequest extends ServerMessage{
    public ServerRequest(String message, String keyValues) {
        super(message, keyValues);
    }
}
