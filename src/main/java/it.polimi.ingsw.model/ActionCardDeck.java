package it.polimi.ingsw.model;

import java.util.*;

/**
 *
 */
public class ActionCardDeck {

    private GameTable gameTable;

    public ActionCardDeck(){
        usedStack = new Stack<ActionCard>();
        availableStack = new Stack<ActionCard>();
        ActionCard advance1 = new ActionCardAdvance(gameTable);
        ActionCard advance2 = new ActionCardAdvance(gameTable);
        ActionCard shuffle = new ActionCardShuffle(gameTable);
        CardDevelopment green = new CardDevelopmentGreen();
        CardDevelopment yellow = new CardDevelopmentYellow();
        CardDevelopment purple = new CardDevelopmentPurple();
        CardDevelopment blue = new CardDevelopmentBlue();
        ActionCard discard1 = new ActionCardDiscard(gameTable, green);
        ActionCard discard2 = new ActionCardDiscard(gameTable, yellow);
        ActionCard discard3 = new ActionCardDiscard(gameTable, purple);
        ActionCard discard4 = new ActionCardDiscard(gameTable, blue);
        availableStack.push(advance1);
        availableStack.push(advance2);
        availableStack.push(shuffle);
        availableStack.push(discard1);
        availableStack.push(discard2);
        availableStack.push(discard3);
        availableStack.push(discard4);
        Collections.shuffle(availableStack);
    }

    private Stack<ActionCard> usedStack;
    private Stack<ActionCard> availableStack;

    /**
     *
     * @return ActionCard
     */
    public ActionCard getCard(){return null;}

    /**
     *
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
