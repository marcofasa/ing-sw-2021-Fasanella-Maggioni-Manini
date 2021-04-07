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

    /**
     * Method to place an acquired CardDevelopment on PersonalBoard.
     * The method incapsulates the placement logic, by which a card can be placed only on top of a card of 1 level lower
     *
     * @param cardToBePlaced The bought card to be placed on the personal board
     * @return the card that was pushed successfully
     * @throws FullSlotException when the player tries to place a card on a full slot
     * @throws InvalidCardDevelopmentPlacementException when an illegal placement is made
     */
    CardDevelopment placeCard(CardDevelopment cardToBePlaced) throws FullSlotException, InvalidCardDevelopmentPlacementException {

        if (cards.size() == MAX_SLOT_SIZE) throw new FullSlotException(id);

        switch (cardToBePlaced.getCardLevel()) {

            case One:

                if (cards.size() == 0) return cards.push(cardToBePlaced);
                else throw new InvalidCardDevelopmentPlacementException();

            case Two:

                if (cards.size() == 1 &&
                        getTop().getCardLevel() == CardDevelopmentLevel.One) return cards.push(cardToBePlaced);
                else throw  new InvalidCardDevelopmentPlacementException();

            case Three:

                if (cards.size() == 2 &&
                        getTop().getCardLevel() == CardDevelopmentLevel.Two) return cards.push(cardToBePlaced);
                else throw new InvalidCardDevelopmentPlacementException();
        }

        //This return should never be reached!
        return null;
    }
}
