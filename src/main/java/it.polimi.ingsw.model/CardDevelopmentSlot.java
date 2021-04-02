package it.polimi.ingsw.model;

import java.util.Stack;

public class CardDevelopmentSlot {

    final int MAX_SLOT_SIZE = 3;
    final CardDevelopmentSlotID id;
    private final Stack<CardDevelopment> cards;

    public CardDevelopmentSlot(CardDevelopmentSlotID _id) {
        cards = new Stack<>();
        id = _id;
    }

    public CardDevelopmentSlotID getId() {
        return id;
    }

    Stack<CardDevelopment> getCards() {
        return cards;
    }

    CardDevelopment getTop() {
        return cards.peek();
    }

    CardDevelopment placeCard(CardDevelopment cardToBePlaced) throws FullSlotException {

        if (cards.size() < MAX_SLOT_SIZE) return cards.push(cardToBePlaced);

        else throw new FullSlotException(id);

    }
}
