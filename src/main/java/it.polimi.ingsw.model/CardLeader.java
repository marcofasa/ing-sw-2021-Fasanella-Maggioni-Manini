package it.polimi.ingsw.model;

public abstract class CardLeader  {

    private PlayerBoard playerBoard;

    public abstract boolean canActivate();

    public abstract void activate();

    /**
     * discard this actionCard from the player's deck
     */
    public void discard(){
        playerBoard.moveFaith(1);
        playerBoard.discardCardLeader(this);
    }

    /**
     * associates a CardLeader to a player
     * @param playerBoard future owner of the card
     * @return this
     */
    public CardLeader draw(PlayerBoard playerBoard){
        if (this.playerBoard != null) {
            throw new CardLeaderAlreadyDrawnException();
        }
        this.playerBoard = playerBoard;
        return this;
        }
}