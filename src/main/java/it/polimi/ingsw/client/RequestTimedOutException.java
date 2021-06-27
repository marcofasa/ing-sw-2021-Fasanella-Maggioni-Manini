package it.polimi.ingsw.client;

/**
 * Thrown when a package with an expired timeout id is received
 */
public class RequestTimedOutException extends Exception {
    public RequestTimedOutException(){
        super("Timeout has been exceeded");
    }
}
