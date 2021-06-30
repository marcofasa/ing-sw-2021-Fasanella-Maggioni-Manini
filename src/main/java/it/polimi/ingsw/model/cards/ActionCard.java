package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.enums.ActionCardEnum;

/**
 * Abstract class for ActionCards
 */
public abstract class ActionCard {

    /**
     * Action of the card
     */
    public abstract ActionCardEnum activate();
}
