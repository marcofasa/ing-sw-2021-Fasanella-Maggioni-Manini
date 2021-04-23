package it.polimi.ingsw.model;

public class CardLeaderDeposit extends CardLeader{

    /**
     * Constructor of the class, this should only be instanced by CardLeaderFactory
     * @param resource Resource type of card
     * @param requirements to activate the card
     * @param victoryPoints worth
     */
    public CardLeaderDeposit(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
        active = false;
    }

    /**
     * adds resource to the LeaderDepositType in player. activate() should be called only if canActivate() == true
     */
    @Override
    public void activate() {
        if(!canActivate() && !active) throw new CardLeaderRequirementsNotMetException();
        if(!active) {
            playerBoard.getDepositLeaderCardInstance().addLeaderDepositType(resource);
            active = true;
        }
    }
}
