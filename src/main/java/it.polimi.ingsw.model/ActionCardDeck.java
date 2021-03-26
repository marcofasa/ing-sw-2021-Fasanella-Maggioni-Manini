package it.polimi.ingsw.model;

import java.util.*;

/**
 *
 */
public class ActionCardDeck {

    public ActionCardDeck(){
        usedStack = new Stack<ActionCard>();
        availableStack = new Stack<ActionCard>();
        ActionCard advance1 = new ActionCardAdvance();
        ActionCard advance2 = new ActionCardAdvance();
        availableStack.push(advance1);
        availableStack.push(advance2);
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
