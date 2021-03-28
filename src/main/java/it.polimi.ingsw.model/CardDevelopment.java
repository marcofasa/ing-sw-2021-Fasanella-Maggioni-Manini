package it.polimi.ingsw.model;

import java.util.Map;

public class CardDevelopment {

    // Development card state

    private CardDevelopmentType cardType;
    private CardDevelopmentLevel cardLevel;
    private Integer victoryPoints;

    private Map<Resource, Integer> cardCosts;
    private Map<Resource, Integer> productionInput;
    private Map<Resource, Integer> productionOutput;


    /* Constructor(s) */

    public CardDevelopment(
            CardDevelopmentType _cardType,
            CardDevelopmentLevel _cardLevel,
            Integer _victoryPoints,
            Map<Resource, Integer> _cardCosts,
            Map<Resource, Integer> _productionInput,
            Map<Resource, Integer> _productionOutput
    ) {

        this.cardType = _cardType;
        this.cardLevel = _cardLevel;
        this.victoryPoints = _victoryPoints;
        this.cardCosts = _cardCosts;
        this.productionInput = _productionInput;
        this.productionOutput = _productionOutput;

    }


    /* METHODS */

    // Getters

    public CardDevelopmentType getCardType() {
        return cardType;
    }

    public CardDevelopmentLevel getCardLevel() {
        return cardLevel;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public Map<Resource, Integer> getCardCosts() {
        return cardCosts;
    }

    public Map<Resource, Integer> getProductionInput() {
        return productionInput;
    }

    public Map<Resource, Integer> getProductionOutput() {
        return productionOutput;
    }

    // Setters

        // Non present because once the CardDevelopment has been created, it will never be modified

    // Class methods

        // Friendly visibility --> Method is callable only in the same package

    boolean canActivate() {

        //Check if PlayerBoard has enough resources in his Stronghold/Deposit to activate production




    }

    void activate() {



    }

}
