package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderProduction extends CardLeader{

    /**
     * Constructor of the class, this should only be instanced by CardLeaderFactory
     * @param resource Resource type of card
     * @param requirements to activate the card
     * @param victoryPoints worth
     */
    CardLeaderProduction(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
    }

    /**
     * gives 1 resource to playerboard's strongbox and moves the player forward in the faith track by 1
     * activate() should be called only if canActivate() == true
     */
    @Override
    public void activate() {
        if(canActivate() && !active) throw new CardLeaderRequirementsNotMetException();
        active = true;
        if (playerBoard.getCardLeaderProductionOutput() == null) throw new IllegalArgumentException();
        playerBoard.getDepositInstance().discard(resource);
        playerBoard.getStrongboxInstance().addResource(playerBoard.getCardLeaderProductionOutput(), 1);
        playerBoard.moveFaith(1);
    }
}
