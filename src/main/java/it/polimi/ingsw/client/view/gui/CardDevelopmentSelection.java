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
    @FXML
    GridPane cardDevelopSel_grid;

    private ImageView[] cardDevelopmentArray;
    private int nRow=3;
    private int pos=-1;


    public void cardDevelopSelected(ActionEvent actionEvent) {
        /*
        if(pos<0){
            printError("Position still not selected");
        }
        else{
            printClick("Position Selected button");
            final Node source = (Node) actionEvent.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }*/
        // DEBUG:
        printClick("Position Selected button");
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

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


                //REAL PATH
                String path = "/images/CardDevelopment/Card_Development_" + cardsDevelopment.get(i).getVictoryPoints().toString() + "-" + color.toString() + ".jpg";


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

    private void setClick(int finalI) {
        printClick("Card Selection at position "+finalI);
        pos=finalI;
    }

    public int getPos(){
        return pos;
    }
/*
    Card_Development_a-b

            (a=victory points, b=color)

    green=0
    purple=1
    blue=2
    yellow=3

 */

}
