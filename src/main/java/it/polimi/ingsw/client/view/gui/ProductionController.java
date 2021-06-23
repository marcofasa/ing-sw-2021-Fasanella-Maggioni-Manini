package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.communication.client.requests.RequestActivateProduction;
import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.ProductionSelection;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class ProductionController extends StandardStage {

    public ProductionSelection productionSelection;
    private ArrayList<CardDevelopment> cardsDevelopment;
    private ArrayList<CardLeader> cardsLeader;
    private final Resource[] resources=new Resource[3];
    private final Boolean[] cardDevelopArray =new Boolean[3];


    /**
     * Sets card for Production selection
     * @param cardsDevelopment of player
     * @param cardsLeader of player
     */
    public void setProduction(ArrayList<CardDevelopment> cardsDevelopment, ArrayList<CardLeader> cardsLeader){
        productionSelection=new ProductionSelection();
        this.cardsDevelopment=cardsDevelopment;
        this.cardsLeader=cardsLeader;
        cardDevelopArray[0]=false;
        cardDevelopArray[1]=false;
        cardDevelopArray[2]=false;
    }


    public ProductionSelection getProductionSelection(){
        return productionSelection;
    }



    //PRODUCTION BUTTONS

    public void basicProduction(ActionEvent actionEvent) {
        productionSelection.setBasicProdInfo(resources);
    }

    public void cardDevelopProduction(ActionEvent actionEvent) {
        FXMLLoader loader = load("/fxml/CardDevelopmentSelection.fxml");
        Scene secondScene = setScene(loader);
        CardDevelopmentSelection cardDevelopmentSelection=loader.getController();
        cardDevelopmentSelection.setCardDevelopmentSelection(cardsDevelopment);
        showStage(secondScene);
        int pos=cardDevelopmentSelection.getPos();

        cardDevelopArray[pos]=true;


    }

    public void cardLeaderProduction(ActionEvent actionEvent) {

        FXMLLoader loader = load("/fxml/CardLeader.fxml");
        Scene secondScene = setScene(loader);

        CardLeaderController cardLeaderController=loader.getController();

        cardLeaderController.setCardLeaderDeck(cardsLeader);
        cardLeaderController.setProduction(true);

        // New window (Selection)
        showStage(secondScene);
        productionSelection.setCardLeadersToActivate(cardLeaderController.getCardLeaders());
        productionSelection.setCardLeaderProdOutputs(cardLeaderController.getResources());


    }

    public void production(ActionEvent actionEvent) {
        productionSelection.setCardDevelopmentSlotActive(cardDevelopArray);
        GUI.sendMessage(new RequestActivateProduction(productionSelection));
        setDialogPane("Production activated!",PlayerBoardController.dialog);
        closeStage(actionEvent);
    }


    //BASIC PRODUCTION BUTTONS

    public void stone_input(MouseEvent mouseEvent) {
        if(resources[0]==null){
            resources[0]=Resource.Stones;
        }
        else resources[1]=Resource.Stones;
    }

    public void coin_input(MouseEvent mouseEvent) {
        if(resources[0]==null){
            resources[0]=Resource.Coins;
        }
        else resources[1]=Resource.Coins;
    }

    public void servant_input(MouseEvent mouseEvent) {
        if(resources[0]==null){
            resources[0]=Resource.Servants;
        }
        else resources[1]=Resource.Servants;
    }

    public void shield_input(MouseEvent mouseEvent) {
        if(resources[0]==null){
            resources[0]=Resource.Shields;
        }
        else resources[1]=Resource.Shields;
    }

    public void stone_output(MouseEvent mouseEvent) {
        resources[2]=Resource.Stones;
    }

    public void coin_output(MouseEvent mouseEvent) {
        resources[2]=Resource.Coins;
    }

    public void shield_output(MouseEvent mouseEvent) {
        resources[2]=Resource.Shields;
    }

    public void servant_output(MouseEvent mouseEvent) {
        resources[2]=Resource.Servants;
    }


}
