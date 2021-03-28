package it.polimi.ingsw.model;

import java.util.Stack;

public class CardDevelopmentSlot {

    final int MAX_SLOT_SIZE = 3;
    private Stack<CardDevelopment> cards;

    public CardDevelopmentSlot() {
        cards = new Stack<CardDevelopment>();
    }

    CardDevelopment getTop() {
        return cards.peek();
    }

    CardDevelopment placeCard(CardDevelopment cardToBePlaced) {

        if (cards.size() < MAX_SLOT_SIZE) return cards.push(cardToBePlaced);
        else return cards.peek();

    }
}
