package it.polimi.ingsw.client;

/**
 * Thrown if address given is invalid
 */
public class IllegalAddressException extends Exception{
    public IllegalAddressException(){
        super("The address given is invalid");
    }
}
