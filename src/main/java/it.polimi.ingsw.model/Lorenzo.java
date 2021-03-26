package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

public class Lorenzo {
    private ActionCardDeck actionCardDeck;
    private GameTable gameTable;

    /**
     * move Lorenzo forward in the faith trail
     * @param i number of moves
     */
    public void advanceFaith(int i) {
        try {
            throw new ExecutionControl.NotImplementedException("Lorenzo advance faith is not implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }
}
