package it.polimi.ingsw.model;

import java.util.Stack;

/*
    A CardDevelopmentStack is a stack of at most 4 cards, all with same level and same type.

 */

public class CardDevelopmentStack {

    final int MAX_STACK_SIZE = 4;
    private Stack<CardDevelopment> cards;

    public CardDevelopmentStack(int row, int col) {

        cards = new Stack<>();

        // Populate the stack
        for (int i = MAX_STACK_SIZE - 1; i >= 0; i--) {

            // The for-loop loops backwards, in order to have the most expensive cards at the bottom of the stack.
            this.push(new CardDevelopment(row ,col, i));
        }
    }

    /* METHODS */

    //Getters

    public int getMAX_STACK_SIZE() {
        return MAX_STACK_SIZE;
    }

    public Stack<CardDevelopment> getCards() {
        return cards;
    }

    // Setters

    // Class methods

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
