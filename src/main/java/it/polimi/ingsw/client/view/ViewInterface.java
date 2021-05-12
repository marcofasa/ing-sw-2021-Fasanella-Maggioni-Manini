package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public interface ViewInterface {
    //Methods display..() then override in CLI and GUI

    /*
    Display Welcome Message
     */
    void displayWelcome();

    /*
    Display Match Starting
     */
    void displayStartingGame();

    /*
    Display Message
     */
    void displayMessage(String message);

    /*
    Display Login
     */
    void displayLogin();

    /*
    Display Position in FaithTrail
     */
    void displayPosition();

    void displayTimeOut();

    /*
    Display Market
     */
    void displayMarket();

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

    //Various asks

    /*
    Ask for NickName
     */
    void askNickName();

    /*
    Ask for number of Players
     */
    void askPlayerNumber();

    /*
    Ask Resource to discard
     */
    void askResourceToDiscard(HashMap<Resource,Integer> choice);

     /*
     Ask to choose Market row or column
     */
     void askMarketChoice();

     void askDevelopmentCardChoice();

     void askProductionActivation();

     void askCardLeaderActivation();

     void askEndTurn();

     ArrayList<Marble> askForResourceSelection(ArrayList<Marble> marbles);

     ArrayList<Resource> askForInitialResourcesSelection(int playerNumber);

     ArrayList<CardLeader> askLeaderCardSelection(ArrayList<CardLeader> cardLeaders);

}
