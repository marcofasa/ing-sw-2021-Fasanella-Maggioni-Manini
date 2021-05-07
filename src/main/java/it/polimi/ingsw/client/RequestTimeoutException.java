package it.polimi.ingsw.client;

public class RequestTimeoutException extends Exception {
    public RequestTimeoutException(){
        super("Timeout has been exceeded");
    }
}
