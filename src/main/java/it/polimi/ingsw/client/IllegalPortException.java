package it.polimi.ingsw.client;

public class IllegalPortException extends Exception{
    public IllegalPortException(){
        super("port must be less than 49152 and more than 65535");
    }
}
