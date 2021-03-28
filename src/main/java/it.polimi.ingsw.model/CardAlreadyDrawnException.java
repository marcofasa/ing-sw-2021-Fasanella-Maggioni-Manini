package it.polimi.ingsw.model;

public class CardAlreadyDrawnException extends RuntimeException {
    public CardAlreadyDrawnException() { super("This card has already been drawn!"); }
}
