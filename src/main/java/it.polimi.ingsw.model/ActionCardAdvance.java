package it.polimi.ingsw.model;

/**
 * Concrete ActionCard
 */
public class ActionCardAdvance extends ActionCard{
    private GameTable gameTable;

    /**
     * Constructor of the class
     * @param gameTable instance of game whereto activate the card
     */
    public ActionCardAdvance(GameTable gameTable){
        this.gameTable = gameTable;
    };

    /**
     * moves Lorenzo forward 2 times
     */
    @Override
    public void activate() {
        gameTable.getLorenzoInstance().advanceFaith(2);
    }
}
