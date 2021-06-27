package it.polimi.ingsw.model;

import java.util.HashMap;

/**
 * Utility Class to find CardLeader Requirements and victory points
 */
public class CardLeaderRequirementsFinder {

    /**
     * static method, finds the requirements of the card
     * @param type specifier of the card
     * @param resource specifier of the card
     * @return CardLeaderRequirements to be assigned to the CardLeader's requirements
     */
    public static CardLeaderRequirements getRequirements(CardLeaderType type, Resource resource) {
        switch (type) {
            case Production -> {
                HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel = new HashMap<>();
                switch (resource) {
                    case Coins -> {
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Green, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    }
                    case Stones -> {
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Purple, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    }
                    case Servants -> {
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Blue, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    }
                    case Shields -> {
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Yellow, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    }
                }
            }
            case Deposit -> {
                HashMap<Resource, Integer> numberOfResources = new HashMap<>();
                switch (resource) {
                    case Coins -> {
                        numberOfResources.put(Resource.Shields, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResources, null,
                                null, numberOfResources);
                    }
                    case Stones -> {
                        numberOfResources.put(Resource.Coins, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResources, null,
                                null, numberOfResources);
                    }
                    case Servants -> {
                        numberOfResources.put(Resource.Stones, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResources, null,
                                null, numberOfResources);
                    }
                    case Shields -> {
                        numberOfResources.put(Resource.Servants, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResources, null,
                                null, numberOfResources);
                    }
                }
            }
            case WhiteMarble -> {
                HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes = new HashMap<>();
                switch (resource) {
                    case Coins -> {
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    }
                    case Stones -> {
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    }
                    case Servants -> {
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    }
                    case Shields -> {
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    }
                }
            }
            case Discount -> {
                HashMap<CardDevelopmentType, Integer> _numberOfDevelopmentCardTypes = new HashMap<>();
                switch (resource) {
                    case Coins -> {
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    }
                    case Stones -> {
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    }
                    case Servants -> {
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    }
                    case Shields -> {
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    }
                }
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * static method to find victory points assigned to a card
     * @param type required to find victoryPoints
     * @return victoryPoints of the card
     */
    public static Integer getVictoryPoints(CardLeaderType type) {
        return switch (type) {
            case Production -> 4;
            case Deposit -> 3;
            case WhiteMarble -> 5;
            case Discount -> 2;
            default -> throw new IllegalArgumentException();
        };
    }
}
