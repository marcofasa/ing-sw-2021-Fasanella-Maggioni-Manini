package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.model.CardDevelopment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image ;

import java.util.ArrayList;


public class CardDevelopmentMarketController extends StandardScene{

    private int nRow=3;
    private int nCol=4;

    private int buyRow;
    private int buyCol;


    @FXML
    GridPane cardDevelop_grid;


    @Override
    public void init() {
        super.init();
    }


      @Override
    public void setDevelopmentMarket(ArrayList<ArrayList<CardDevelopment>> cardDevelopments){
        ImageView[][] cardDevelopmentMatrix =new ImageView[3][4];
        for(int i=0;i<nRow;i++){
            for (int j=0;j<nCol;j++){

                Integer victoryPoints=cardDevelopments.get(i).get(j).getVictoryPoints();
                Integer color;
                switch (cardDevelopments.get(i).get(j).getCardType()){
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
                        throw new IllegalStateException("Unexpected value: " + cardDevelopments.get(i).get(j).getCardType());
                }
                //String path="/images/CardDevelopment/Card_Development_"+victoryPoints.toString()+"-"+color.toString()+".jpg";
                String path="/images/CardDevelopment/Card_Development_1-0.jpg";


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
        //Better to do not wait (sleep) on click (do it on a new thread)

    }
}
