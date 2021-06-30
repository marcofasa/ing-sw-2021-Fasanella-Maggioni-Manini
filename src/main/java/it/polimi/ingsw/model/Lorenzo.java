package it.polimi.ingsw.model;

/**
 * This class represents the NPC Lorenzo
 */
public class Lorenzo {
    private final ActionCardDeck actionCardDeck;
    private final GameTable gameTable;

    /**
     * Initializes Lorenzo and the ActionCardDeck
     * @param gameTable caller of the constructor
     */
    Lorenzo(GameTable gameTable){
        this.gameTable = gameTable;
        actionCardDeck = new ActionCardDeck(this, gameTable);
    }

    /**
     * move Lorenzo forward in the faith trail
     * @param i number of moves
     *
     */
    public void advanceFaith(int i) {
        for(int n = 0; n<i; n++){
        gameTable.moveFaithTrailLorenzo();}
    }

    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }
}
