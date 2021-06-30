package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.enums.ActionCardEnum;
import it.polimi.ingsw.model.enums.CardDevelopmentType;

public class ActionCardDiscard extends ActionCard{
    private final CardDevelopmentType cardDevelopmentType;
    private final GameTable gameTable;

    /**
     * Constructor of the class,
     * @param gameTable instance of game whereto activate the card
     * @param cardDevelopmentType type of card to discard when activated
     */
    public ActionCardDiscard(GameTable gameTable, CardDevelopmentType cardDevelopmentType){
        this.cardDevelopmentType = cardDevelopmentType;
        this.gameTable = gameTable;
    }

    /**
     * Discard two development cards of the type selected when activated
     */
    @Override
    public ActionCardEnum activate() {
        gameTable.discardDevelopmentCardFromSlot(cardDevelopmentType);
        return ActionCardEnum.Discard;
    }
}
