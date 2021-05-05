package it.polimi.ingsw.client.view;

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

    /*
    Display Market
     */
    void displayMarket();

    /*
    Display StrongBox
     */
    void displayStrongBox();


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

    //Vari ask.

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
    void askResourceToDiscard(HashMap<Resource,Integer> choice,int leftResource);

     /*
     Ask to choose Market row or column
     */
     void askMarketChoice();


}
