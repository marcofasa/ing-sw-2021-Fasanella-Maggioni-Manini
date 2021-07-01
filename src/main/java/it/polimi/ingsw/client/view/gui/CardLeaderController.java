package it.polimi.ingsw.client.view.gui;
import it.polimi.ingsw.communication.client.requests.RequestActivateCardLeader;
import it.polimi.ingsw.communication.client.requests.RequestDiscardCardLeader;
import it.polimi.ingsw.model.CardLeader;
import it.polimi.ingsw.model.enums.CardLeaderType;
import it.polimi.ingsw.model.enums.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CardLeaderController extends StandardStage {

    /*
    IMAGE FORMAT
             Card_Leader_a_b

             (a=type, b=color)

    Discount=1
    Deposit=2
    WhiteMarble=3
    Production=4

    shield=2
    coin=0
    stone=3
    servant=1
     */

    @FXML
    GridPane cardleader_grid;
    @FXML
    Label stone_label;
    @FXML
    Label coin_label;
    @FXML
    Label shield_label;
    @FXML
    Label servant_label;

    private CardLeader[] cardLeaders;
    private ArrayList<CardLeader> cardsLeaderArray;
    private Resource[] resources;
    private int lastClick;
    private boolean production=false;


    /**
     * Sets the card Leader images into the Grid
     * @param cardsLeaderArray arraylist of current player
     */
    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeaderArray) {
        this.cardsLeaderArray= cardsLeaderArray;
        ImageView[] cardLeaderArray = new ImageView[2];
        int nRow = 2;
        for (int i = 0; i < nRow; i++) {


        if((cardsLeaderArray.size()==1 &&  i==1) || cardsLeaderArray.size()==0 || cardsLeaderArray.get(i)==null){

            //Standard path for empty slot

            String path="/images/CardDevelopment/Card_Development_Empty.png";
            setImageToArray(i,path, cardLeaderArray,120,220);

            //Mouse Click Event
            int finalI = i;

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> setClick(finalI));

            //Adding to GridPane
        }
        else {

            //Generation of image path
            Integer type = getCardLeaderType(cardsLeaderArray.get(i));
            Integer color = getCardLeaderColor(cardsLeaderArray.get(i));
            String path="/images/CardLeader/Card_Leader_"+type.toString()+"-"+ color.toString()+".jpg";
            setImageToArray(i,path, cardLeaderArray,120,220);

            //Mouse Click Event
            int finalI = i;

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> setClick(finalI));

            //Adding to GridPane
        }
            cardleader_grid.add(cardLeaderArray[i],i,0);
        }

        resourceHandler(GUI.getClient().getLightModel().getDepositLeaderContent(),coin_label,servant_label,shield_label,stone_label);

        }

    private void setClick(int finalI) {
        lastClick=finalI;
        if (cardLeaders == null){
            cardLeaders=new CardLeader[2];
        }
        if (cardLeaders[0] == null){
            cardLeaders[0]=cardsLeaderArray.get(finalI);
        }
        else cardLeaders[1]=cardsLeaderArray.get(finalI);
    }

    public void setProduction(boolean production) {
        this.production = production;
    }


    //GETTERS

    public CardLeader[] getCardLeaders() {
        return cardLeaders;
    }

    public Resource[] getResources() {
        if (resources==null){
            resources=new Resource[2];
            resources[0]=null;
            resources[1]=null;
        }
        return resources;
    }

    //CLOSING BUTTONS

    public void discardCardLeader(ActionEvent actionEvent) {
        if(lastClick>=0){
        GUI.sendMessage(new RequestDiscardCardLeader(lastClick+1));
        setDialogPane("Card Leader discarded",PlayerBoardController.dialog);
        closeStage(actionEvent);

        }
        else {
            setDialogPane("Card Leader not picked!",PlayerBoardController.dialog);
        }
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        if (production) {
            if (cardsLeaderArray.get(lastClick).getDescription() != CardLeaderType.Production) {
                GUI.displayMessage("Only Card Leader Production can be activated from this panel");
                return;
            }
            FXMLLoader loader = load("/fxml/ActivateCardLeaderOutput.fxml");
            Scene secondScene = setScene(loader);
            ActivateCardLeaderOutputController activateCardLeaderOutput=loader.getController();
            // New window (Selection)
            showStage(secondScene);
            if (resources==null){
                resources=new Resource[2];
            }
            if (resources[0]==null){
                resources[0]=activateCardLeaderOutput.getResource();
            }
            else resources[1]=activateCardLeaderOutput.getResource();

            GUI.displayMessage("Card Leader added to selection");

            setProduction(false);
            closeStage(actionEvent);
        }
        else {
            //setDialogPane("Card leader can be activated only in Production action",PlayerBoardController.dialog);
            if (cardsLeaderArray.get(lastClick).getDescription() != CardLeaderType.Production) {
                GUI.sendMessage(new RequestActivateCardLeader(cardsLeaderArray.get(lastClick)));
                GUI.displayMessage("Card leader " + cardsLeaderArray.get(lastClick).getDescription() + " activation requested");
            } else {
                GUI.displayMessage("Production Leader Cards must be activated from the Production panel!");
            }
        }
    }
}
