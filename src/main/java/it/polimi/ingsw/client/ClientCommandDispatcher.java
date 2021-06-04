package it.polimi.ingsw.client;

import it.polimi.ingsw.communication.timeout_handler.ClientTimeoutHandler;
import it.polimi.ingsw.communication.client.*;
import it.polimi.ingsw.communication.client.requests.RequestAddResourceSelection;
import it.polimi.ingsw.communication.client.responses.ResponseInitialSelection;
import it.polimi.ingsw.communication.client.responses.ResponsePlayersNumber;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.*;

import java.io.IOException;
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
        client.getView().displayNickNameUnavailable();
        //System.out.println("Nickname is unavailable! Please try another one...");

        client.setConnected(false);
        client.closeStream();
    }

    public void gameHasStarted(int gameID, ArrayList<String> playersNickname) {
        System.out.println("Game Has Started. Game ID: " + gameID);
        client.setPlayersNicknames(playersNickname);
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

    public void displayTurn(String nickname, GamePhase gamePhase) {
        client.getView().displayTurn(nickname, gamePhase);
    }

    public void notEnoughResource() {
        client.getView().displayNotEnoughResource();
    }

    public void success(GamePhase gamePhase) {
        System.out.println("Successo attivato con Gamephase " + gamePhase.toString());
        client.getView().displaySuccess();
        if(gamePhase == GamePhase.Initial || gamePhase == GamePhase.Final)
            client.getView().displayTurn(client.getNickname(), gamePhase);
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
        System.out.println("A game logic error was encountered, the move has been reverted.");
    }

    public void setDeposit(HashMap<Resource, Integer> depositClone, ArrayList<Resource> leaderRes, HashMap<Resource, Integer> leaderContent) {
        client.getView().getLightModel().setDeposit(depositClone);
        client.getView().getLightModel().setDepositLeaderResources(leaderRes);
        client.getView().getLightModel().setDepositLeaderContent(leaderContent);
    }

    public void setStrongbox(HashMap<Resource, Integer> strongboxClone) {
        client.getView().getLightModel().setStrongbox(strongboxClone);
    }

    public void setMarketInstance(ArrayList<ArrayList<MarbleType>> marketClone, Marble spareMarble) {
        client.getView().getLightModel().setMarket(marketClone); /* TODO spare marble */
        client.getView().getLightModel().setSpareMarble(spareMarble);
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

    public void startingEndGame(String payload) {
        client.getView().displayStartingEndGame(payload);
    }

    public void showScoreBoard(HashMap<String, Integer> showScoreBoard) {
        client.getView().displayScoreBoard(showScoreBoard);
    }

    public void mainMoveAlreadyMade() { client.getView().displayMainMoveAlreadyMade();
    }

    public void notifyBriefModel(BriefModel briefModel, String nickname) {
        client.setModelForPlayer(briefModel, nickname);
    }

    public void displayLorenzoActivation(ActionCardEnum actionCardType) {
        client.getView().displayLorenzoActivation(actionCardType);
    }

    public void displayInvalidPlacementSelection() {
        client.getView().displayInvalidPlacementSelection();
    }

    public void displaySinglePlayerOutcome(boolean hasWon, Integer points) {

        if (hasWon) {

            HashMap<String, Integer> scoreMap = new HashMap<>();
            scoreMap.put(client.getNickname(), points);

            client.getView().displayScoreBoard(scoreMap);
        } else {

            System.out.println("Lorenzo has reached a win condition...\n");
            client.getView().displayLost();
        }

    }
}
