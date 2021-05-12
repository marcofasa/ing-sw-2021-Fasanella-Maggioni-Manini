package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class GUI implements ViewInterface {
  //implements ViewInterface
    //TODO

    private Client client;

    @Override
    public void displayWelcome() {

    }

    @Override
    public void displayStartingGame() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void displayLogin() {

    }

    @Override
    public void displayPosition() {

    }

    @Override
    public void displayTimeOut() {

    }

    @Override
    public void displayMarket() {

    }

    @Override
    public void displayStrongBox() {

    }

    @Override
    public void displayNotActivePlayerError() {

    }

    @Override
    public void displayNotEnoughResource() {

    }

    @Override
    public void displayDisconnection() {

    }

    @Override
    public void displayWin() {

    }

    @Override
    public void displayLost() {

    }

    @Override
    public void displaySuccess() {

    }

    @Override
    public void displayLeaderRequirementsNotMet() {

    }

    @Override
    public String askNickName() {
        return null;
    }

    @Override
    public int askPlayerNumber() {
        return 0;
    }

    @Override
    public void askResourceToDiscard(HashMap<Resource, Integer> choice) {

    }

    @Override
    public void askMarketChoice() {

    }

    @Override
    public void askDevelopmentCardChoice() {

    }

    @Override
    public void askProductionActivation() {

    }

    @Override
    public void askCardLeaderActivation() {

    }


    @Override
    public ArrayList<Marble> askForResourceSelection(ArrayList<Marble> marbles) {

        return marbles;
    }

    @Override
    public ArrayList<Resource> askForInitialResourcesSelection(int playerNumber) {
        return null;
    }


    @Override
    public void askEndTurn() {

    }

    @Override
    public ArrayList<CardLeader> askLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        return null;
    }
}
