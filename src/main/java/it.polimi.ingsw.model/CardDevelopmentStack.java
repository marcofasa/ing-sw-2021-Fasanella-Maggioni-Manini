package it.polimi.ingsw.model;

import java.util.Stack;

/*
    A CardDevelopmentStack is a stack of at most 4 cards, all with same level and same type.

 */

public class CardDevelopmentStack {

    final int MAX_STACK_SIZE = 4;
    private Stack<CardDevelopment> cards;

    public CardDevelopmentStack() {

        cards = new Stack<CardDevelopment>();

        // Need to populate the stack

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

    CardDevelopment push(CardDevelopment item) {
        if (cards.size() < MAX_STACK_SIZE) return cards.push(item);
        else return cards.peek();
    }

    CardDevelopment getTop() {
        return cards.peek();
    }

    CardDevelopment pop() {
        return cards.pop();
    }

}
