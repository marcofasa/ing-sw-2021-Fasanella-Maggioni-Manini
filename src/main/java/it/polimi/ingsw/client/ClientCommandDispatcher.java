package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.ClientTimeoutHandler;
import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.communication.client.requests.RequestAddResourceSelection;
import it.polimi.ingsw.communication.client.responses.ResponseDiscardResourceSelection;
import it.polimi.ingsw.communication.client.responses.ResponseInitialSelection;
import it.polimi.ingsw.communication.client.responses.ResponsePlayersNumber;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientCommandDispatcher {

    private final Client client;

    public ClientCommandDispatcher(Client client){
        this.client = client;
    }

    public void clientAccepted() {
        System.out.println("Connected to server");
    }

    public void requestPlayersNumber(int timeoutID) {
        System.out.println("Request Players Number received");
        sendWithTimeoutID(new ResponsePlayersNumber(client.getView().askPlayerNumber()), timeoutID);
    }

    public void nicknameIsUnavailable(){ /* TODO */
        System.out.println("Nickname is unavailable");
    }

    public void gameHasStarted(int gameID) {
        System.out.println("Game Has Started. Game ID: " + gameID);
    }

    private ArrayList<CardLeader> subRequestLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        ArrayList<CardLeader> cardLeaders1 =  client.getView().askForLeaderCardSelection(cardLeaders);
        return cardLeaders1;
    }

    private ArrayList<Resource> subRequestInitialResourcesSelection(int playerNumber) {
        return client.getView().askForInitialResourcesSelection(playerNumber);
    }

    public void discardResourceSelection(HashMap<Resource, Integer> resources) {
        HashMap<Resource,Integer> resources1= client.getView().askForResourceToDiscard(resources);

        client.send(new RequestAddResourceSelection(resources1));
    }

    public void notActivePlayerError() {
        client.getView().displayNotActivePlayerError();
    }

    public void displayTurn(String nickname) {
        client.getView().displayTurn(nickname);
    }

    public void notEnoughResource() {
        client.getView().displayNotEnoughResource();
    }

    public void success() {
        client.getView().displaySuccess();
    }

    public void leaderRequirementsNotMet() {
        client.getView().displayLeaderRequirementsNotMet();
    }

    public ClientTimeoutHandler getTimeoutHandler() {
        return client.getTimeoutHandler();
    }

    public void requestInitialSelection(ArrayList<CardLeader> cardLeaders, int playerNumber, int timeoutID) {
        ArrayList<CardLeader> cardLeaders1 = subRequestLeaderCardSelection(cardLeaders);
        ArrayList<Resource> resources = subRequestInitialResourcesSelection(playerNumber);
        sendWithTimeoutID(new ResponseInitialSelection(resources.get(0), resources.get(1), cardLeaders1), timeoutID);
    }

    private void sendWithTimeoutID(ClientMessage clientResponse, int timeoutID) {
        clientResponse.setTimeoutID(timeoutID);
        client.send(clientResponse);
    }

    public void unexpectedMove() {
        System.out.println("ClientCommandDispatcher.unexpectedMove : METHOD HOLDS ONLY THIS PRINT, IT IS NOT IMPLEMENTED");
    }

    public void setDeposit(HashMap<Resource, Integer> depositClone) {
        client.getView().getLightModel().setDeposit(depositClone);
    }

    public void setStrongbox(HashMap<Resource, Integer> strongboxClone) {
        client.getView().getLightModel().setStrongbox(strongboxClone);
    }

    public void setMarketInstance(ArrayList<ArrayList<MarbleType>> marketClone) {
        client.getView().getLightModel().setMarket(marketClone);
    }

    public void setCardDevelopmentMarketInstance(ArrayList<ArrayList<CardDevelopment>> _cardMarketClone) {
        client.getView().getLightModel().setCardDevelopmentMarket(_cardMarketClone);
    }

    public void setFaithTrail(HashMap<String, Integer> _playerPositions,ArrayList<FaithTileStatus> tileStatuses ) {
        client.getView().getLightFaithTrail().setFaithTrail(_playerPositions,tileStatuses);
    }

    public void setLeaderCards(ArrayList<CardLeader> leaderCards) {
        client.getView().getLightModel().setCardsLeader(leaderCards);
    }

    public void setTopCardsDevelopment(ArrayList<CardDevelopment> developmentCards) {
        client.getView().getLightModel().setCardsDevelopment(developmentCards);
    }
}
