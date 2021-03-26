package it.polimi.ingsw.model;

public class ActionCardDiscard extends ActionCard{
    private CardDevelopment discardType;
    private GameTable gameTable;

    public ActionCardDiscard(GameTable gameTable ,CardDevelopment cardDevelopmentType){
        discardType = cardDevelopmentType;
        this.gameTable = gameTable;
    }

    @Override
    public void activate(GameTable gameTable) {
        PlayerBoard players = gameTable.getPlayerBoards().get(0);
        players.discardDevCard(discardType, 2);
    }
}
