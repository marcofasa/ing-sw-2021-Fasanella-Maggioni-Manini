package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.requests.RequestEndTurn;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class PlayerBoardController extends StandardStage {

    @FXML
    GridPane cardDevelop_grid;

    @FXML
    GridPane resources_grid;

    @FXML
    DialogPane dialogPane;


    private ImageView[] cardDevelopmentArray;
    private ImageView[][] resourceMatrix ;
    public static DialogPane dialog;
    private LightFaithTrail lightFaithTrail;
    private LightModel lightModel;
    private GamePhase gamePhase;
    private boolean endPhase = false;
    private boolean discardRequest = false;
    public static String messages;


    //SETTERS

    /**
     * Sets the Player Board
     *
     * @param lightModel      of player
     * @param lightFaithTrail of the game
     * @param gamePhase       of current turn
     */
    public void setModels(LightModel lightModel, LightFaithTrail lightFaithTrail, GamePhase gamePhase) {
        this.lightFaithTrail = lightFaithTrail;
        this.lightModel = lightModel;
        this.gamePhase = gamePhase;
        if (gamePhase == GamePhase.Final) {
            setEndPhase();
        } else endPhase = false;
        messages = "";
        dialog = dialogPane;

        //setting card development
        cardDevelopmentArray = new ImageView[3];
        for(int i=1;i<4;i++){
           if( lightModel.getCardsDevelopment().get(i-1)!=null){


            Integer color;
            switch (lightModel.getCardsDevelopment().get(i-1).getCardType()) {
                case Green:
                    color = 0;
                    break;
                case Blue:
                    color = 2;
                    break;
                case Purple:
                    color = 1;
                    break;
                default:
                    color = 3;
            }


            //image final path
            String path = "/images/CardDevelopment/Card_Development_" + lightModel.getCardsDevelopment().get(i-1).getVictoryPoints().toString() + "-" + color.toString() + ".jpg";

            setImageToArray(i-1,path,cardDevelopmentArray,80,120);

        } else {
            // if there's no card on the deck (printing default image)
            String path = "/images/CardDevelopment/Card_Development_Empty.png" ;
            setImageToArray(i-1,path,cardDevelopmentArray,80,120);
        }

        //Adding to GridPane
        cardDevelop_grid.add(cardDevelopmentArray[i-1], i, 0);
    }

        resourceMatrix=new ImageView[3][4];
        //TODO

    }

    private void setEndPhase() {
        endPhase = true;
    }

    public void setDiscardRequest() {
        discardRequest = true;
    }


    //DISPLAY BUTTONS

    public void displayFaithTrail(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/FaithTrail.fxml");
        Scene secondScene = setScene(loader);

        FaithTrailController faithTrailController = loader.getController();
        faithTrailController.setFaithTrail(lightFaithTrail.getFaithTrail(), lightModel.getNickname());

        messages = setDialogPane("Faith Trail displayed", dialogPane, messages);
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
        messages = setDialogPane("Deposit displayed", dialogPane, messages);

        newWindow.showAndWait();
    }

    public void displayResourceMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
        Scene secondScene = setScene(loader);

        ResourceMarketController resourceMarketController = loader.getController();
        resourceMarketController.setResourceMarket(lightModel.getMarket());
        resourceMarketController.setViewOnly();


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        messages = setDialogPane("Resource Market displayed", dialogPane, messages);


        newWindow.showAndWait();
    }

    public void displayCardMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
        Scene secondScene = setScene(loader);

        CardDevelopmentMarketController cardDevelopmentMarketController = loader.getController();
        cardDevelopmentMarketController.setViewOnly();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(), lightModel.getCardsDevelopment());
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        messages = setDialogPane("Card Market displayed", dialogPane, messages);

        newWindow.showAndWait();
    }


    //ACTIONS BUTTONS

    public void buyCard(ActionEvent actionEvent) {
        if (endPhase) {
            messages = setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else if (discardRequest) {
            messages = setDialogPane("You have to discard resources!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else {
            FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
            Scene secondScene = setScene(loader);

            CardDevelopmentMarketController cardDevelopmentMarketController = loader.getController();
            cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(), lightModel.getCardsDevelopment());
            // New window (Selection)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);


            newWindow.showAndWait();
        }
    }

    public void buyResource(ActionEvent actionEvent) {
        if (endPhase) {
            messages = setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else if (discardRequest) {
            messages = setDialogPane("You have to discard resources!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else {
            FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
            Scene secondScene = setScene(loader);

            ResourceMarketController resourceMarketController = loader.getController();
            resourceMarketController.setResourceMarket(lightModel.getMarket());


            // New window (Selection)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);

            newWindow.showAndWait();
        }


    }


    public void production(ActionEvent actionEvent) {
        if (endPhase) {
            messages = setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else if (discardRequest) {
            messages = setDialogPane("You have to discard resources!", PlayerBoardController.dialog, PlayerBoardController.messages);
        } else {
            FXMLLoader loader = load("/fxml/Production.fxml");
            Scene secondScene = setScene(loader);

            ProductionController productionController = loader.getController();
            productionController.setProduction(lightModel.getCardsDevelopment(), lightModel.getCardsLeader());

            // New window (Production)
            Stage newWindow = new Stage();
            newWindow.setScene(secondScene);
            newWindow.showAndWait();
        }
    }

    public void endTurn(ActionEvent actionEvent) {
        if (!discardRequest) {
            GUI.sendMessage(new RequestEndTurn());
        } else
            messages = setDialogPane("You have to discard resources!", PlayerBoardController.dialog, PlayerBoardController.messages);

    }

    public void discardResources(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/DiscardResource.fxml");
        Scene secondScene = setScene(loader);

        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();
    }

    public void displayCardLeader(ActionEvent actionEvent) {

        FXMLLoader loader = load("/fxml/CardLeader.fxml");
        Scene secondScene = setScene(loader);

        CardLeaderController cardLeaderController = loader.getController();

        cardLeaderController.setCardLeaderDeck(lightModel.getCardsLeader());


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);
        newWindow.showAndWait();
    }
}
