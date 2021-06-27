package it.polimi.ingsw.model;

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
        return switch (type) {
            case Production -> new CardLeaderProduction(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                    CardLeaderRequirementsFinder.getVictoryPoints(type));
            case Deposit -> new CardLeaderDeposit(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                    CardLeaderRequirementsFinder.getVictoryPoints(type));
            case WhiteMarble -> new CardLeaderWhiteMarble(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                    CardLeaderRequirementsFinder.getVictoryPoints(type));
            case Discount -> new CardLeaderDiscount(resource, CardLeaderRequirementsFinder.getRequirements(type, resource),
                    CardLeaderRequirementsFinder.getVictoryPoints(type));
        };
    }
}
