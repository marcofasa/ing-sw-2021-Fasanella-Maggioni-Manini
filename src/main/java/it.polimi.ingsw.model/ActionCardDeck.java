package it.polimi.ingsw.model;

import java.util.*;


public class ActionCardDeck {

    private Stack<ActionCard> usedStack;
    private Stack<ActionCard> availableStack;

    /**
     * Constructor, generates a new shuffled deck
     */
    public ActionCardDeck(GameTable gameTable){
        usedStack = new Stack<ActionCard>();
        availableStack = new Stack<ActionCard>();
        ActionCard advance1 = new ActionCardAdvance(gameTable);
        ActionCard advance2 = new ActionCardAdvance(gameTable);
        ActionCard shuffle = new ActionCardShuffle(gameTable);
        ActionCard discard1 = new ActionCardDiscard(gameTable, CardDevelopmentType.Green);
        ActionCard discard2 = new ActionCardDiscard(gameTable, CardDevelopmentType.Yellow);
        ActionCard discard3 = new ActionCardDiscard(gameTable, CardDevelopmentType.Purple);
        ActionCard discard4 = new ActionCardDiscard(gameTable, CardDevelopmentType.Blue);
        availableStack.push(advance1);
        availableStack.push(advance2);
        availableStack.push(shuffle);
        availableStack.push(discard1);
        availableStack.push(discard2);
        availableStack.push(discard3);
        availableStack.push(discard4);
        shuffle.activate();
        Collections.shuffle(availableStack);
    }

    /**
     * get action card from available deck
     * @return ActionCard
     */
    public ActionCard getCard(){
        ActionCard tempActionCard = availableStack.pop();
        usedStack.push(tempActionCard);
        return tempActionCard;
    }

    /**
     *  shuffles the deck and puts back in the already used cards
     */
    public void shuffleDeck(){
        Stack<ActionCard> temp = new Stack<ActionCard>();
        temp.addAll(0, usedStack);
        temp.addAll(0, availableStack);
        Collections.shuffle(temp);
        usedStack = temp;
        availableStack = new Stack<ActionCard>();
    }
}
