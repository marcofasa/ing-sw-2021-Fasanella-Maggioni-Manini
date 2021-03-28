package it.polimi.ingsw.model;

public abstract class CardLeader  {
    public abstract boolean canActivate();
    public abstract void activate();
    public abstract void discard();
    public abstract void boughtBy(PlayerBoard playerBoard);
    public abstract PlayerBoard ownedBy() throws CardLeaderNotSoldException;
}
