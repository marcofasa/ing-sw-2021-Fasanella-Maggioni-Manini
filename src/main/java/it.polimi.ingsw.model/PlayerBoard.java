package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;

public class PlayerBoard {

    private Resource whiteEffect;

    private CardDevelopmentSlot cardSlot;

    private GameTable gameTable;

    private ArrayList<CardLeader> cardLeaderBeforeSelecting;

    private ArrayList<CardLeader> cardLeader;


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
