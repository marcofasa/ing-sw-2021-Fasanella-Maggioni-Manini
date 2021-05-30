package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardLeader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CardLeaderController extends StandardScene{
    @FXML
    GridPane cardleader_grid;

    private ImageView[] cardLeaderArray;
    private int nRow=3;


    public void discardCardLeader(ActionEvent actionEvent) {
        printClick("Discard button");
    }

    public void activateCardLeader(ActionEvent actionEvent) {
        printClick("Activate button");

    }

    public void setCardLeaderDeck(ArrayList<CardLeader> cardsLeader) {
        cardLeaderArray = new ImageView[3];
        for (int i = 0; i < nRow; i++) {


            Integer type;
            switch (cardsLeader.get(i).getDescription()){
                case Deposit:
                    type =1;
                    break;
                case Discount:
                    type =0;
                    break;
                case Production:
                    type =3;
                    break;
                default:
                    type =2;
            }

            Integer color;
            switch (cardsLeader.get(i).getResource()){
                case Coins:
                    color=0;
                    break;
                case Servants:
                    color=1;
                    break;
                case Shields:
                    color=2;
                    break;
                case Stones:
                    color=3;
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + cardsLeader.get(i).getResource().toString());
            }


            //REAL PATH
            String path="/images/CardLeader/Card_Leader_"+type.toString()+"-"+ color.toString()+".jpg";


            Image image=new Image(GUI.class.getResourceAsStream(path));
            cardLeaderArray[i]=new ImageView(image);

            //Fitting Image
            cardLeaderArray[i].setFitWidth(80);
            cardLeaderArray[i].setFitHeight(120);

            //Mouse Click Event
            int finalI = i;

            cardLeaderArray[i].setOnMouseClicked(mouseEvent -> {
                setClick(finalI);
            });

            //Adding to GridPane
            cardleader_grid.add(cardLeaderArray[i],0,i);
        }
        }

    private void setClick(int finalI) {
    }









    /*
    IMAGE FORMAT
    Card_Leader_a_b
    (a=type, b=color)

    Discount=0
    Deposit=1
    WhiteMarble=2
    Production=3

    shield=2
    coin=0
    stone=3
    servant=1
     */



}
