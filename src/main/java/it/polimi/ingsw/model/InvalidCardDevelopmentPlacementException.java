package it.polimi.ingsw.model;

public class InvalidCardDevelopmentPlacementException extends Exception {

    CardDevelopment cardToBePlaced;

    public InvalidCardDevelopmentPlacementException(CardDevelopment _cardToBePlaced) {
        super("This card cannot be placed here!");
        cardToBePlaced = _cardToBePlaced;
    }
}
