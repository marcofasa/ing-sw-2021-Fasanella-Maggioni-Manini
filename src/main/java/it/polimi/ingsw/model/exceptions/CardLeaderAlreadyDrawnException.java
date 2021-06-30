package it.polimi.ingsw.model.exceptions;

/**
 * Runtime Exception thrown if active() is called before canActivate()
 */
public class CardLeaderAlreadyDrawnException extends RuntimeException {
    public CardLeaderAlreadyDrawnException() { super("This card has already been drawn!"); }
}
