package it.polimi.ingsw.model;

import java.util.HashMap;

/**
 * Implements Factory Pattern
 */
public class CardLeaderFactory {
    /**
     * produce a CardLeader of the desired CardLeaderType and resource
     * @param type CardLeaderType desired
     * @param resource resource master of the card
     * @return concrete CardLeader's child
     */
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
