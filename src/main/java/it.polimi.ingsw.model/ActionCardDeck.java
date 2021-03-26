package it.polimi.ingsw.model;

import java.util.*;

public class ActionCardDeck {
    public ActionCardDeck(){
        usedStack = new LinkedList<ActionCard>();
        availableStack = new LinkedList<ActionCard>();
        /* TODO */
    }
    private LinkedList<ActionCard> usedStack;
    private LinkedList<ActionCard> availableStack;
    public ActionCard getCard(){return null;}
    public void shuffleDeck(){
        LinkedList<ActionCard> temp = new LinkedList<ActionCard>();
        temp.addAll(0, usedStack);
        temp.addAll(0, availableStack);
        Collections.shuffle(temp);
        usedStack = temp;
        availableStack = new LinkedList<ActionCard>();
    }
}
