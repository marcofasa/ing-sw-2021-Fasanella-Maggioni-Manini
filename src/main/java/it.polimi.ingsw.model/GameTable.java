package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.Nullable;

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

    /**
     * Constructor that creates player, cardLeaderDeck and distributes them
     * @param nicknames
     */
    public GameTable(ArrayList<String> nicknames) {
        numberOfPlayers = nicknames.size();
        players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == 0) players.add(new PlayerBoard(nicknames.get(i), true, PlayerState.PLAYING, this));
            else players.add(new PlayerBoard(nicknames.get(i), false, PlayerState.IDLE, this));
        }

        isSinglePlayer = (numberOfPlayers == 1);

        cardLeaderDeck = new CardLeaderDeck(this);

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
        if(cardDevelopmentMarket == null)
            cardDevelopmentMarket = new CardDevelopmentMarket();
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

    public PlayerBoard getPlayerByIndex(int i) {
        return players.get(i);
    }

    /**
     * If player is the last of players list then returns from the first
     * @param currPlayer
     * @return next Player
     */
    public PlayerBoard getNextPlayer(PlayerBoard currPlayer) {
        int i = players.indexOf(currPlayer);
        if (i < numberOfPlayers - 1) {
            return players.get(i + 1); //next Player
        } else return players.get(0); //first Player
    }

    public FaithTrail getFaithTrailInstance() {
        if (faithTrail == null)
            faithTrail = new FaithTrail(this, players);
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
    void discardDevelopmentCardFromSlot(CardDevelopmentType discardType) {

        cardDevelopmentMarket.discardCards(discardType);

        //THIS FUNCTION SHOULD ACTIVATE ENDGAME
        if (cardDevelopmentMarket.isColumnEmpty(discardType)) activateEndGame();
    }


    /**
     * This method should only notify the controller that an EndGame condition has been met.
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

    /**
     * Calls movePlayer() method in FaithTrail
     * @param player to be moved
     * @param steps
     */
    public void moveFaithTrail(PlayerBoard player, int steps) {
        faithTrail.movePlayer(player, steps);
    }

    public void moveOthersFaithTrail(PlayerBoard notMovingPlayer) {
        ListIterator<PlayerBoard> iterator = players.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(notMovingPlayer)) continue;
            else faithTrail.movePlayer(iterator.next(), 1);
        }
    }

    /**
     * THIS METHOD MUST BE CALLED INSIDE THE CONTROLLER, AFTER RECEIVING PLAYER RESOURCE SELECTIONS
     * <p>
     * Method to place initial resources and initial advances for players except for the first one.
     *
     * @param playerIndex An int the player's position within the game turn order.
     * @param bonus1      First bonus resource selected by the player, can be null for playerIndex 0 or 1
     * @param bonus2      Second bonus resource selected by the player, can be null for playerIndex 0 or 1 or 2
     */
    public void setupHelper(int playerIndex, //TODO non è meglio shiftare da 0->3 a 1->4?
                            @Nullable Resource bonus1,
                            @Nullable Resource bonus2) {

        if(bonus1 == null && playerIndex > 0){
            throw new IllegalArgumentException("bonus1 must be selected for this player: " + getPlayerByIndex(playerIndex));
        }
        if(bonus2 == null && playerIndex > 2){
            throw new IllegalArgumentException("bonus2 must be selected for this player: " + getPlayerByIndex(playerIndex));
        }

        switch (playerIndex) {

            case 1:
                players.get(playerIndex).getDepositInstance().addResource(bonus1, 1);
                break;
            case 2:
                players.get(playerIndex).getDepositInstance().addResource(bonus1, 1);
                moveFaithTrail(players.get(playerIndex), 1);
                break;
            case 3:
                players.get(playerIndex).getDepositInstance().addResource(bonus1, 1);
                players.get(playerIndex).getDepositInstance().addResource(bonus2, 1);
                moveFaithTrail(players.get(playerIndex), 1);
                break;
            // Not applicable for first player -> 0 index
            default:
                break;

        }
    }

    private void distributeLeaderCards() {

        for (PlayerBoard player : players) {
            for (int i = 0; i < 4; i++)
                player.getCardsLeaderBeforeSelecting().add(cardLeaderDeck.getCardLeader(player));
        }

    }

}
