package it.polimi.ingsw.model;

public class ActionCardShuffle extends ActionCard{

    private GameTable gameTable;

    ActionCardShuffle(GameTable gameTable){
        this.gameTable = gameTable;
    }

    @Override
    public void activate(GameTable gameTable) {
        gameTable.getLorenzoInstance().advanceFaith(1);
        gameTable.getLorenzoInstance().getActionCardDeck().shuffleDeck();
    }
}
