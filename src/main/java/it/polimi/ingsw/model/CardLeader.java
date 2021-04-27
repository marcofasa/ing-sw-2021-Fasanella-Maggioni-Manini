package it.polimi.ingsw.model;

public abstract class CardLeader  {


    protected PlayerBoard playerBoard;

    protected CardLeaderRequirements requirements;

    protected Integer victoryPoints;

    protected boolean active = false;

    protected Resource resource;


    /**
     * Check if requirements are met
     * @return true if activate() can be called, false otherwise
     */
    public boolean canActivate() {
        return requirements.meetsRequirements(playerBoard);
    }

    /**
     * Activate the cards if the requirements are met. activate() should be called only if canActivate() == true
     */
    public abstract void activate();

    /**
     * discard this CardLeader from the player's deck
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
        if (playerBoard == null) {
            throw new IllegalArgumentException("playerBoard cannot be null");
        }
        this.playerBoard = playerBoard;
        return this;
    }

    /**
     * returns victory points value of the card
     * @return number of victory points worth
     */
    public Integer getVictoryPoints(){
        return active? victoryPoints : 0;
    }

    /**
     * Returns owner of the card, null if this card is still in the deck
     * @return PlayerBoard associated to the card
     */
    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }
}