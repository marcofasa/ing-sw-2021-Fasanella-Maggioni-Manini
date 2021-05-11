package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class is to be instantiated in the Client, populated based on user input when the player
 * desires to activate his production powers and sent to the Server for action exectuion.
 *
 * The constructor creates an empty, useless instance. It must be populated through the class setters before
 * being sent.
 */
public class ProductionSelection implements Serializable {

    private Boolean basicProduction; // true -> basic production selected, false -> basic production not selected
    private Resource[] basicProdInfo; // basicProdInfo[0] and basicProdInfo[1] are the basic production inputs, basicProdInfo[2] the output
    private Boolean[] cardDevelopmentSlotActive; // array of 3 booleans, if true -> CardDevelopment on top of the indexed stack is to be activated
    private CardLeader[] cardLeadersToActivate; // array of at most 2 card leader production
    private Resource[] cardLeaderProdOutputs; // array of Resources selected to be the output by leader card production powers

    public ProductionSelection()
    {
        this.basicProduction = false;
        this.basicProdInfo = new Resource[3];
        this.cardDevelopmentSlotActive = new Boolean[3];
        this.cardLeadersToActivate = new CardLeader[2];
        this.cardLeaderProdOutputs = new Resource[2];
    }

    // Getters

    public Boolean[] getCardDevelopmentSlotActive() {
        return cardDevelopmentSlotActive;
    }

    public Boolean getBasicProduction() {
        return basicProduction;
    }

    public CardLeader[] getCardLeadersToActivate() {
        return cardLeadersToActivate;
    }

    public Resource[] getBasicProdInfo() {
        return basicProdInfo;
    }

    public Resource[] getCardLeaderProdOutputs() {
        return cardLeaderProdOutputs;
    }

    // Setters

    public void setBasicProdInfo(Resource[] basicProdInfo) {
        this.basicProdInfo = basicProdInfo;
    }

    public void setBasicProduction(Boolean basicProduction) {
        this.basicProduction = basicProduction;
    }

    public void setCardDevelopmentSlotActive(Boolean[] cardDevelopmentSlotActive) {
        this.cardDevelopmentSlotActive = cardDevelopmentSlotActive;
    }

    public void setCardLeadersToActivate(CardLeader[] cardLeadersToActivate) {
        this.cardLeadersToActivate = cardLeadersToActivate;
    }

    public void setCardLeaderProdOutputs(Resource[] cardLeaderProdOutputs) {
        this.cardLeaderProdOutputs = cardLeaderProdOutputs;
    }
}
