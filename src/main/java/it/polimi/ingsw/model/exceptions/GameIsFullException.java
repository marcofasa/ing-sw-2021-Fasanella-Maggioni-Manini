package it.polimi.ingsw.model.exceptions;

public class GameIsFullException extends RuntimeException {
    public GameIsFullException(){
        super("Cannot add any more players");
    }
}
