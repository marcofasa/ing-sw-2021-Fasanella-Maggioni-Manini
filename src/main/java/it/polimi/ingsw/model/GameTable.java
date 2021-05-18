package it.polimi.ingsw.model;

import jdk.jshell.spi.ExecutionControl;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

public class GameTable implements Serializable {

    private ArrayList<PlayerBoard> players;
    private Lorenzo lorenzo;
    private FaithTrail faithTrail;
    private CardLeaderDeck cardLeaderDeck;
    private Market market;
    private CardDevelopmentMarket cardDevelopmentMarket;
    private int numberOfPlayers;
    private boolean isFirstRound;
    private final boolean isSinglePlayer;
    private boolean gameHasStarted;

    //Constructor

    public GameTable(Boolean isSinglePlayer){
        isFirstRound = true;
        numberOfPlayers = 0;
        players = new ArrayList<>();
        this.isSinglePlayer = isSinglePlayer;
        cardLeaderDeck = new CardLeaderDeck(this);
        gameHasStarted = false;
    }

    /**
     * Constructor that creates player, cardLeaderDeck and distributes them
     *
     * @deprecated use {@link #GameTable(Boolean)} instead and add players via {@link #addPlayer(String)} then call {@link #startGame()}.
     * @param nicknames ArrayList of nicknames chosen by the client(s)
     */
    @Deprecated
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
        gameHasStarted = true;

    }

    /**
     * Add player to the game. Do not use this method to resume a player connection
     * @param nickname String of the new player's nickname
     */
    public void addPlayer(String nickname){

        if (gameHasStarted) throw new RuntimeException("Cannot add players while game is running");
        if (players.size() == 4) throw new GameIsFullException();

        if (numberOfPlayers == 0) {
            players.add(new PlayerBoard(nickname, true, PlayerState.PLAYING, this));
        }
        else {
            players.add(new PlayerBoard(nickname, false, PlayerState.IDLE, this));
        }
        numberOfPlayers++;
    }

    /**
     * Starts a game with the current players in it
     */
    public void startGame(){
        distributeLeaderCards();
        gameHasStarted = true;
    }

    /* Getters */

    //Singletons

    public CardDevelopmentMarket getCardDevelopmentMarketInstance() {
        if (cardDevelopmentMarket == null)
            cardDevelopmentMarket = new CardDevelopmentMarket();
        return cardDevelopmentMarket;
    }

    public Lorenzo getLorenzoInstance() {
        if (lorenzo == null) {
            lorenzo = new Lorenzo(this);
        }
        return lorenzo;
    }

    public FaithTrail getFaithTrailInstance() {
        if (faithTrail == null)
            if (isSinglePlayer) {
                faithTrail = new FaithTrail(this, players, getLorenzoInstance());
            } else faithTrail = new FaithTrail(this, players);
        return faithTrail;
    }

    public Market getMarketInstance() {
        if (market == null) {
            market = new Market(this);
        }
        return market;
    }

    /**
     *
     * @return player with PlayerState Playing
     */
    public PlayerBoard getActivePlayer(){
        for (int i = 0; i < numberOfPlayers; i++) {
            if (players.get(i).getPlayerState()==PlayerState.PLAYING) return players.get(i);
        }
        //This return should never be reached
        return null;
    }

    //Questi metodi andrebbero rinominati a dwarCardLeader imo, non sono veri e propri getter -Lucas
    public CardLeader getCardLeader(PlayerBoard playerBoard) {
        return cardLeaderDeck.getCardLeader(playerBoard);
    }

    //State getters

    public boolean isFirstRound() {
        return isFirstRound;
    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public ArrayList<PlayerBoard> getPlayerBoards() {
        return players;
        // return new ArrayList<>(players);
    }

    public PlayerBoard getPlayerByIndex(int i) {
        return players.get(i);
    }

    public int getIndexFromPlayer(PlayerBoard _player) {

        int output = 0;

        for (PlayerBoard player : players) {

            if (player.equals(_player)) break;
            output++;
        }

        return output;

    }

    /**
     * If player is the last of players list then returns from the first
     *
     * @param currPlayer Active player
     * @return next player in the turn queue
     */
    public PlayerBoard getNextPlayer(PlayerBoard currPlayer) {
        int i = players.indexOf(currPlayer);
        if (i < numberOfPlayers - 1) {
            return players.get(i + 1); //next Player
        } else
            return players.get(0); //first Player
    }

    /* Setters */

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /* Class methods */

    /**
     * Discard development card from PlayerBoard after a discardAction was drawn from Lorenzo.
     * This method always discards 2 cards of discardType
     *
     * @param discardType type of development card
     */
    void discardDevelopmentCardFromSlot(CardDevelopmentType discardType) {

        cardDevelopmentMarket.discardCards(discardType);

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

        /*
        Appunto di Marco: ci sono due tipi di endgame:
        -- Endgame immediato (solo in single player con Lorenzo)
             (getposition(Lorenzo)== 24  OR Colonna di Carte sviluppo di una certa tipologia è vuota) --> PERSO
             (getposition(SinglePlayer)== 24 OR Settima carta Sviluppo comprata) --> VINTO
        -- Endgame normale
            getposition(Player)== 24
            Settima carta Sviluppo comprata
        In entrambi i casi andrebbe controllato se isSinglePlayer è true e terminare o fare l'ultimo giro.
         */

        try {
            throw new ExecutionControl.NotImplementedException("Activate Endgame has not been implemented yet");
        } catch (ExecutionControl.NotImplementedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls movePlayer() method in FaithTrail
     *
     * @param player to be moved
     * @param steps number of steps player will be moved forward
     */
    public void moveFaithTrail(PlayerBoard player, int steps) {
        getFaithTrailInstance().movePlayer(player, steps);
    }

    public void moveFaithTrailLorenzo() {
        getFaithTrailInstance().moveLorenzo();
    }

    public void moveOthersFaithTrail(PlayerBoard notMovingPlayer) {
        for (PlayerBoard board : players) {
            if (!board.getNickname().equals(notMovingPlayer.getNickname())) getFaithTrailInstance().movePlayer(board, 1);
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

        if (bonus1 == null && playerIndex > 0) {
            throw new IllegalArgumentException("bonus1 must be selected for this player: " + getPlayerByIndex(playerIndex));
        }
        if (bonus2 == null && playerIndex > 2) {
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
                player.addCardLeaderBeforeSelecting(cardLeaderDeck.getCardLeader(player));
        }

    }

    public void endFirstRound() {
        isFirstRound = false;
    }

    public PlayerBoard getPlayerByNickname(String nickname) {

        for (PlayerBoard player : players) {

            if (player.getNickname().equals(nickname)) return player;
        }

        //This return should never be reached!
        throw new IllegalArgumentException("No player has that nickname");
    }
}
