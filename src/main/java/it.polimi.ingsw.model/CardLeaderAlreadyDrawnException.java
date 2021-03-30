package it.polimi.ingsw.model;

public class CardLeaderAlreadyDrawnException extends RuntimeException {
    public CardLeaderAlreadyDrawnException() { super("This card has already been drawn!"); }
}
