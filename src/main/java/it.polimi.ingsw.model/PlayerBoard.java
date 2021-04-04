package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerBoard {

    private Resource whiteEffect;

    private CardDevelopmentSlot cardSlot;

    private GameTable gameTable;

    private ArrayList<CardLeader> cardsLeaderBeforeSelecting;

    private ArrayList<CardLeader> cardsLeader;

    private HashMap<Resource, Integer> tempDeposit;

    private Deposit deposit;

    public ArrayList<Marble> getMarketRow(Integer integer) {
        return gameTable.getMarketInstance().getRow(integer);
    }

    public ArrayList<Marble> getMarketCol(Integer integer) {
        return gameTable.getMarketInstance().getCol(integer);
    }

    public void discardCardLeader(CardLeader cardLeader){
        cardsLeader.remove(cardLeader);
    }

    public void addToTemporaryDeposit(Resource resource){
        Integer numberOfResourcesOrNull = tempDeposit.get(resource);
        if(numberOfResourcesOrNull == null) {
            tempDeposit.putIfAbsent(resource, 1);
        } else {
            tempDeposit.replace(resource, numberOfResourcesOrNull + 1);
        }
    }

    private void resetTemporaryDeposit(){
        tempDeposit = new HashMap<>();
    }

    public boolean tryAddMarbles(ArrayList<Marble> marbles){
        resetTemporaryDeposit();
        for (Marble marble: marbles
             ) {
            marble.activate(this);
        }
        return deposit.tryAdd(tempDeposit);
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
