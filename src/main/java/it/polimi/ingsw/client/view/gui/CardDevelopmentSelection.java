package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.model.CardDevelopment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CardDevelopmentSelection extends StandardStage {


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

    private int pos=-1;
    private boolean environmentProduction = false;
    private final boolean[] posArray = {false, false, false};


    /**
     * Selection confirmed and window closing button
     * @param actionEvent
     */
    public void cardDevelopSelected(ActionEvent actionEvent) {
        printClick("Position Selected button");
        closeStage(actionEvent);
    }

    /**
     * Sets given Card Developments on GridPane
     * @param cardsDevelopment of current player
     */
    public void setCardDevelopmentSelection(ArrayList<CardDevelopment> cardsDevelopment) {
        ImageView[] cardDevelopmentArray = new ImageView[3];
        int nRow = 3;
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

                setImageToArray(i,path, cardDevelopmentArray,80,120);

                ColorAdjust monochrome = new ColorAdjust();
                monochrome.setSaturation(-1);

                //Mouse Click Event
                int finalI = i;

                cardDevelopmentArray[i].setOnMouseClicked(mouseEvent -> {
                    setClick(finalI);

                    if (environmentProduction) {

                        if (posArray[finalI]) {
                            cardDevelopmentArray[finalI].setEffect(null);
                        } else {
                            cardDevelopmentArray[finalI].setEffect(monochrome);
                        }

                    } else {

                        for (ImageView image : cardDevelopmentArray) {
                            image.setEffect(null);
                        }
                        cardDevelopmentArray[finalI].setEffect(monochrome);

                    }

                });


            } else {
                // if there's no card on the deck (printing default image)
                String path = "/images/CardDevelopment/Card_Development_Empty.png" ;
                setImageToArray(i,path, cardDevelopmentArray,80,120);

                //Mouse Click Event
                /*int finalI = i;

                cardDevelopmentArray[i].setOnMouseClicked(mouseEvent -> {
                    setClick(finalI);
                });*/
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
        if (!environmentProduction) pos=finalI;
        else {
            posArray[finalI] = !posArray[finalI];
        }
    }


    public int getPos(){
        return pos;
    }

    public boolean[] getPosArray() {
        return posArray;
    }

    public void setProdEnvironment(boolean b) {
        this.environmentProduction = b;
    }
}
