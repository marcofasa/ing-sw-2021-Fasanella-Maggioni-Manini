package it.polimi.ingsw.model;

/**
 * Concrete ActionCard
 */
public class ActionCardAdvance extends ActionCard{
    private Lorenzo lorenzo;

    /**
     * Constructor of the class
     * @param lorenzo instance of Lorenzo
     */
    public ActionCardAdvance(Lorenzo lorenzo){
        this.lorenzo = lorenzo;
    }

    /**
     * moves Lorenzo forward 2 times
     */
    @Override
    public void activate() {
        lorenzo.advanceFaith(2);
    }
}
