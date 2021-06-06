package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.CardDevelopmentMarket;
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

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
    }

    public void displayDeposit(ActionEvent actionEvent) {

    }

    public void setModels(LightModel lightModel, LightFaithTrail lightFaithTrail, GamePhase gamePhase){
        this.lightFaithTrail=lightFaithTrail;
        this.lightModel=lightModel;
        this.gamePhase=gamePhase;
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

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
    }

    public void displayCardLeader(ActionEvent actionEvent) {
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

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

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

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
    }

    public void buyCard(ActionEvent actionEvent) {
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

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
    }

    public void buyResource(ActionEvent actionEvent) {
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

            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);

            newWindow.showAndWait();
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        //NOT USEFUL
    }

    public void production(ActionEvent actionEvent) {

    }

    public void endTurn(ActionEvent actionEvent) {
        //SEND END TURN

    }
}
