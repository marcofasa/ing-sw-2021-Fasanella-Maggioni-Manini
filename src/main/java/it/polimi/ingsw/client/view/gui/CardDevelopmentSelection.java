package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardDevelopment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CardDevelopmentSelection extends StandardScene{


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


    @FXML
    GridPane cardDevelopSel_grid;

    private ImageView[] cardDevelopmentArray;
    private int nRow=3;
    private int pos=-1;


    /**
     * Selection confirmed and window closing button
     * @param actionEvent
     */
    public void cardDevelopSelected(ActionEvent actionEvent) {
        printClick("Position Selected button");

        //closing this Stage
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets given Card Developments on GridPane
     * @param cardsDevelopment of current player
     */
    public void setCardDevelopmentSelection(ArrayList<CardDevelopment> cardsDevelopment) {
        cardDevelopmentArray = new ImageView[3];
        for (int i = 0; i < nRow; i++) {
            if (cardsDevelopment.get(i)!=null) {

                Integer color;
                switch (cardsDevelopment.get(i).getCardType()) {
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
                String path = "/images/CardDevelopment/Card_Development_" + cardsDevelopment.get(i).getVictoryPoints().toString() + "-" + color.toString() + ".jpg";


                //loading image and associate a click action
                Image image = new Image(GUI.class.getResourceAsStream(path));
                cardDevelopmentArray[i] = new ImageView(image);

                //Fitting Image
                cardDevelopmentArray[i].setFitWidth(80);
                cardDevelopmentArray[i].setFitHeight(120);

                //Mouse Click Event
                int finalI = i;

                cardDevelopmentArray[i].setOnMouseClicked(mouseEvent -> {
                    setClick(finalI);
                });


            } else {
                // if there's no card on the deck (printing default image)
                String path = "/images/CardDevelopment/Card_Development_Empty.png" ;
                Image image = new Image(GUI.class.getResourceAsStream(path));
                cardDevelopmentArray[i] = new ImageView(image);

                //Fitting Image
                cardDevelopmentArray[i].setFitWidth(80);
                cardDevelopmentArray[i].setFitHeight(120);

                //Mouse Click Event
                int finalI = i;

                cardDevelopmentArray[i].setOnMouseClicked(mouseEvent -> {
                    setClick(finalI);
                });


            }

            //Adding to GridPane
            cardDevelopSel_grid.add(cardDevelopmentArray[i], i, 0);
        }
    }

    /**
     * Sets the column of the position
     * @param finalI column of Card Development Deck
     */
    private void setClick(int finalI) {
        printClick("Card Selection at position "+finalI);
        pos=finalI;
    }


    public int getPos(){
        return pos;
    }

}
