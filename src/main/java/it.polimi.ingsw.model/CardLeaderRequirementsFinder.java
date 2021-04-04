package it.polimi.ingsw.model;

import java.util.HashMap;

public class CardLeaderRequirementsFinder {
    public static CardLeaderRequirements getRequirements(CardLeaderType type, Resource resource) {
        switch (type) {
            case Production:
                HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel = new HashMap<>();
                switch (resource) {
                    case Coins:
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Green, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    case Stones:
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Purple, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    case Servants:
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Blue, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                    case Shields:
                        numberOfDevelopmentCardLevel.put(CardDevelopmentType.Yellow, CardDevelopmentLevel.Two);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardLevel, numberOfDevelopmentCardLevel,
                                null, null);
                }
                break;
            case Deposit:
                HashMap<Resource, Integer> numberOfResources = new HashMap<>();
                switch (resource) {
                    case Coins:
                        numberOfResources.put(Resource.Shields, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResurces, null,
                                null, numberOfResources);
                    case Stones:
                        numberOfResources.put(Resource.Coins, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResurces, null,
                                null, numberOfResources);
                    case Servants:
                        numberOfResources.put(Resource.Stones, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResurces, null,
                                null, numberOfResources);
                    case Shields:
                        numberOfResources.put(Resource.Servants, 5);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfResurces, null,
                                null, numberOfResources);
                }
                break;
            case WhiteMarble:
                HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes = new HashMap<>();
                switch (resource) {
                    case Coins:
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    case Stones:
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    case Servants:
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                    case Shields:
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 2);
                        numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                numberOfDevelopmentCardTypes, null);
                }
                break;
            case Discount:
                HashMap<CardDevelopmentType, Integer> _numberOfDevelopmentCardTypes = new HashMap<>();
                switch (resource) {
                    case Coins:
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    case Stones:
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    case Servants:
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Yellow, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Green, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                    case Shields:
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Blue, 1);
                        _numberOfDevelopmentCardTypes.put(CardDevelopmentType.Purple, 1);
                        return new CardLeaderRequirements(CardLeaderRequirementsType.NumberOfDevelopmentCardTypes, null,
                                _numberOfDevelopmentCardTypes, null);
                }
                break;
        }
        throw new IllegalArgumentException();
    }

    public static Integer getVictoryPoints(CardLeaderType type) {
        switch (type) {
            case Production:
                return 4;
            case Deposit:
                return 3;
            case WhiteMarble:
                return 5;
            case Discount:
                return 2;
            default:
                throw new IllegalArgumentException();
        }
    }
}
