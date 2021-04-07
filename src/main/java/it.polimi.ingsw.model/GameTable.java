package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameTable {

    private ArrayList<PlayerBoard> players;
    private Lorenzo lorenzo;
    private FaithTrail faithTrail;
    private CardLeaderDeck cardLeaderDeck;
    private Market market;
    private CardDevelopmentMarket cardDevelopmentMarket;
    private int numberOfPlayers;
    private boolean isFirstRound;
    private boolean isSinglePlayer;

    //Constructor

    public GameTable(ArrayList<String> nicknames){
        numberOfPlayers=nicknames.size();
        players=new ArrayList<PlayerBoard>();

        for (int i=0;i<numberOfPlayers;i++){
            if (i==0) players.add(new PlayerBoard(nicknames.get(i),true,PlayerState.PLAYING));
            else players.add(new PlayerBoard(nicknames.get(i),false,PlayerState.IDLE));
        }

        isSinglePlayer = (numberOfPlayers == 1);

        /*
         The following statements could not be necessary if we should chose to use more Singleton patterns, yet
         they serve as a defensive line to assure we do not run into NullPointerExceptions
         */
        faithTrail = new FaithTrail(this, players);
        cardLeaderDeck = new CardLeaderDeck(this);
        market = getMarketInstance();
        cardDevelopmentMarket = new CardDevelopmentMarket();

        //setupHelper() non puo' essere chiamato qui, va chiamato nel controller dopo aver ricevuto le risorse scelte dai giocatori

        distributeLeaderCards();

    }



    public CardLeader getCardLeader(PlayerBoard playerBoard) {
        return cardLeaderDeck.getCardLeader(playerBoard);
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public CardDevelopmentMarket getCardDevelopmentMarketInstance() {
        return cardDevelopmentMarket;
    }


    public Lorenzo getLorenzoInstance() {
        if (lorenzo == null) {
            lorenzo = new Lorenzo(this);
        }
        return lorenzo;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return new ArrayList<>(players);
    }

    public PlayerBoard getPlayerByIndex(int i){
        return players.get(i);
    }

    public PlayerBoard getNextPlayer(PlayerBoard currPlayer){
        int i= players.indexOf(currPlayer);
        if(i<numberOfPlayers-1){
            return  players.get(i+1); //next Player
        }
        else return players.get(0); //first Player
    }

    public FaithTrail getFaithTrail() {
        return faithTrail;
    }

    public Market getMarketInstance() {
        if (market == null) {
            market = new Market(this);
        }
        return market;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * discard development card from PlayerBoard after a discardAction was drawn from Lorenzo.
     * This method always discards 2 cards of discardType
     *
     * @param discardType type of development card
     */
    private void discardDevelopmentCardFromSlot(CardDevelopmentType discardType) {

        cardDevelopmentMarket.discardCards(discardType);

        //THIS FUNCTION SHOULD ACTIVATE ENDGAME
        if (cardDevelopmentMarket.isColumnEmpty(discardType)) activateEndGame();
    }


    /**
     * This method should only notify the controller that an EndGame condition has been met.
     *
     *
     */
    public void activateEndGame() {

        /*
        Appunto di Lucas : Secondo me questo metodo non ha bisogno di prendere un player come parametro. Solo
        modifichera` lo stato di un suo attributo interno e ci chiamera .update(), notificando al Controller
        che inizia l'utimo giro di turni.
         */

        try {
            throw new ExecutionControl.NotImplementedException("Activate Endgame has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    public void moveFaithTrail(PlayerBoard player, int steps){
        faithTrail.movePlayer(player,steps);
    }

    public void moveOthersFaithTrail(PlayerBoard notMovingPlayer){
        ListIterator<PlayerBoard> iterator = players.listIterator();
        while(iterator.hasNext()){
            if (iterator.next().equals(notMovingPlayer)) continue;
            else faithTrail.movePlayer(iterator.next(),1);
        }
    }

    /**
     * THIS METHOD MUST BE CALLED BY THE CONTROLLER, AFTER RECEIVING PLAYER RESOURCE SELECTIONS
     *
     * Method to place initial resources and initial advances for players except for the first one.
     * @param playerIndex An int the player's position within the game turn order.
     * @param bonus1 First bonus resource selected by the player
     * @param bonus2 Second bonus resource selected by the player
     */
    public void setupHelper(int playerIndex,Resource bonus1,Resource bonus2){

        switch (playerIndex) {

            case 1:
                players.get(playerIndex).getDeposit().addResource(bonus1, 1);
                break;
            case 2:
                players.get(playerIndex).getDeposit().addResource(bonus1, 1);
                moveFaithTrail(players.get(playerIndex), 1);
                break;
            case 3:
                players.get(playerIndex).getDeposit().addResource(bonus1, 1);
                players.get(playerIndex).getDeposit().addResource(bonus2, 1);
                moveFaithTrail(players.get(playerIndex), 1);
                break;
            // Not applicable for first player -> 0 index
            default:
                break;

        }
    }

    private void distributeLeaderCards(){

        for (PlayerBoard player : players) {
            for (int i = 0; i < 4; i++) player.getCardsLeaderBeforeSelecting().add(cardLeaderDeck.getCardLeader(player));
        }

    }

}
