package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerBoard {

    private Resource whiteEffect;

    private CardDevelopmentSlot cardSlot;

    private GameTable gameTable;

    private ArrayList<CardLeader> cardLeaderBeforeSelecting;

    private ArrayList<CardLeader> cardLeader;

    private HashMap<Resource, Integer> tempDeposit;

    private Deposit deposit;

    private Strongbox strongbox;


    public PlayerBoard(GameTable gameTable) {
        this.gameTable = gameTable;
        this.strongbox = new Strongbox();
        this.deposit = new Deposit();
    }

    public ArrayList<Marble> getMarketRow(Integer integer) {
        return gameTable.getMarketInstance().getRow(integer);
    }

    public ArrayList<Marble> getMarketCol(Integer integer) {
        return gameTable.getMarketInstance().getCol(integer);
    }

    public Strongbox getStrongbox() {
        return this.strongbox;
    }

    public Deposit getDeposit() {
        return deposit;
    }

    public void addToTemporaryDeposit(Resource resource){
        Integer numberOfResourcesOrNull = tempDeposit.get(resource);
        if(numberOfResourcesOrNull == null) {
            tempDeposit.putIfAbsent(resource, 1);
        } else {
            tempDeposit.replace(resource, numberOfResourcesOrNull + 1);
        }
    }

    public void resetTemporaryDeposit(){
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
