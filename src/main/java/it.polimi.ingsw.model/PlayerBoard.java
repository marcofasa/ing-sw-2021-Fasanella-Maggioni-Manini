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

    /**
     *Constructor
     * @param _nickname of the player
     * @param _first true if is the first player
     * @param _playerState if the player is Playing or Idle
     * @param _gameTable
     */
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

    public Strongbox getStrongboxInstance() {

        if (strongbox == null) strongbox = new Strongbox();

        return strongbox;
    }

    public Deposit getDepositInstance() {

        if (deposit == null) deposit = new Deposit();

        return deposit;
    }

    public CardDevelopmentSlot getCardDevelopmentSlotByIndex(int index) throws InvalidSlotIndexException {

        if (index < 0 || index > 2) throw new InvalidSlotIndexException();

        return cardSlotArray[index];
    }

    //RISOLVERE IN QUALCHE MODO QUESTE DUE
    void discardCardLeaderController(CardLeader cardLeader) {
        cardLeader.discard();
    }

    void discardCardLeader(CardLeader cardLeader) {
        cardsLeader.remove(cardLeader);
    }

    /**
     * Adds a resource to the Temporary Deposit
     * @param resource
     */
    void addToTemporaryDeposit(Resource resource) {
        Integer numberOfResourcesOrNull = tempDeposit.get(resource);
        if (numberOfResourcesOrNull == null) {
            tempDeposit.putIfAbsent(resource, 1);
        } else {
            tempDeposit.replace(resource, numberOfResourcesOrNull + 1);
        }
    }

    /**
     * Resets the Temporary Deposit
     */
    private void resetTemporaryDeposit() {
        tempDeposit = new HashMap<>();
        for (Resource resource :
                Resource.values()) {
            tempDeposit.put(resource, 0);
        }
    }

    /**
     * For each call all the players move of one step except this player
     * @param resource
     */
    public void discardFromTemporaryDeposit(Resource resource) {

        //Remove 1 Resource from the temporary deposit
        tempDeposit.replace(resource, tempDeposit.get(resource) - 1);

        gameTable.moveOthersFaithTrail(this);
    }

    /**
     * Resets the Temporary Deposit and tries to add the given arraylist of marbles
     * @param marbles to be added
     * @return
     */
    boolean tryAddMarbles(ArrayList<Marble> marbles) {
        resetTemporaryDeposit();
        for (Marble marble : marbles
        ) {
            marble.activate(this);
        }
        getDepositLeaderCardInstance().add(tempDeposit);
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

    /**
     * Moves this player of i steps
     * @param i number of steps
     */
    public void moveFaith(int i) {
        gameTable.moveFaithTrail(this, i);
    }

    Resource getCardLeaderProductionOutput() {
        return cardLeaderProductionOutput;
    }

    private void setCardLeaderProductionOutput(Resource resource) {
        cardLeaderProductionOutput = resource;
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

    public DepositLeaderCard getDepositLeaderCardInstance() {
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

    /**
     * Giving two resources you can get one of any type
     * @param input1
     * @param input2
     * @param output
     */
    private void activateBasicProduction(Resource input1, Resource input2, Resource output){
        getDepositInstance().discard(input1);
        getDepositInstance().discard(input2);
        getStrongboxInstance().addResource(output, 1);
    }

    /**
     *
     * @param cardDevelopmentSlotToActivate
     */
    private void activateCardProduction(CardDevelopmentSlot cardDevelopmentSlotToActivate){
        cardDevelopmentSlotToActivate.getTop().activateProduction(this);
    }

    /**
     * Checks if cardLeader can be activated and calls activate() on it
     * @param cardLeaderToBeActivated
     * @param output
     */
    private void activateLeaderProduction(CardLeader cardLeaderToBeActivated, Resource output){
        if(cardLeaderToBeActivated.getClass() != CardLeaderProduction.class) throw new IllegalArgumentException("this CardLeader is not a CardLeaderProduction");
        setCardLeaderProductionOutput(output);
        cardLeaderToBeActivated.activate();
    }

    public Integer getVictoryPoints(){
        Integer victoryPoints = 0;

        //Faith Trail
        victoryPoints += gameTable.getFaithTrailInstance().getVictoryPoints(this);

        //Carte leader
        for (CardLeader cardLeader :
                cardsLeader) {
            victoryPoints += cardLeader.getVictoryPoints();
        }

        //Deposit
        for (Resource resource:
             getDepositInstance().getContent().keySet()) {
            victoryPoints += getDepositInstance().getContent().get(resource);
        }

        //Strongbox
        for (Resource resource:
                getStrongboxInstance().getContent().keySet()) {
            victoryPoints += getStrongboxInstance().getContent().get(resource);
        }

        //CardLeaderProduction
        for (Resource resource:
                getDepositLeaderCardInstance().getContent().keySet()) {
            victoryPoints += getStrongboxInstance().getContent().get(resource);
        }

        //CardDevelopment
        for (CardDevelopment cardDevelopment:
                getAllDevelopmentCards()) {
            victoryPoints += cardDevelopment.getVictoryPoints();
        }

        return victoryPoints;

    }

    //FUNZIONI GROSSE

    public CardDevelopment buyCardDevelopmentCardFromMarket(int rowIndex, int colIndex) throws NotEnoughResourcesException {

        CardDevelopmentMarket marketInstance = gameTable.getCardDevelopmentMarketInstance();
        CardDevelopmentStack desiredStack = marketInstance.getMarket()[rowIndex][colIndex];
        CardDevelopment desiredCard  = desiredStack.peek();

        if (hasResources(desiredCard.getCardCosts())) {
            return marketInstance.buyCardFromStack(this, rowIndex, colIndex);
        } else {
            throw new NotEnoughResourcesException(this.nickname, rowIndex, colIndex);
        }
    }

    public void placeCardDevelopmentCardOnBoard(CardDevelopment cardToBePlaced, int cardSlotIndex) throws InvalidCardDevelopmentPlacementException, FullSlotException, InvalidSlotIndexException {

        /*
        Exception should not be handled here : it should float to the Controller, where if it is caught the
        Controller must select a valid index.
         */
        CardDevelopmentSlot targetSlot = getCardDevelopmentSlotByIndex(cardSlotIndex);


        /*
        Exceptions should be handled in the Controller :
        FullSlotException is thrown when a card is added to a stack that already contains 3 cards
        InvalidPlacementException is thrown when the placement logic is not respected
         */
        targetSlot.placeCard(cardToBePlaced);
    }
}
