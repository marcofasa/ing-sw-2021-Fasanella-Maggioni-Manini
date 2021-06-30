package it.polimi.ingsw.model;

import java.util.Stack;

/*
    A CardDevelopmentStack is a stack of at most 4 cards, all with same level and same type.
 */

public class CardDevelopmentStack {

    final int MAX_STACK_SIZE = 4;
    private final Stack<CardDevelopment> cards;

    /**
     * Base constructor
     * @param row row of stack in the market matrix
     * @param col column of stack in the market matrix
     */
    public CardDevelopmentStack(int row, int col) {

        cards = new Stack<>();

        // Populate the stack
        for (int i = MAX_STACK_SIZE - 1; i >= 0; i--) {

            // The for-loop loops backwards, in order to have the most expensive cards at the bottom of the stack.
            cards.push(new CardDevelopment(row ,col, i));
        }
    }

    /**
     * Constructor to create a deep copy of a CardDevelopmentStack
     * @param stackToBeCloned stack to be cloned
     */
    public CardDevelopmentStack(CardDevelopmentStack stackToBeCloned) {

        cards = new Stack<>();
        for (CardDevelopment card : stackToBeCloned.cards) cards.push(new CardDevelopment(card));
    }

    /* METHODS */

    //Getters

    public int getMAX_STACK_SIZE() {
        return MAX_STACK_SIZE;
    }

    /**
     * Getter for the internal card stack, which represents up to 4 cards piled on each other in the card market
     * @return a deep copy of the card stack
     */
    public Stack<CardDevelopment> getCards() {

        CardDevelopmentStack clone = new CardDevelopmentStack(this);
        return clone.cards;
    }

    // Setters

    // Class methods

    /**
     * Pushes a given card
     * @param item Instance of CardDevelopment to be pushed on the inner stack
     */
    void push(CardDevelopment item) {
        if (cards.size() < MAX_STACK_SIZE) cards.push(item);
    }

    CardDevelopment peek() {
        return cards.peek();
    }

    CardDevelopment pop() {
        return cards.pop();
    }


}
