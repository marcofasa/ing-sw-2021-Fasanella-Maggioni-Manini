package it.polimi.ingsw.model;

import java.util.HashMap;
import java.util.Map;

public class CardLeaderProduction extends CardLeader{

    CardLeaderProduction(Resource resource, CardLeaderRequirements requirements, Integer victoryPoints){
        this.resource = resource;
        this.requirements = requirements;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public void activate() {
        if(!canActivate()) throw new CardLeaderRequirementsNotMetException();
        active = true;
        HashMap<Resource, Integer> resources = new HashMap<>();
        resources.put(playerBoard.getCardLeaderProductionResource(), 1);
        playerBoard.addToStrongbox(resource);
    }
}
