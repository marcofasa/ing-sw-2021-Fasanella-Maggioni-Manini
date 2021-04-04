package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderFactory {
    public CardLeader produce(CardLeaderType type, Resource resource) {
        switch (type){
            case Production:
                return new CardLeaderProduction(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                        CardLeaderRequirementsFinder.getVictoryPoints(type));
            case Deposit:
                return new CardLeaderDeposit(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                        CardLeaderRequirementsFinder.getVictoryPoints(type));
            case WhiteMarble:
                return new CardLeaderWhiteMarble(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                        CardLeaderRequirementsFinder.getVictoryPoints(type));
            case Discount:
                return new CardLeaderDiscount(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                        CardLeaderRequirementsFinder.getVictoryPoints(type));
            default:
                throw new IllegalArgumentException();
        }
    }
}
