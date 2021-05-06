package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.server.*;
import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Marble;

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
        client.send(new PlayersNumber(Integer.toString(client.askPlayersNumber())));
    }

    public void nicknameIsUnavailable(){ /* TODO */
        System.out.println("Nickname is unavailable");
    }

    public void gameHasStarted(int gameID) {
        System.out.println("Game Has Started. Game ID: " + gameID);
    }

    public void leaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        client.getView().askLeaderCardSelection(cardLeaders);
    }

    public void initialResourcesSelection(int playerNumber) {
        client.getView().askForInitialResourcesSelection();
    }

    public void discardResourceSelection(ArrayList<Marble> marbles) {
        client.getView().askForResourceSelection(marbles);
    }

    public void notActivePlayerError() {
        client.getView().notifyNotActivePlayerError();
    }

    public void notEnoughResource() {
        client.getView().notifyNotEnoughResource();
    }

    public void success() {
        client.getView().notifySuccess();
    }

    public void leaderRequirementsNotMet() {
        client.getView().notifyLeaderRequirementsNotMet();
    }
}

//
