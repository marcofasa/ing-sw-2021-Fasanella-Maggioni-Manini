package it.polimi.ingsw.model;

public class CardLeaderRequirementsFinder {
    public static CardLeaderRequirements getRequirements(CardLeaderType type, Resource resource) {
        switch (type) {
            case Production:
                switch (resource) {
                    case Coins:
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardType, null, null, null);
                    case Stones:
                        break;
                    case Servants:
                        break;
                    case Shields:
                        break;
                }
                break;
            case Deposit:
                break;
            case WhiteMarble:
                break;
            case Discount:
                break;
        }
        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardType, null, null, null);
    }
}
