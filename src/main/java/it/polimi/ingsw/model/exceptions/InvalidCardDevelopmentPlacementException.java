package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.cards.CardDevelopment;

public class InvalidCardDevelopmentPlacementException extends Exception {

    final CardDevelopment cardToBePlaced;

    public InvalidCardDevelopmentPlacementException(CardDevelopment _cardToBePlaced) {
        super("This card cannot be placed here!");
        cardToBePlaced = _cardToBePlaced;
    }
}
