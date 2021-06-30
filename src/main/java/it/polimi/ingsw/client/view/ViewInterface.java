package it.polimi.ingsw.client.view;
import it.polimi.ingsw.client.ConnectionInfo;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.enums.ActionCardEnum;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.enums.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public interface ViewInterface {

    /**
     * LightModel getter
     * @return instance of player's LightModel
     */
    LightModel getLightModel();

    LightFaithTrail getLightFaithTrail();

    //DISPLAY

    /**
     * Display starting game message
     */
    void displayStartingGame();


    /**
     * Display Position in FaithTrail
     */
    void displayPosition();


    /**
     * Display timeout message
     */
    void displayTimeOut();

    /**
     * Display Resource Market
     */
    void displayResourceMarket();

    /**
     * Display StrongBox
     */
    void displayStrongBox();

    /**
     * Display that the Player is not active
     */
    void displayNotActivePlayerError();

    /**
     * Display that there are not enough resources to continue action
     */
    void displayNotEnoughResource();

    /**
     * Display disconnection message
     */
    void displayDisconnection();

    /**
     * Display connection message
     */
    void displayConnection();

    /**
     * Display victory message
     */
    void displayWin();

    /**
     * Display loss message
     */
    void displayLost();

    /**
     * Display successful action message
     */
    void displaySuccess();

    /**
     * Display requirements not met message
     */
    void displayLeaderRequirementsNotMet();

    /**
     * Display new turn message
     */
    void displayTurn(String currentPlayer, GamePhase gamePhase);

    /**
     * Display waiting for opponent message
     * @param currentPlayer nickname of the player that's currently playing, all other must wait for his turn to end
     */
    void displayWaitingOpponent(String currentPlayer);

    /**
     * Display player's deposit
     */
    void displayDeposit();

    /**
     * Display player's leader cards
     */
    void displayCardLeader();

    /**
     * Display player's top development cards
     */
    void displayCardDevelopment();

    //REQUEST

    /**
     * Request lobby size
     * @return selected size for the game lobby
     */
    int askPlayerNumber();

    /**
     * Request selection of Resource market row/column
     */
    void askMarketChoice();

    /**
     * Request selection of development card to be bought from Card Development Market
     */
    void askDevelopmentCardChoice();

    /**
     * Request selection of production powers to be activated
     */
    void askProductionActivation();

    /**
     * Request selection of a leader card to be activated
     */
    void askCardLeaderActivation();

    /**
     * Signal ending of turn
     */
    void askEndTurn();

    //RESPONSE

    /**
     * Request selection of initial bonus resources
     * @param playerNumber the player's index within the turn, an int from 1 to the lobby size included
     * @return ArrayList of correctly selected resources
     */
    ArrayList<Resource> askForInitialResourcesSelection(int playerNumber);

    /**
     * Ask to select two cards leader at the beginning of the game.
     *
     * @param cardLeaders deck of leader cards from which the player can choose.
     * @return ArrayList of selected leader cards.
     */
    ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders);

    /**
     * Let player select which resources to discard
     *
     * @param choice HashMap of available resources
     * @return HashMap of selected resources
     */
    HashMap<Resource,Integer> askForResourceToDiscard(HashMap<Resource,Integer> choice);

    /**
     * Display waiting message.
     * @param timeoutInSeconds timeout
     */
    void displayWaiting(int timeoutInSeconds);

    /**
     * Request selection for card leader to be discarded
     */
    void askCardLeaderDiscard();

    /**
     * Display card development market
     */
    void displayCardDevelopmentMarket();

    /**
     * Display endgame activation message
     * @param payload message payload
     */
    void displayStartingEndGame(String payload);

    /**
     * Display final scoreboard
     * @param showScoreBoard HashMap of nickname -> total victory points.
     */
    void displayScoreBoard(HashMap<String, Integer> showScoreBoard);

    /**
     * Display main move already made message.
     */
    void displayMainMoveAlreadyMade();

    /**
     * Display connection error message.
     */
    void displayConnectionError();

    /**
     * Display timeout error
     */
    void displayTimeoutError();

    /**
     * Display Lorenzo move description message
     * @param actionCardType The type of Lorenzo's move
     */
    void displayLorenzoActivation(ActionCardEnum actionCardType);

    /**
     * Display invalid placement selection message.
     * Applies when player is trying to place a development card in an illegal slot.
     */
    void displayInvalidPlacementSelection();

    ConnectionInfo getConnectionInfo();

    /**
     * Display nickname unavailable message
     */
    void displayNickNameUnavailable();

    /**
     * Display server unreachable message
     */
    void displayServerUnreachable();

    /**
     * Display game has started message
     */
    void gameHasStarted();

    /**
     * Display connection accepted message
     */
    void displayClientAccepted();

    /**
     * Display player disconnection message.
     * @param nickname nickname of the disconnected player
     */
    void notifyDisconnectionOf(String nickname);

    /**
     * Display player reconnection message.
     * @param nickname nickname of the reconnected player
     */
    void notifyReconnection(String nickname);

    /**
     * Display main move not made message
     */
    void displayMainMoveNotMade();

    /**
     * Display unexpected move message.
     */
    void unexpectedMove();
}
