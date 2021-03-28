package it.polimi.ingsw.model;

public abstract class CardLeader  {

    private PlayerBoard playerBoard;

    public abstract boolean canActivate();

    public abstract void activate();

    public void discard(){
        playerBoard.moveFaith(1);
    };


    public CardLeader draw(PlayerBoard playerBoard){
        if (playerBoard == null) {
            throw new CardAlreadyDrawnException();
        }
        this.playerBoard = playerBoard;
        return this;
        }
}