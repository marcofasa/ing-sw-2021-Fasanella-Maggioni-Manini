package it.polimi.ingsw.model;

import java.util.List;

public class ActionCardDeck {
    private List<ActionCard> usedStack;
    private List<ActionCard> availableStack;
    public ActionCard getCard();
    public void shuffleDeck(){
        List<ActionCard> temp = new List<ActionCard>();
        temp.addAll(0, usedStack);
    };
}
