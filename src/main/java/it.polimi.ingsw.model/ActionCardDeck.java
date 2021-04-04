package it.polimi.ingsw.model;

import java.util.*;


public class ActionCardDeck {

    private Stack<ActionCard> usedStack;
    private Stack<ActionCard> availableStack;

    /**
     * Constructor, generates a new shuffled deck
     */
    public ActionCardDeck(GameTable gameTable){
        usedStack = new Stack<>();
        availableStack = new Stack<>();
        ActionCard advance1 = new ActionCardAdvance(gameTable.getLorenzoInstance());
        ActionCard advance2 = new ActionCardAdvance(gameTable.getLorenzoInstance());
        ActionCard shuffle = new ActionCardShuffle(gameTable.getLorenzoInstance());
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
        availableStack.addAll(0, usedStack);
        Collections.shuffle(availableStack);
        usedStack = new Stack<>();
    }
}
