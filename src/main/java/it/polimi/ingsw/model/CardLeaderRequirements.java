package it.polimi.ingsw.model;


import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;

public class CardLeaderRequirements implements Serializable {
    private final CardLeaderRequirementsType cardLeaderRequirementsType;

    private HashMap<CardDevelopmentType, CardDevelopmentLevel> numberOfDevelopmentCardLevel;

    private HashMap<CardDevelopmentType, Integer> numberOfDevelopmentCardTypes;

    private HashMap<Resource, Integer> numberOfResources;

    /**
     * Constructor of the class, takes null values for numberOfDevelopmentCardLevel, numberOfDevelopmentCardTypes, numberOfResources. The param
     * specified in the CardLeaderRequirementsType must no be null.
     * @param cardLeaderRequirementsTypes Type of the requirements
     * @param numberOfDevelopmentCardLevel Map describing requirements of the LeaderCard if cardLeaderRequirementsTypes == NumberOfDevelopmentCardLevel.
     *                                     Can be multiple CardDevelopmentType with assigned level required
     * @param numberOfDevelopmentCardTypes Map describing requirements of the LeaderCard if cardLeaderRequirementsTypes == NumberOfDevelopmentCardTypes.
     *                                     Can be multiple CardDevelopmentType with assigned the number of each type required
     * @param numberOfResources Map describing requirements of the LeaderCard if cardLeaderRequirementsTypes == NumberOfResources.
     *                                     Can be multiple Resources with the assigned number required
     */
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
            case NumberOfResources:
                if(numberOfResources == null)
                    throw new IllegalArgumentException();
                this.numberOfResources = numberOfResources;
                break;
        }
    }

    /**
     * Establish if a playerBoard has the necessary requirements to activate this LeaderCard
     * @param playerBoard must be the owner of the card
     * @return boolean if requirements are met
     */
    public boolean meetsRequirements(PlayerBoard playerBoard){
        switch (cardLeaderRequirementsType) {
            case NumberOfDevelopmentCardTypes:
                for (CardDevelopmentType type :
                        numberOfDevelopmentCardTypes.keySet()) {
                    if (playerBoard.getAllDevelopmentCards().stream().filter(x -> x.getCardType() == type).count() < numberOfDevelopmentCardTypes.get(type))
                        return false;
                }
                return true;
            case NumberOfDevelopmentCardLevel:
                for (var type :
                        numberOfDevelopmentCardLevel.keySet()) {
                    boolean reached = false;
                    for (var card:
                         playerBoard.getAllDevelopmentCards()) {
                        if (card.getCardLevel().ordinal() >= numberOfDevelopmentCardLevel.get(type).ordinal())
                            reached = true;
                    }
                    if (!reached)
                            return false;
                }
                return true;
            case NumberOfResources:
                return playerBoard.hasResources(numberOfResources);
        }
        throw new IllegalArgumentException();
    }

}
