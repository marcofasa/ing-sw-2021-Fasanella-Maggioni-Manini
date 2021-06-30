package it.polimi.ingsw.model.exceptions;

/**
 * Runtime Exception thrown if active() is called before canActivate()
 */
public class CardLeaderWrongOwnerException extends RuntimeException {
    public CardLeaderWrongOwnerException() { super("This PlayerBoard is not the owner of this card!"); }
}
