package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardDevelopment;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.ProductionSelection;
import it.polimi.ingsw.model.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ProductionController extends StandardScene{

    public ProductionSelection productionSelection;
    private ArrayList<CardDevelopment> cardsDevelopment;
    private ArrayList<CardLeader> cardsLeader;
    private Resource[] resources=new Resource[3];
    private Boolean[] cardDevelopArray =new Boolean[3];

    public void basicProduction(ActionEvent actionEvent) {
        productionSelection.setBasicProdInfo(resources);
    }

    public void cardDevelopProduction(ActionEvent actionEvent) { 


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardDevelopmentSelection.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardDevelopmentSelection cardDevelopmentSelection=loader.getController();
        cardDevelopmentSelection.setCardDevelopmentSelection(cardsDevelopment);
        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
        int pos=cardDevelopmentSelection.getPos();

        cardDevelopArray[pos]=true;


    }

    public void cardLeaderProduction(ActionEvent actionEvent) {
        //TODO
        /*
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardLeader.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CardLeaderController cardLeaderController=loader.getController();
        cardLeaderController.setCardLeaderDeck(cardsLeader);


        // New window (Selection)
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();

         */

    }

    public void production(ActionEvent actionEvent) {
        productionSelection.setCardDevelopmentSlotActive(cardDevelopArray);

        printClick("Production button");
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

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
