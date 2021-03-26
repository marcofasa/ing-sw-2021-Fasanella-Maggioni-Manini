package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

public class PlayerBoard {

    private Resource whiteEffect;

    private CardDevelopmentSlot cardSlot;

    /**
     * discard development card from PlayerBoard
     * @param discardType type of development card
     * @param i number of dev. cards to discard
     */
    public void discardDevelopmentCard(CardDevelopmentType discardType, int i) {
        try {
            throw new ExecutionControl.NotImplementedException("discardDevCard has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    public Resource getWhiteEffect() {
        return whiteEffect;
    }

    public void setWhiteEffect(Resource whiteEffect) {
        this.whiteEffect = whiteEffect;
    }
}
