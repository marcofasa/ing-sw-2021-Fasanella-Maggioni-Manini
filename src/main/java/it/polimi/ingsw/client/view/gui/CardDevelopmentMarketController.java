package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardDevelopment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image ;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class CardDevelopmentMarketController extends StandardScene{
    

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

    private int nRow=3;
    private int nCol=4;

    private int buyRow=-1;
    private int buyCol=-1;
    private ImageView[][] cardDevelopmentMatrix;
    private int pos;
    private boolean viewOnly=false;
    private ArrayList<CardDevelopment> cardDevelopments;

    @FXML
    GridPane cardDevelop_grid;



    @Override
    public void init() {
        super.init();
    }



    public void setDevelopmentMarket(ArrayList<ArrayList<CardDevelopment>> cardDevelopmentMarket,ArrayList<CardDevelopment> cardDevelopments){
        this.cardDevelopments=cardDevelopments;
         cardDevelopmentMatrix =new ImageView[3][4];
        for(int i=0;i<nRow;i++){
            for (int j=0;j<nCol;j++){

                Integer victoryPoints=cardDevelopmentMarket.get(i).get(j).getVictoryPoints();
                Integer color;
                switch (cardDevelopmentMarket.get(i).get(j).getCardType()){
                    case Green:
                        color=0;
                        break;
                    case Purple:
                        color=1;
                        break;
                    case Blue:
                        color=2;
                        break;
                    case Yellow:
                        color=3;
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + cardDevelopmentMarket.get(i).get(j).getCardType());
                }

                //Image Path
                String path="/images/CardDevelopment/Card_Development_"+victoryPoints.toString()+"-"+color.toString()+".jpg";



                Image image=new Image(GUI.class.getResourceAsStream(path));
                cardDevelopmentMatrix[i][j]=new ImageView(image);

                //Fitting Image
                cardDevelopmentMatrix[i][j].setFitWidth(80);
                cardDevelopmentMatrix[i][j].setFitHeight(120);

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
            printError("Action not admitted. View only mode!");
        }
        else{
        if(pos<0 || buyRow<0 || buyCol<0){
            printError("Position still not selected or Card not Picked");
        }
        else{
            printClick("Purchase button");
            //send al client
            final Node source = (Node) actionEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }}
    }


    /**
     * Opens a new window with player Card Development Cards and lets choose the place position of the card
     * that will be purchased.
     * @param actionEvent button clicked
     */
    public void selectionPositionCardDevelop(ActionEvent actionEvent) {
        //loading secondary scene
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/CardDevelopmentSelection.fxml"));
        Scene secondScene = null;
        try {
            secondScene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }


        CardDevelopmentSelection cardDevelopmentSelection=loader.getController();
        cardDevelopmentSelection.setCardDevelopmentSelection(cardDevelopments);
        Stage newWindow = new Stage();
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        //newWindow.setX(primaryStage.getX() + 200);
        //newWindow.setY(primaryStage.getY() + 100);

        newWindow.showAndWait();
        pos=cardDevelopmentSelection.getPos();
    }

    /**
     * Sets the market to View Only Mode: player will not be able to buy cards (clicking button)
     */
    public void setViewOnly() {
        viewOnly=true;
    }
}
