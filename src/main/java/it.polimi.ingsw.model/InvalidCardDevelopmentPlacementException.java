package it.polimi.ingsw.model;

public class InvalidCardDevelopmentPlacementException extends Exception {

    public InvalidCardDevelopmentPlacementException() {
        super("This card cannot be placed here!");
    }
}
