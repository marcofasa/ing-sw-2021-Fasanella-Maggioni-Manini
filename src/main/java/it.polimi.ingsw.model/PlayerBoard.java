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

    private Strongbox strongbox;

    /**
     * Tutte le risorse sono inizializzate a 0 sempre!
     */
    private HashMap<Resource, Integer> cardLeaderDeposit;

    private ArrayList<Resource> cardLeaderDepositTypes;
    
    private Resource cardLeaderProductionResource;


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

    //QUESTA FUNZIONE VA RIFATTA, NON TIENE CONTO DEL DEPOSITO LEADER!
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

    public boolean canAddLeaderDeposit(HashMap<Resource, Integer> resources){
        for (Resource resource: resources.keySet()) {
            if(!cardLeaderDepositTypes.contains(resource)) return false;
            if(resources.get(resource) + cardLeaderDeposit.get(resource) > 2) return false;
        }
        return true;
    }

    public boolean tryAddLeaderDeposit(HashMap<Resource, Integer> resources){
        if (!canAddLeaderDeposit(resources)) return false;
        for (Resource resource : resources.keySet()) {
            cardLeaderDeposit.replace(resource, cardLeaderDeposit.get(resource) + resources.get(resource));
        }
        return true;
    }

    public void addLeaderDepositType(Resource resource) {
        cardLeaderDepositTypes.add(resource);
    }

    public Resource getCardLeaderProductionResource() {
        return cardLeaderProductionResource;
    }

    public void setCardLeaderProductionResource(Resource resource) {
        cardLeaderProductionResource = resource;
    }

    public void addToStrongbox(Resource cardLeaderProductionResource) {
    }
}
