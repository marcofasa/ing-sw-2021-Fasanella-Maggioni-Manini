package it.polimi.ingsw.client.view;
import it.polimi.ingsw.client.ConnectionInfo;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.ActionCardEnum;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public interface ViewInterface {

    LightModel getLightModel();

    LightFaithTrail getLightFaithTrail();

    //DISPLAY

    /*
    Display Match Starting
     */
    void displayStartingGame();


    /*
    Display Position in FaithTrail
     */
    void displayPosition();


    void displayTimeOut();

    /*
    Display Resource Market
     */
    void displayResourceMarket();

    /*
    Display StrongBox
     */
    void displayStrongBox();

    /*
    Display that the Player is not active
     */
    void displayNotActivePlayerError();

    /*
    Display that there are not enough resources to continue action
     */
    void displayNotEnoughResource();

    /*
    Display Disconnection
     */
    void displayDisconnection();

    void displayConnection();
    /*
    Display Win Message
     */
    void displayWin();

    /*
    Display Lost Message
     */
    void displayLost();

    /*
    Display successful action
     */
    void displaySuccess();

    /*
    Display requirements not met
     */
    void displayLeaderRequirementsNotMet();

    /*
    Display
     */
    void displayTurn(String currentPlayer, GamePhase gamePhase);

    void displayWaitingOpponent(String currentPlayer);

    /*

     */
    void displayDeposit();

    /*
    Display Card
     */
    void displayCardLeader();

    /*
    Display Card Development Deck
     */
    void displayCardDevelopment();

    //ASK

    //REQUEST

    int askPlayerNumber();

    void askMarketChoice();

    void askDevelopmentCardChoice();

    void askProductionActivation();

    void askCardLeaderActivation();

    void askEndTurn();

    //RESPONSE

    ArrayList<Resource> askForInitialResourcesSelection(int playerNumber);

    ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders);

    HashMap<Resource,Integer> askForResourceToDiscard(HashMap<Resource,Integer> choice);

    void displayWaiting(int timeoutInSeconds);

    void askCardLeaderDiscard();

    void displayCardDevelopmentMarket();

    void displayStartingEndGame(String payload);

    void displayScoreBoard(HashMap<String, Integer> showScoreBoard);

    void displayMainMoveAlreadyMade();

    void displayConnectionError();

    void displayTimeoutError();

    void displayLorenzoActivation(ActionCardEnum actionCardType);

    void displayInvalidPlacementSelection();

    ConnectionInfo getConnectionInfo();

    void displayNickNameUnavailable();

    void displayServerUnreachable();

    void gameHasStarted();

    void displayClientAccepted();

    void notifyDisconnectionOf(String nickname);

    void notifyReconnection(String nickname);

    void displayMainMoveNotMade();

    void unexpectedMove();
}
