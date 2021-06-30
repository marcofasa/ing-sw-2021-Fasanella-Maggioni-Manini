package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Lorenzo;
import it.polimi.ingsw.model.enums.ActionCardEnum;

/**
 * Concrete ActionCard
 */
public class ActionCardAdvance extends ActionCard{
    private final Lorenzo lorenzo;

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
    public ActionCardEnum activate() {
        lorenzo.advanceFaith(2);
        return ActionCardEnum.Advance;
    }
}
