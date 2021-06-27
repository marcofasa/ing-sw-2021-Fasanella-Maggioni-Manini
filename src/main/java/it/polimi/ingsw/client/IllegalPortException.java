package it.polimi.ingsw.client;

/**
 * Thrown if port is not between 1024 and 65535
 */
public class IllegalPortException extends Exception{
    public IllegalPortException(){
        super("Port must be between 1024 and 65535");
    }
}
