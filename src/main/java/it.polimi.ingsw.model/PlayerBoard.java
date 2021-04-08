package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerBoard {

    private String nickname;
    private boolean first;
    private PlayerState playerState;
    private Resource whiteEffect;
    private CardDevelopmentSlot[] cardSlotArray;
    private GameTable gameTable;
    private ArrayList<CardLeader> cardsLeaderBeforeSelecting;
    private ArrayList<CardLeader> cardsLeader;
    private HashMap<Resource, Integer> tempDeposit;
    private Deposit deposit;
    private Strongbox strongbox;
    private Resource cardLeaderProductionOutput;
    private DepositLeaderCard depositLeaderCard;


    public PlayerBoard(String _nickname, boolean _first, PlayerState _playerState, GameTable _gameTable) {
        nickname = _nickname;
        first = _first;
        playerState = _playerState;

        strongbox = getStrongboxInstance();
        deposit = getDepositInstance();
        cardSlotArray = new CardDevelopmentSlot[3];

        gameTable = _gameTable;
        cardsLeaderBeforeSelecting = new ArrayList<>();
        cardsLeader = new ArrayList<>();
    }

    //TODO getter card development : FATTO
    public ArrayList<CardDevelopment> getAllDevelopmentCards() {

        ArrayList<CardDevelopment> outputList = new ArrayList<>();
        ArrayList<CardDevelopment> tempList;

        for (CardDevelopmentSlot cardDevelopmentSlot : cardSlotArray) {

            // Il costruttore di ArrayList puo' prendere una Collection: crea una lista con gli elementi della collezione ricevuta
            tempList = new ArrayList<>(cardDevelopmentSlot.getCards());
            outputList.addAll(tempList);
        }

        return outputList;
    }

    public ArrayList<Marble> getMarketRow(Integer integer) {
        return gameTable.getMarketInstance().getRow(integer);
    }

    public ArrayList<Marble> getMarketCol(Integer integer) {
        return gameTable.getMarketInstance().getCol(integer);
    }

    public ArrayList<CardLeader> getCardsLeader() {
        return cardsLeader;
    }

    public ArrayList<CardLeader> getCardsLeaderBeforeSelecting() {
        return cardsLeaderBeforeSelecting;
    }

    //TODO mettere instance : FATTO
    public Strongbox getStrongboxInstance() {

        if (strongbox == null) strongbox = new Strongbox();

        return strongbox;
    }

    //TODO mettere instance : FATTO
    public Deposit getDepositInstance() {

        if (deposit == null) deposit = new Deposit();

        return deposit;
    }

    //RISOLVERE IN QUALCHE MODO QUESTE DUE
    void discardCardLeaderController(CardLeader cardLeader) {
        cardLeader.discard();
    }

    void discardCardLeader(CardLeader cardLeader) {
        cardsLeader.remove(cardLeader);
    }

    void addToTemporaryDeposit(Resource resource) {
        Integer numberOfResourcesOrNull = tempDeposit.get(resource);
        if (numberOfResourcesOrNull == null) {
            tempDeposit.putIfAbsent(resource, 1);
        } else {
            tempDeposit.replace(resource, numberOfResourcesOrNull + 1);
        }
    }

    private void resetTemporaryDeposit() {
        tempDeposit = new HashMap<>();
        for (Resource resource :
                Resource.values()) {
            tempDeposit.put(resource, 0);
        }
    }

    public void discardFromTemporaryDeposit(Resource resource) {

        //Remove 1 Resource from the temporary deposit
        tempDeposit.replace(resource, tempDeposit.get(resource) - 1);

        //TODO Move all other players forward by 1 position on the faith trail : FATTO
        gameTable.moveOthersFaithTrail(this);
    }

    boolean tryAddMarbles(ArrayList<Marble> marbles) {
        resetTemporaryDeposit();
        for (Marble marble : marbles
        ) {
            marble.activate(this);
        }
        getDepositLeaderCard().add(tempDeposit);
        return deposit.tryAdd(tempDeposit);
    }

    public void getCardLeader() {
        gameTable.getCardLeader(this);
    }

    public Resource getWhiteEffect() {
        return whiteEffect;
    }

    void setWhiteEffect(Resource whiteEffect) {
        this.whiteEffect = whiteEffect;
    }

    //TODO AGGIUSTARE IN QUALCHE MODO : FATTO
    public void moveFaith(int i) {
        gameTable.moveFaithTrail(this, i);
    }

    public Resource getCardLeaderProductionOutput() {
        return cardLeaderProductionOutput;
    }

    public void setCardLeaderProductionOutput(Resource resource) {
        cardLeaderProductionOutput = resource;
    }

    //TODO Confermare che questo metodo deve solamente aggiungere risorse allo strongbox
    void addToStrongbox(HashMap<Resource, Integer> cardLeaderProductionResource) {
        getStrongboxInstance().tryAdd(cardLeaderProductionResource);
    }

    /**
     * Checks if strongbox and deposit COMBINED hold enough resources as specified in the param numberOfResources
     * @param numberOfResources A HashMap of the amounts to be met in order to return true
     * @return true if strongbox + deposit hold enough resources, false otherwise
     */
    public boolean hasResources(HashMap<Resource, Integer> numberOfResources) {

        HashMap<Resource, Integer> temp = new HashMap<>(getStrongboxInstance().getContent());

        for (Resource res : Resource.values()) {
            temp.replace(res, temp.get(res) + getDepositInstance().getContent().get(res));
        }

        for (Resource res : Resource.values()) {
            if (numberOfResources.get(res) > temp.get(res)) return false;
        }

        return true;
    }

    public void discountResource(Resource resource) {
        //???
    }

    public DepositLeaderCard getDepositLeaderCard() {
        if (depositLeaderCard == null)
            depositLeaderCard = new DepositLeaderCard();
        return depositLeaderCard;
    }

    public boolean isFirst(){
        return first;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }


    //FUNZIONI GROSSE

}
