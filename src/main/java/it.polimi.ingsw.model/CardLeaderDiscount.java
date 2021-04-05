package it.polimi.ingsw.model;

public class CardLeaderDiscount extends CardLeader {

    /**
     * Constructor of the class, this should only be instanced by CardLeaderFactory
     * @param resource Resource type of card
     * @param requirements to activate the card
     * @param victoryPoints worth
     */
    public CardLeaderDiscount(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
    }

    /**
     * discounts resource to buy development cards. activate() should be called only if canActivate() == true
     */
    @Override
    public void activate() {
        if(canActivate() && !active) throw new CardLeaderRequirementsNotMetException();
        if(!active) {
            playerBoard.discountResource(resource);
            active = true;
        }
    }
}
