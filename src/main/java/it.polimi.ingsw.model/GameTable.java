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

    public GameTable(ArrayList<String> nicknames){
        numberOfPlayers=nicknames.size();
        players=new ArrayList<PlayerBoard>();

        for (int i=0;i<numberOfPlayers;i++){
            if (i==0) players.add(new PlayerBoard(nicknames.get(i),true,PlayerState.PLAYING));
            else players.add(new PlayerBoard(nicknames.get(i),false,PlayerState.IDLE));
        }

        if (numberOfPlayers==1){
            isSinglePlayer=true;
        }
        else {
            isSinglePlayer=false;

        }

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
     * discard development card from PlayerBoard
     *
     * @param discardType type of development card
     * @param i           number of dev. cards to discard
     */
    private void discardDevelopmentCardFromSlot(CardDevelopmentType discardType, int i) {
        try {
            throw new ExecutionControl.NotImplementedException("discardDevCard has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
        //THIS FUNCTION SHOULD ACTIVATE ENDGAME
    }

    public void activateEndGame(PlayerBoard p) {
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

    private void setupHelper(int playerIndex,Resource bonus1,Resource bonus2){

    }

    private void distributeLeaderCards(PlayerBoard board){

    }

}
