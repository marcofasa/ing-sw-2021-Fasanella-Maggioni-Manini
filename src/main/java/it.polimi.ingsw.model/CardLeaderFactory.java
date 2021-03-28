package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderFactory {
    public CardLeader produce(CardLeaderType type, Resource resource) {
        CardLeaderRequirements cardLeaderRequirements = new CardLeaderRequirements();
        HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements = null;
        switch (type){
            case Production:
                return new CardLeaderProduction(resource, cardLeaderRequirements.getRequirements(CardLeaderType.Production, resource));
            case Deposit:
                return new CardLeaderDeposit(resource, requirements);
            case WhiteMarble:
                return new CardLeaderWhiteMarble(resource, requirements);
            case Discount:
                return new CardLeaderDiscount(resource, requirements);
            default:
                throw new IllegalArgumentException();
        }
    }
}
