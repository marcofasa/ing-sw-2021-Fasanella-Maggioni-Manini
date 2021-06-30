package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enums.CardLeaderType;
import it.polimi.ingsw.model.enums.Resource;
import it.polimi.ingsw.model.exceptions.CardLeaderRequirementsNotMetException;
import it.polimi.ingsw.model.exceptions.CardLeaderWrongOwnerException;

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
        active = false;
    }

    /**
     * gives 1 resource to playerboard's strongbox and moves the player forward in the faith track by 1
     * activate() should be called only if canActivate() == true
     */
    @Override
    public void activate(PlayerBoard playerBoard) {
        if(!playerBoard.getNickname().equals(playerName)) throw new CardLeaderWrongOwnerException();
        if(!canActivate(playerBoard) && !active) throw new CardLeaderRequirementsNotMetException();
        active = true;
        if (playerBoard.getCardLeaderProductionOutput() == null) throw new IllegalArgumentException();

        HashMap<Resource, Integer> cost = new HashMap<>();
        for (Resource res : Resource.values()) cost.put(res, 0);

        cost.put(resource, 1);

        playerBoard.consumeResources(cost);

        playerBoard.getStrongboxInstance().addResource(playerBoard.getCardLeaderProductionOutput(), 1);
        playerBoard.moveFaith(1);
    }

    @Override
    public CardLeaderType getDescription() {
        return CardLeaderType.Production;
    }
}
