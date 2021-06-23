package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.LightFaithTrail;
import it.polimi.ingsw.client.LightModel;
import it.polimi.ingsw.communication.client.requests.RequestEndTurn;
import it.polimi.ingsw.communication.server.requests.GamePhase;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;


public class PlayerBoardController extends StandardStage {

    @FXML
    GridPane cardDevelop_grid;

    @FXML
    GridPane resources_grid;

    @FXML
    DialogPane dialogPane;


    private ImageView[] cardDevelopmentArray;
    private ImageView[][] resourceMatrix ;
    private HashMap<Resource,Integer> strongbox;
    private Boolean[] strongboxLevel;
    public static DialogPane dialog;
    private LightFaithTrail lightFaithTrail;
    private LightModel lightModel;
    private GamePhase gamePhase;
    private boolean endPhase = false;
    private boolean discardRequest = false;
    public static String messages;
    private final Utils utils=new Utils();


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


        //Loading card development
        cardDevelopmentArray = new ImageView[3];
        for(int i=1;i<4;i++){
           if( lightModel.getCardsDevelopment().get(i-1)!=null){

            Integer color = switch (lightModel.getCardsDevelopment().get(i - 1).getCardType()) {
                case Green -> 0;
                case Blue -> 2;
                case Purple -> 1;
                default -> 3;
            };
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


        //Loading Strongbox
        resourceMatrix=new ImageView[3][5];
        strongbox= lightModel.getDeposit();
        strongboxLevel=utils.setStrongboxLevel(strongboxLevel);
        for(Resource resource: strongbox.keySet()){
            if(strongbox.get(resource)==3){
                  loadStrongboxLevel(resource,resourceMatrix,2,1,3,resources_grid);
                   strongboxLevel[2]=true;
            }
            else if(strongbox.get(resource)==2){
                 if(!strongboxLevel[1]){
                     loadStrongboxLevel(resource,resourceMatrix,1,1,2,resources_grid);
                     strongboxLevel[1]=true;
                 }
                 else {
                     loadStrongboxLevel(resource,resourceMatrix,2,1,2,resources_grid);
                     strongboxLevel[2]=true;
                 }
            }
            else if (strongbox.get(resource)==1){
               if(!strongboxLevel[0]){
                   loadStrongboxLevel(resource,resourceMatrix,0,2,1,resources_grid);
strongboxLevel[0]=true;
               }
               else if(!strongboxLevel[1]){
                   loadStrongboxLevel(resource,resourceMatrix,1,1,1,resources_grid);
                   strongboxLevel[1]=true;
               }
               else {
                   loadStrongboxLevel(resource,resourceMatrix,2,1,1,resources_grid);
                   strongboxLevel[2]=true;
               }
            }
        }

    }

    private void loadStrongboxLevel(Resource resource, ImageView[][] resourceMatrix, int row, int startingColumn, int nResources,GridPane gridPane) {
        String path= utils.getResourcePath(resource);
        while(nResources>0) {
            setImageToMatrix(row, startingColumn, resourceMatrix, path, 20, 20,gridPane);
            startingColumn++;
            nResources--;
        }
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
        setDialogPane("Deposit displayed", dialogPane);


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
            setDialogPane("Turn finished!", PlayerBoardController.dialog);
        } else
            setDialogPane("You have to discard resources!", PlayerBoardController.dialog);
    }

    public void discardResources(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/DiscardResource.fxml");
        Scene secondScene = setScene(loader);
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
