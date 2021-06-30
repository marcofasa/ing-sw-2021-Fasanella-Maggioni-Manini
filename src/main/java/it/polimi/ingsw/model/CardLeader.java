package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the leader cards
 */
public abstract class CardLeader implements Serializable {

    protected String playerName;

    protected CardLeaderRequirements requirements;

    protected Integer victoryPoints;

    protected boolean active;

    protected Resource resource;

    // ---- GETTERS
    public CardLeaderRequirements getRequirements(){
        return requirements;
    }

    public boolean getActivationState(){
        return active;
    }

    public Resource getResource(){
        return resource;
    }

    // end of getters

    /**
     * Check if requirements are met
     * @return true if activate() can be called, false otherwise
     */
    public boolean canActivate(PlayerBoard playerBoard) {
        return requirements.meetsRequirements(playerBoard);
    }

    /**
     * Activate the cards if the requirements are met. activate() should be called only if canActivate() == true
     */
    public abstract void activate(PlayerBoard playerBoard);

    /**
     * discard this CardLeader from the player's deck
     */
    public void discard(PlayerBoard playerBoard){
        playerBoard.discardCardLeader(this);
        playerBoard.moveFaith(1);
    }

    /**
     * associates a CardLeader to a player
     * @param playerBoard future owner of the card
     * @return this
     */
    public CardLeader draw(PlayerBoard playerBoard){
        if (this.playerName != null) {
            throw new CardLeaderAlreadyDrawnException();
        }
        if (playerBoard == null) {
            throw new IllegalArgumentException("playerBoard cannot be null");
        }
        this.playerName = playerBoard.getNickname();
        return this;
    }

    /**
     * returns victory points value of the card if it active
     * @return number of victory points worth
     */
    public Integer getVictoryPoints(){
        return active? victoryPoints : 0;
    }

    /**
     * returns victory points value of the card
     * @return victory points given by this card
     */
    public Integer getVictoryPointsValue(){
        return victoryPoints;
    }

    /**
     * Returns owner of the card, null if this card is still in the deck
     * @return PlayerBoard associated to the card
     */
    public String getPlayerName() {
        return playerName;
    }

    public abstract CardLeaderType getDescription();
}