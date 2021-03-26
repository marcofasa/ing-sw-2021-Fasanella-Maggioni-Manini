package it.polimi.ingsw.model;

public class ActionCardAdvance extends ActionCard{
    private GameTable gameTable;

    public ActionCardAdvance(GameTable gameTable){
        this.gameTable = gameTable;
    };

    @Override
    public void activate(GameTable gameTable) {
        gameTable.getLorenzoInstance().advanceFaith(2);
    }
}
