package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.ActionCardEnum;
import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.CardDevelopmentLevel;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends Application implements ViewInterface {

    private Client client;
    private Stage primaryStage;
    private FXMLLoader fxmlLoader;


    private void mainScene(String fxmlPath) {

            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(fxmlPath));

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            scene = new Scene(new Label("Error during FXML Loading"));
        }

        ((StandardScene) fxmlLoader.getController()).init();
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        //Parent loader = FXMLLoader.load(getClass().getResource("/fxml/LogIn.fxml"));
        this.primaryStage=stage;
        displayCardDevelopmentMarket();

    }

    @Override
    public LightModel getLightModel() {
        return client.getLightModel();
    }

    @Override
    public LightFaithTrail getLightFaithTrail() {
        return null;
    }

    @Override
    public void setClient(Client client) {
        this.client= client;
    }

    @Override
    public void displayWelcome() {
       mainScene("/fxml/LogIn.fxml");
    }

    @Override
    public void displayStartingGame() {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void displayPosition() {

    }

    @Override
    public void displayTimeOut() {

    }

    @Override
    public void displayResourceMarket() {

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
    public void displayTurn(String currentPlayer, GamePhase gamePhase) {

    }

    @Override
    public void displayWaitingOpponent(String currentPlayer) {

    }

    @Override
    public void displayDeposit() {

    }

    @Override
    public void displayCardLeader() {

    }

    @Override
    public void displayCardDevelopment() {

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
    public void askEndTurn() {

    }

    @Override
    public ArrayList<Resource> askForInitialResourcesSelection(int playerNumber) {
        return null;
    }

    @Override
    public ArrayList<CardLeader> askForLeaderCardSelection(ArrayList<CardLeader> cardLeaders) {
        return null;
    }

    @Override
    public HashMap<Resource, Integer> askForResourceToDiscard(HashMap<Resource, Integer> choice) {
        return null;
    }

    @Override
    public void displayWaiting(int timeoutInSeconds) {

    }

    @Override
    public void askCardLeaderDiscard() {

    }

    @Override
    public void displayCardDevelopmentMarket()  {

        //cardDevelop Market Array
        ArrayList<ArrayList<CardDevelopment>> cardDevelopments=new ArrayList<ArrayList<CardDevelopment>>();
        for(int i=0;i<3;i++){
            cardDevelopments.add(new ArrayList<CardDevelopment>());
            for (int j=0;j<4;j++){
                cardDevelopments.get(i).add(new CardDevelopment(i,j,1));
            }
        }





        mainScene("/fxml/CardDevelopmentMarket.fxml");


        CardDevelopmentMarketController cardDevelopmentMarketController= fxmlLoader.getController();

        //REAL LINE
        //cardDevelopmentMarketController.setDevelopmentMarket(client.getLightModel().getCardDevelopmentMarket());

        //TEST LINE
        cardDevelopmentMarketController.setDevelopmentMarket(cardDevelopments);



    }

    @Override
    public void displayStartingEndGame(String payload) {

    }

    @Override
    public void displayScoreBoard(HashMap<String, Integer> showScoreBoard) {

    }

    @Override
    public void displayMainMoveAlreadyMade() {

    }

    @Override
    public void displayConnectionError() {

    }

    @Override
    public void displayTimeoutError() {

    }

    @Override
    public void displayLorenzoActivation(ActionCardEnum actionCardType) {

    }

    @Override
    public void displayInvalidPlacementSelection() {

    }
}
