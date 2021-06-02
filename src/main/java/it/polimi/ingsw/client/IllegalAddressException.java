package it.polimi.ingsw.client;

public class IllegalAddressException extends Exception{
    public IllegalAddressException(){
        super("The address given is invalid");
    }
}
