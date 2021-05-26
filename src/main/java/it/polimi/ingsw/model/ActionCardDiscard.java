package it.polimi.ingsw.model;

public class ActionCardDiscard extends ActionCard{
    private CardDevelopmentType cardDevelopmentType;
    private GameTable gameTable;

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
