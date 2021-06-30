package it.polimi.ingsw.model.exceptions;

public class InvalidResourceSelectedException extends Exception {
    public InvalidResourceSelectedException(){
        super("This resource is not present!");
    }
}
