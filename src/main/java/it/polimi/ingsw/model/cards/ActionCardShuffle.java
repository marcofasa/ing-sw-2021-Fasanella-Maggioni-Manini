package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.Lorenzo;
import it.polimi.ingsw.model.enums.ActionCardEnum;

/**
 * Concrete ActionCard
 */
public class ActionCardShuffle extends ActionCard{

    private final Lorenzo lorenzo;

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
    public ActionCardEnum activate() {
        lorenzo.advanceFaith(1);
        lorenzo.getActionCardDeck().shuffleDeck();
        return ActionCardEnum.Shuffle;
    }
}
