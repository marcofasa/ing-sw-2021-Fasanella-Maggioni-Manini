package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CardLeaderRequirements {
    public HashMap<CardDevelopmentType, CardDevelopmentLevel> getRequirements(CardLeaderType production, Resource resource) {
        HashMap<CardDevelopmentType, CardDevelopmentLevel> requirements = new HashMap<>();
        switch (production) {
            case Production:
                switch (resource) {
                    case Coins:
                        requirements.put(CardDevelopmentType.Purple, CardDevelopmentLevel.One);
                        requirements.put(CardDevelopmentType.Green, CardDevelopmentLevel.One);
                        return requirements;
                    case Stone:
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
    return requirements;}
}
