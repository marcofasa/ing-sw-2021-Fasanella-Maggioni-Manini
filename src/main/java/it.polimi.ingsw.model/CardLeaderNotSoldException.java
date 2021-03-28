package it.polimi.ingsw.model;

public class CardLeaderNotSoldException extends Exception {
    public CardLeaderNotSoldException() { super("This card has not been sold, owner is undefined"); }
}
