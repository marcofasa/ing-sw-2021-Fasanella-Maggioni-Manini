package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class ClientCommandDispatcher {

    private Client client;

    public ClientCommandDispatcher(Client client){
        this.client = client;
    }

    public void clientAccepted() {
        System.out.println("Connected to server");
    }

    public void requestPlayersNumber(int timeoutID) {
        System.out.println("Request Players Number received");
        sendWithTimeoutID(new ResponsePlayersNumber(Integer.toString(client.getView().askPlayerNumber())), timeoutID);
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
        ArrayList<Resource> resources = client.getView().askForInitialResourcesSelection(playerNumber);
        return resources;
    }

    public void requestDiscardResourceSelection(ArrayList<Marble> marbles, int timeoutID) {
        //HashMap<Resource,Integer> marbles1= client.getView().askForResourceToDiscard(marbles);
        ArrayList<Marble> marbles1 = client.getView().askForResourceSelection(marbles);
        sendWithTimeoutID(new ResponseDiscardResourceSelection(marbles1), timeoutID);
    }

    public void notActivePlayerError() {
        client.getView().displayNotActivePlayerError();
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

    public TimeoutHandler getTimeoutHandler() {
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
}

//
