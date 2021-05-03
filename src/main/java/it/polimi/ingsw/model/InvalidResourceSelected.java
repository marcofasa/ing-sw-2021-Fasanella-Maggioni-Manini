package it.polimi.ingsw.model;

public class InvalidResourceSelected extends IllegalArgumentException {
    public InvalidResourceSelected(){
        super("This resource is not present!");
    }
}
