package it.polimi.ingsw.model;

import java.util.ArrayList;

public class ProductionSelection {
    private final ArrayList<Boolean> cardDevelopmentSlotActive;
    private final Boolean basicProduction;
    private final ArrayList<CardLeader> cardLeadersToActivate;

    public ProductionSelection(ArrayList<Boolean> cardDevelopmentSlotActive, Boolean basicProduction, ArrayList<CardLeader> cardLeadersToActivate){
        this.basicProduction = basicProduction;
        this.cardDevelopmentSlotActive = cardDevelopmentSlotActive;
        this.cardLeadersToActivate = cardLeadersToActivate;
    }

    public ArrayList<Boolean> getCardDevelopmentSlotActive() {
        return cardDevelopmentSlotActive;
    }

    public Boolean getBasicProduction() {
        return basicProduction;
    }

    public ArrayList<CardLeader> getCardLeadersToActivate() {
        return cardLeadersToActivate;
    }
}
