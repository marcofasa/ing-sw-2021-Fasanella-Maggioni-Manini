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
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PlayerBoardController extends StandardStage {

    @FXML
    GridPane cardDevelop_grid;

    @FXML
    GridPane resources_grid;

    @FXML
    DialogPane dialogPane;

    public static DialogPane dialog;
    private LightFaithTrail lightFaithTrail;
    private LightModel lightModel;
    private GamePhase gamePhase;
    private boolean endPhase=false;
    private boolean discardRequest=false;
    public static String messages;

    public void displayFaithTrail(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/FaithTrail.fxml");
        Scene secondScene=setScene(loader);

        Platform.runLater(()->{
            FaithTrailController faithTrailController=loader.getController();
            faithTrailController.setFaithTrail(lightFaithTrail.getTileStatuses(),lightFaithTrail.getPlayersPosition(),lightModel.getNickname());
        });
       // FaithTrailController faithTrailController=loader.getController();
      //  faithTrailController.setFaithTrail(lightFaithTrail.getTileStatuses(),lightFaithTrail.getPlayersPosition(),lightModel.getNickname());

        messages=setDialogPane("Faith Trail displayed",dialogPane,messages);
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();
    }

    public void displayDeposit(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = load("/fxml/Deposit.fxml");
        Scene secondScene = setScene(fxmlLoader);

        DepositController depositController = fxmlLoader.getController();
        depositController.setDeposit(lightModel.getDeposit());

        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        messages=setDialogPane("Deposit displayed",dialogPane,messages);

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
        messages="";
        dialog=dialogPane;
    }

    private void setEndPhase() {
        endPhase=true;
    }

    public void displayCardMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
        Scene secondScene = setScene(loader);

        CardDevelopmentMarketController cardDevelopmentMarketController=loader.getController();
        cardDevelopmentMarketController.setViewOnly();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(),lightModel.getCardsDevelopment());
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        messages=setDialogPane("Card Market displayed",dialogPane,messages);

        newWindow.showAndWait();
    }


    public void displayResourceMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
        Scene secondScene = setScene(loader);

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
            messages=setDialogPane("Action not allowed, primary action already taken!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else if(discardRequest){
            messages= setDialogPane("You have to discard resources!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else{
        FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
        Scene secondScene = setScene(loader);

        CardDevelopmentMarketController cardDevelopmentMarketController=loader.getController();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(),lightModel.getCardsDevelopment());
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);


        newWindow.showAndWait();}
    }

    public void buyResource(ActionEvent actionEvent) {
        if(endPhase){
            messages= setDialogPane("Action not allowed, primary action already taken!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else if(discardRequest){
            messages=setDialogPane("You have to discard resources!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else{
            FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
            Scene secondScene = setScene(loader);

            ResourceMarketController resourceMarketController=loader.getController();
            resourceMarketController.setResourceMarket(lightModel.getMarket());


            // New window (Selection)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            newWindow.showAndWait();}
    }


    public void production(ActionEvent actionEvent) {
        if(discardRequest){
            messages= setDialogPane("You have to discard resources!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else {

        }
    }

    public void endTurn(ActionEvent actionEvent) {
        if(!discardRequest){
            GUI.sendMessage(new RequestEndTurn());
        }
        else           messages=  setDialogPane("You have to discard resources!",PlayerBoardController.dialog,PlayerBoardController.messages);

    }

    public void setDiscardRequest() {
        discardRequest=true;
    }

    public void discardResources(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/DiscardResource.fxml");
        Scene secondScene = setScene(loader);

        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        if(endPhase ){
            messages=setDialogPane("Action not allowed, primary action already taken!",PlayerBoardController.dialog,PlayerBoardController.messages);
        }
        else if(discardRequest){
            messages=setDialogPane("You have to discard resources!",PlayerBoardController.dialog,PlayerBoardController.messages);

        }
        else{
        FXMLLoader loader = load("/fxml/CardLeader.fxml");
        Scene secondScene = setScene(loader);

        CardLeaderController cardLeaderController=loader.getController();

        cardLeaderController.setCardLeaderDeck(lightModel.getCardsLeader());


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();}
    }
}
