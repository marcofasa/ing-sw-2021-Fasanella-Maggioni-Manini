package it.polimi.ingsw.model;

public class InvalidResourceSelected extends Exception {
    public InvalidResourceSelected(){
        super("This resource is not present!");
    }
}
