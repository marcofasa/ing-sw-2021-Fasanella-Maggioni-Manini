package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderFactory {
    public CardLeader produce(CardLeaderType type, Resource resource) {
        HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements = null;
        switch (type){
            case Production:
                return new CardLeaderProduction(resource, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.Production, resource));
            case Deposit:
                return new CardLeaderDeposit(resource, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.Deposit, resource));
            case WhiteMarble:
                return new CardLeaderWhiteMarble(resource, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.WhiteMarble, resource));
            case Discount:
                return new CardLeaderDiscount(resource, CardLeaderRequirementsFinder.getRequirements(CardLeaderType.Discount, resource));
            default:
                throw new IllegalArgumentException();
        }
    }
}
