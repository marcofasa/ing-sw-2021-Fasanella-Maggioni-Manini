package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.requests.RequestEndTurn;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerBoardController extends StandardScene{

    @FXML
    GridPane cardDevelop_grid;

    @FXML
    GridPane resources_grid;


    private LightFaithTrail lightFaithTrail;
    private LightModel lightModel;
    private GamePhase gamePhase;
    private boolean endPhase=false;
    private boolean discardRequest=false;

    public void displayFaithTrail(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/FaithTrail.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(()->{
            FaithTrailController faithTrailController=loader.getController();
            faithTrailController.setFaithTrail(lightFaithTrail.getTileStatuses(),lightFaithTrail.getPlayersPosition());
        });
        FaithTrailController faithTrailController=loader.getController();
        faithTrailController.setFaithTrail(lightFaithTrail.getTileStatuses(),lightFaithTrail.getPlayersPosition());


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);


        newWindow.showAndWait();
    }

    public void displayDeposit(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/Deposit.fxml"));
        Scene secondScene = null;

        try {
            secondScene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DepositController depositController = fxmlLoader.getController();
        depositController.setDeposit(lightModel.getDeposit());

        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        newWindow.showAndWait();
    }

    public void setModels(LightModel lightModel, LightFaithTrail lightFaithTrail, GamePhase gamePhase){
        this.lightFaithTrail=lightFaithTrail;
        this.lightModel=lightModel;
        this.gamePhase=gamePhase;
        if(gamePhase==GamePhase.Final){
            setEndPhase();
        }
        else endPhase=false;
    }

    private void setEndPhase() {
        endPhase=true;
    }

    public void displayCardMarket(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardDevelopmentMarket.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardDevelopmentMarketController cardDevelopmentMarketController=loader.getController();
        cardDevelopmentMarketController.setViewOnly();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(),lightModel.getCardsDevelopment());
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);


        newWindow.showAndWait();
    }


    public void displayResourceMarket(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/ResourceMarket.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResourceMarketController resourceMarketController=loader.getController();
        resourceMarketController.setResourceMarket(lightModel.getMarket());
        resourceMarketController.setViewOnly();


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        newWindow.showAndWait();
    }

    public void buyCard(ActionEvent actionEvent) {
        if(endPhase ){
            printError("action not allowed, primary action already taken");
        }
        else if(discardRequest){
            printError("you have to discard resources");
        }
        else{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardDevelopmentMarket.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardDevelopmentMarketController cardDevelopmentMarketController=loader.getController();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(),lightModel.getCardsDevelopment());
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);


        newWindow.showAndWait();}
    }

    public void buyResource(ActionEvent actionEvent) {
        if(endPhase){
            printError("action not allowed, primary action already taken");
        }
        else if(discardRequest){
            printError("you have to discard resources");
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ResourceMarket.fxml"));
            Scene secondScene = null;
            try {
                secondScene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }

            ResourceMarketController resourceMarketController=loader.getController();
            resourceMarketController.setResourceMarket(lightModel.getMarket());


            // New window (Selection)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            newWindow.showAndWait();}
    }


    public void production(ActionEvent actionEvent) {
        if(discardRequest){
            printError("you have to discard resources");
        }
        else {

        }
    }

    public void endTurn(ActionEvent actionEvent) {
        if(!discardRequest){
            GUI.sendMessage(new RequestEndTurn());
        }
        else printError("you have to discard resources");
    }

    public void setDiscardRequest() {
        discardRequest=true;
    }

    public void discardResources(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/DiscardResource.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        if(endPhase ){
            printError("action not allowed, primary action already taken");
        }
        else if(discardRequest){
            printError("you have to discard resources");
        }
        else{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardLeader.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardLeaderController cardLeaderController=loader.getController();

        cardLeaderController.setCardLeaderDeck(lightModel.getCardsLeader());


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();}
    }
}
