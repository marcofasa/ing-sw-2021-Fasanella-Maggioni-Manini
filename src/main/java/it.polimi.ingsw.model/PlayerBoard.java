package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class PlayerBoard {

    private Resource whiteEffect;

    private CardDevelopmentSlot cardSlot;

    private GameTable gameTable;

    private ArrayList<CardLeader> cardLeaderBeforeSelecting;

    private ArrayList<CardLeader> cardLeader;

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

    public void getCardLeader(){
        gameTable.getCardLeader(this);
    }

    public Resource getWhiteEffect() {
        return whiteEffect;
    }

    public void setWhiteEffect(Resource whiteEffect) {
        this.whiteEffect = whiteEffect;
    }

    public void moveFaith(int i) {
    }
}
