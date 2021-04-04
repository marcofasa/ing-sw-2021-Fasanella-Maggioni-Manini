package it.polimi.ingsw.model;


import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CardLeaderRequirements {
    private final CardLeaderRequirementsType cardLeaderRequirementsType;

    private HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel;

    private HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes;

    private HashMap<Resource, Integer> numberOfResurces;

    public CardLeaderRequirements(CardLeaderRequirementsType cardLeaderRequirementsTypes,
                                  @Nullable HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel,
                                  @Nullable HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes,
                                  @Nullable HashMap<Resource, Integer> numberOfResources
                                  ){
        this.cardLeaderRequirementsType = cardLeaderRequirementsTypes;
        switch (cardLeaderRequirementsTypes){
            case NumberOfDevelopmentCardTypes:
                if(numberOfDevelopmentCardTypes == null)
                    throw new IllegalArgumentException();
                this.numberOfDevelopmentCardTypes = numberOfDevelopmentCardTypes;
                break;
            case NumberOfDevelopmentCardLevel:
                if(numberOfDevelopmentCardLevel == null)
                    throw new IllegalArgumentException();
                this.numberOfDevelopmentCardLevel = numberOfDevelopmentCardLevel;
                break;
            case NumberOfResurces:
                if(numberOfResources == null)
                    throw new IllegalArgumentException();
                this.numberOfResurces = numberOfResources;
                break;
        }
    }

    public boolean meetsRequirements(PlayerBoard playerBoard){
        try {
            throw new ExecutionControl.NotImplementedException("meets requirements has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
