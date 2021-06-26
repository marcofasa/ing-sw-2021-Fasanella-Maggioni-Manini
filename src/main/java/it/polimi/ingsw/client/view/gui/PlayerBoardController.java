package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.requests.RequestEndTurn;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


public class PlayerBoardController extends StandardStage {

    @FXML
    GridPane cardDevelop_grid;

    @FXML
    GridPane resources_grid;

    @FXML
    DialogPane dialogPane;


    private Boolean[] depositLevel;
    public static DialogPane dialog;
    private LightFaithTrail lightFaithTrail;
    private LightModel lightModel;
    private boolean endPhase = false;
    private boolean discardRequest = false;
    public static String messages;
    private final Utils utils = new Utils();
    private HashMap<Resource, Integer> discardChoice;


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
        this.discardChoice = new HashMap<>();
        if (gamePhase == GamePhase.Final) {
            setEndPhase();
        } else {
            endPhase = false;
        }
        dialog = dialogPane;

        ArrayList<CardDevelopment> topDevCards = lightModel.getCardsDevelopment();

        //Loading card development
        ImageView[] cardDevelopmentArray = new ImageView[3];
        for (int i = 1; i < 4; i++) {
            if (topDevCards.get(i - 1) != null) {

                Integer color = switch (topDevCards.get(i - 1).getCardType()) {
                    case Green -> 0;
                    case Blue -> 2;
                    case Purple -> 1;
                    default -> 3;
                };
                //image final path
                String path = "/images/CardDevelopment/Card_Development_" + topDevCards.get(i - 1).getVictoryPoints().toString() + "-" + color.toString() + ".jpg";
                setImageToArray(i - 1, path, cardDevelopmentArray, 80, 120);
            } else {
                // if there's no card on the deck (printing default image)
                String path = "/images/CardDevelopment/Card_Development_Empty.png";
                setImageToArray(i - 1, path, cardDevelopmentArray, 80, 120);
            }
            //Adding to GridPane
            cardDevelop_grid.add(cardDevelopmentArray[i - 1], i, 0);
        }


        //Loading Strongbox
        ImageView[][] resourceMatrix = new ImageView[3][5];
        HashMap<Resource, Integer> deposit = lightModel.getDeposit();
        depositLevel = utils.initializeDepositLevel(depositLevel);
        loadDepositLevels(depositLevel,deposit,resources_grid,resourceMatrix);
    }

    void setEndPhase() {
        endPhase = true;
    }

    public void setDiscardRequest(HashMap<Resource, Integer> choice) {
        if (discardChoice == null) {
            discardChoice = new HashMap<Resource, Integer>();
        }
        discardChoice = choice;
        discardRequest = true;
    }


    //DISPLAY BUTTONS

    public void displayFaithTrail(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/FaithTrail.fxml");
        Scene secondScene = setScene(loader);

        FaithTrailController faithTrailController = loader.getController();
        faithTrailController.setFaithTrail(lightFaithTrail.getFaithTrail(), lightModel.getNickname());

        setDialogPane("Faith Trail displayed", dialogPane);
        // New window (Selection)
        showStage(secondScene);
    }

    public void displayStrongBox(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = load("/fxml/StrongBox.fxml");
        Scene secondScene = setScene(fxmlLoader);

        StrongBoxController strongBoxController = fxmlLoader.getController();
        strongBoxController.setStrongBox(lightModel.getStrongbox());

        showStage(secondScene);
        setDialogPane("Strongbox displayed", dialogPane);


    }

    public void displayResourceMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
        Scene secondScene = setScene(loader);

        ResourceMarketController resourceMarketController = loader.getController();
        resourceMarketController.setResourceMarket(lightModel.getMarket());
        resourceMarketController.setViewOnly();


        showStage(secondScene);
        setDialogPane("Resource Market displayed", dialogPane);

    }

    public void displayCardMarket(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
        Scene secondScene = setScene(loader);

        CardDevelopmentMarketController cardDevelopmentMarketController = loader.getController();
        cardDevelopmentMarketController.setViewOnly();
        cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(), lightModel.getCardsDevelopment());
        showStage(secondScene);
        setDialogPane("Card Market displayed", dialogPane);

    }


    //ACTIONS BUTTONS

    public void buyCard(ActionEvent actionEvent) {
        if (endPhase) {
            setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog);
        } else if (discardRequest) {
            setDialogPane("You have to discard resources!", PlayerBoardController.dialog);
        } else {
            FXMLLoader loader = load("/fxml/CardDevelopmentMarket.fxml");
            Scene secondScene = setScene(loader);

            CardDevelopmentMarketController cardDevelopmentMarketController = loader.getController();
            cardDevelopmentMarketController.setDevelopmentMarket(lightModel.getCardDevelopmentMarket(), lightModel.getCardsDevelopment());
            showStage(secondScene);
        }
    }

    public void buyResource(ActionEvent actionEvent) {
        if (endPhase) {
            setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog);
        } else if (discardRequest) {
            setDialogPane("You have to discard resources!", PlayerBoardController.dialog);
        } else {
            FXMLLoader loader = load("/fxml/ResourceMarket.fxml");
            Scene secondScene = setScene(loader);

            ResourceMarketController resourceMarketController = loader.getController();
            resourceMarketController.setResourceMarket(lightModel.getMarket());

            showStage(secondScene);
        }


    }


    public void production(ActionEvent actionEvent) {
        if (endPhase) {
            setDialogPane("Action not allowed, primary action already taken!", PlayerBoardController.dialog);
        } else if (discardRequest) {
            setDialogPane("You have to discard resources!", PlayerBoardController.dialog);
        } else {
            FXMLLoader loader = load("/fxml/Production.fxml");
            Scene secondScene = setScene(loader);

            ProductionController productionController = loader.getController();
            productionController.setProduction(lightModel.getCardsDevelopment(), lightModel.getCardsLeader());
            showStage(secondScene);
        }
    }

    public void endTurn(ActionEvent actionEvent) {
        if (!discardRequest) {
            GUI.sendMessage(new RequestEndTurn());
            setDialogPane("Passing turn... wait for confirmation!", PlayerBoardController.dialog);
        } else
            setDialogPane("You have to discard resources!", PlayerBoardController.dialog);
    }

    public void discardResources(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/DiscardResource.fxml");
        Scene secondScene = setScene(loader);
        DiscardResourceController discardResourceController = loader.getController();
        if (discardChoice != null) {
            discardResourceController.setDiscardSelection(discardChoice);
        }
        showStage(secondScene);
    }

    public void displayCardLeader(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/CardLeader.fxml");
        Scene secondScene = setScene(loader);
        CardLeaderController cardLeaderController = loader.getController();
        cardLeaderController.setCardLeaderDeck(lightModel.getCardsLeader());
        showStage(secondScene);


    }
}
