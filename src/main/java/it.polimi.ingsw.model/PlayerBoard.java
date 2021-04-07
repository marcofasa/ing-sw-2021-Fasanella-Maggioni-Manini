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

    private Resource cardLeaderProductionOutput;

    private DepositLeaderCard depositLeaderCard;

    public PlayerBoard(GameTable gameTable) {
        this.gameTable = gameTable;
        this.strongbox = new Strongbox();
        this.deposit = new Deposit();
    }

    //TODO getter card development

    public ArrayList<Marble> getMarketRow(Integer integer) {
        return gameTable.getMarketInstance().getRow(integer);
    }

    public ArrayList<Marble> getMarketCol(Integer integer) {
        return gameTable.getMarketInstance().getCol(integer);
    }

    //mettere instance
    public Strongbox getStrongbox() {
        return this.strongbox;
    }

    //mettere instance
    public Deposit getDeposit() {
        return deposit;
    }

    //RISOLVERE IN QUALCHE MODO QUESTE DUE
    void discardCardLeaderController(CardLeader cardLeader){
        cardLeader.discard();
    }

    void discardCardLeader(CardLeader cardLeader){
        cardsLeader.remove(cardLeader);
    }
    
    void addToTemporaryDeposit(Resource resource){
        Integer numberOfResourcesOrNull = tempDeposit.get(resource);
        if(numberOfResourcesOrNull == null) {
            tempDeposit.putIfAbsent(resource, 1);
        } else {
            tempDeposit.replace(resource, numberOfResourcesOrNull + 1);
        }
    }

    private void resetTemporaryDeposit(){
        tempDeposit = new HashMap<>();
        for (Resource resource:
             Resource.values()) {
            tempDeposit.put(resource, 0);
        }
    }

    public void discardFromTemporaryDeposit(Resource resource){
        tempDeposit.replace(resource, tempDeposit.get(resource) - 1);
        //MOVE FORWARD BY 1 TODO
    }

    boolean tryAddMarbles(ArrayList<Marble> marbles){
        resetTemporaryDeposit();
        for (Marble marble: marbles
             ) {
            marble.activate(this);
        }
        getDepositLeaderCard().add(tempDeposit);
        return deposit.tryAdd(tempDeposit);
    }

    public void getCardLeader(){
        gameTable.getCardLeader(this);
    }

    public Resource getWhiteEffect() {
        return whiteEffect;
    }

    void setWhiteEffect(Resource whiteEffect) {
        this.whiteEffect = whiteEffect;
    }

    //AGGIUSTARE IN QUALCHE MODO
    public void moveFaith(int i) {
        //gameTable.faiqualcosafaith()
    }

    public Resource getCardLeaderProductionOutput() {
        return cardLeaderProductionOutput;
    }

    public void setCardLeaderProductionOutput(Resource resource) {
        cardLeaderProductionOutput = resource;
    }

    void addToStrongbox(HashMap<Resource, Integer> cardLeaderProductionResource) {
    }

    public boolean hasResources(HashMap<Resource, Integer> numberOfResources) {
        return true;
    }

    public void discountResource(Resource resource) {
        //???
    }

    public DepositLeaderCard getDepositLeaderCard(){
        if (depositLeaderCard == null)
            depositLeaderCard = new DepositLeaderCard();
        return depositLeaderCard;
    }


    //FUNZIONI GROSSE

}
