package it.polimi.ingsw.client;

public class IllegalPortException extends Exception{
    public IllegalPortException(){
        super("Port must be between 1024 and 65535");
    }
}
