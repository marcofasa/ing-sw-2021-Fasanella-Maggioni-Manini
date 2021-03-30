package it.polimi.ingsw.model;

/**
 * Concrete ActionCard
 */
public class ActionCardShuffle extends ActionCard{

    private Lorenzo lorenzo;

    /**
     * Constructor of the class
     * @param lorenzo istance of Lorenzo
     */
    ActionCardShuffle(Lorenzo lorenzo){
        this.lorenzo = lorenzo;
    }

    /**
     * move Lorenzo forward by 1 and shuffles the deck
     */
    @Override
    public void activate() {
        lorenzo.advanceFaith(1);
        lorenzo.getActionCardDeck().shuffleDeck();
    }
}
