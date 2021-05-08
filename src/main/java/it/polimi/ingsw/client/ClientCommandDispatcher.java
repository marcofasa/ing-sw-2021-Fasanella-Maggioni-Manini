package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.Resource;

import java.util.ArrayList;

public class ClientCommandDispatcher {

    private Client client;

    public ClientCommandDispatcher(Client client){
        this.client = client;
    }

    public void clientAccepted() {
        System.out.println("Connected to server");
    }

    public void requestPlayersNumber() {
        System.out.println("Request Players Number received");
        client.send(new ResponsePlayersNumber(Integer.toString(client.askPlayersNumber())));
    }

    public void nicknameIsUnavailable(){ /* TODO */
        System.out.println("Nickname is unavailable");
    }

    public void gameHasStarted(int gameID) {
        System.out.println("Game Has Started. Game ID: " + gameID);
    }

    public ArrayList<CardLeader> requestLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        ArrayList<CardLeader> cardLeaders1 =  client.getView().askLeaderCardSelection(cardLeaders);
        return cardLeaders1;
    }

    public ArrayList<Resource> requestInitialResourcesSelection(int playerNumber) {
        ArrayList<Resource> resources = client.getView().askForInitialResourcesSelection();
        return resources;
    }

    public void requestDiscardResourceSelection(ArrayList<Marble> marbles) {
        client.getView().askForResourceSelection(marbles);
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

    public void requestInitialSelection(ArrayList<CardLeader> cardLeaders, int playerNumber) {
        ArrayList<CardLeader> cardLeaders1 = requestLeaderCardSelection(cardLeaders);
        ArrayList<Resource> resources = requestInitialResourcesSelection(playerNumber);
        client.send(new ResponseInitialSelection(resources.get(0), resources.get(1), cardLeaders1));
    }
}

//
