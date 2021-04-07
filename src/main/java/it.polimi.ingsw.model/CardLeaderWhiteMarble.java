package it.polimi.ingsw.model;

public class CardLeaderWhiteMarble extends CardLeader {

    /**
     * Constructor of the class, this should only be instanced by CardLeaderFactory
     * @param resource Resource type of card
     * @param requirements to activate the card
     * @param victoryPoints worth
     */
    public CardLeaderWhiteMarble(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
    }

    /**
     * set playerboard's white marble conversion. activate() should be called only if canActivate() == true
     */
    @Override
    public void activate() {
        if(canActivate() && !active) throw new CardLeaderRequirementsNotMetException();
        playerBoard.setWhiteEffect(resource);
        active = true;
    }
}
