package it.polimi.ingsw.model;

/**
 * Concrete ActionCard
 */
public class ActionCardShuffle extends ActionCard{

    private GameTable gameTable;

    /**
     * Constructor of the glass
     * @param gameTable
     */
    ActionCardShuffle(GameTable gameTable){
        this.gameTable = gameTable;
    }

    /**
     * move Lorenzo forward by 1 and shuffles the deck
     */
    @Override
    public void activate() {
        gameTable.getLorenzoInstance().advanceFaith(1);
        gameTable.getLorenzoInstance().getActionCardDeck().shuffleDeck();
    }
}
