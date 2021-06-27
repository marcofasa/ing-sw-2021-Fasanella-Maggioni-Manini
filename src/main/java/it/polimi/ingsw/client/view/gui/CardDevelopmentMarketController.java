package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.communication.client.requests.RequestBuyDevelopmentCard;
import it.polimi.ingsw.model.CardDevelopment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class CardDevelopmentMarketController extends StandardStage {
    

    /*
    IMAGE FORMAT
    This is a generic path for Card Development Cards
               Card_Development_a-b

            (a=victory points, b=color)

    green=0
    purple=1
    blue=2
    yellow=3
     */

    private int buyRow=-1;
    private int buyCol=-1;
    private int pos;
    private boolean viewOnly=false;
    private ArrayList<CardDevelopment> cardDevelopments;

    @FXML
    GridPane cardDevelop_grid;

    @Override
    public void init() {
        super.init();
    }


    /**
     * Sets the market images and let the player choose where to place the purchased card
     * @param cardDevelopmentMarket arraylist of the market
     * @param cardDevelopments arraylist of current player cards (to select where to place the purchased card)
     */
    public void setDevelopmentMarket(ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket,ArrayList<CardDevelopment> cardDevelopments){
        this.cardDevelopments=cardDevelopments;
        ImageView[][] cardDevelopmentMatrix = new ImageView[3][4];
        String path;
        int nRow = 3;
        for(int i = 0; i< nRow; i++){
            int nCol = 4;
            for (int j = 0; j< nCol; j++){

                if (cardDevelopmentMarket.get(i).get(j) != null) {

                    Integer victoryPoints=cardDevelopmentMarket.get(i).get(j).getVictoryPoints();
                    Integer color = switch (cardDevelopmentMarket.get(i).get(j).getCardType()) {
                        case Green -> 0;
                        case Purple -> 1;
                        case Blue -> 2;
                        case Yellow -> 3;
                    };


                    //Image Path
                    path = "/images/CardDevelopment/Card_Development_"+victoryPoints.toString()+"-"+color.toString()+".jpg";

                } else path = "/images/CardDevelopment/Card_Development_Empty.png";

               setImageToMatrix(i,j, cardDevelopmentMatrix,path,80,120);

                //Mouse Click Event
                int finalI = i;
                int finalJ = j;
                cardDevelopmentMatrix[i][j].setOnMouseClicked(mouseEvent -> {
                     setBuyClick(finalI, finalJ);
                });

                //Adding to GridPane
                cardDevelop_grid.add(cardDevelopmentMatrix[i][j],j,i);
            }
        }
    }


    /**
     * Sets the coordinates of the card
     * @param row of market (0<=col<=2)
     * @param col of market (0<=col<=3)
     */
    public void setBuyClick(int row,int col){
        buyCol=col;
        buyRow=row;

        //DEBUG
        System.out.println("You have chosen row "+buyRow+" column "+buyCol);
    }


    /**
     * Mouse click on "Purchase" button
     * @param actionEvent
     */
    public void buyCardDevelopment(ActionEvent actionEvent) {
        if(viewOnly){
            setDialogPane("Action not admitted. View only mode!",PlayerBoardController.dialog);
        }
        else{
        if(pos<0 || buyRow<0 || buyCol<0){
            setDialogPane("Position still not selected or Card not Picked",PlayerBoardController.dialog);
        }
        else{
            setDialogPane("Card Development purchased",PlayerBoardController.dialog);
            GUI.sendMessage(new RequestBuyDevelopmentCard(buyRow,buyCol,pos));
            closeStage(actionEvent);
        }}
    }


    /**
     * Opens a new window with player Card Development Cards and lets choose the place position of the card
     * that will be purchased.
     * @param actionEvent button clicked
     */
    public void selectionPositionCardDevelop(ActionEvent actionEvent) {
        FXMLLoader loader=load("/fxml/CardDevelopmentSelection.fxml");
        Scene secondScene=setScene(loader);

        //loading Stage
        CardDevelopmentSelectionController cardDevelopmentSelection=loader.getController();
        cardDevelopmentSelection.setCardDevelopmentSelection(cardDevelopments);
        showStage(secondScene);
        pos=cardDevelopmentSelection.getPos();
    }

    /**
     * Sets the market to View Only Mode: player will not be able to buy cards (clicking button)
     */
    public void setViewOnly() {
        viewOnly=true;
    }
}
