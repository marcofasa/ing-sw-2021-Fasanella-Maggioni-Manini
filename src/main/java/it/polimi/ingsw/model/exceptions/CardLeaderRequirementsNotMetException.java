package it.polimi.ingsw.model.exceptions;

public class CardLeaderRequirementsNotMetException extends RuntimeException {
    public CardLeaderRequirementsNotMetException() { super("This card cannot be activated!"); }
}
